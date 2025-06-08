package com.example.StudentManagement.Service.AuthService;

import com.example.StudentManagement.Entity.Role;
import com.example.StudentManagement.Entity.User;
import com.example.StudentManagement.Exception.CustomerException.*;
import com.example.StudentManagement.Model.AuthenticationRequest;
import com.example.StudentManagement.Model.AuthenticationResponse;
import com.example.StudentManagement.Model.RegisterRequest;
import com.example.StudentManagement.Model.StudentAuthRequest;
import com.example.StudentManagement.Repository.UserRepository;
import com.example.StudentManagement.Service.JwtService.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.StudentManagement.Exception.CustomerException.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;


    //user registration
    public AuthenticationResponse saveUser(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(existingUser -> {
            throw new UserAlreadyExistException("User with email " + registerRequest.getEmail() + " already exists");
        });
        User user = User.builder()
                .name(registerRequest.getFirstName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf("STUDENT"))
                .build();

        User savedUser = userRepository.save(user);
        //generate token with secret (jwt)
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }

    //    user login Authentication
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User Not Found by this E-mail: " + request.getEmail()));
        //generate token with secret (jwt)
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }

    public AuthenticationResponse studentAuthentication(StudentAuthRequest request) {
        User user = userRepository.findByStudentCode(request.getStudentCode())
                .orElseThrow(() -> new UserNotFoundException("Student not found with code: " + request.getStudentCode()));

        if (!user.getDateOfBirth().equals(request.getDateOfBirth())) {
            throw new RuntimeException("Invalid date of birth");
        }

        // Check role (optional)
        if (user.getRole() != Role.STUDENT) {
            throw new RuntimeException("Access denied: User is not a student");
        }

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }

}
