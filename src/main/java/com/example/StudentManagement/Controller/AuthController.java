package com.example.StudentManagement.Controller;

import com.example.StudentManagement.Model.AuthenticationRequest;
import com.example.StudentManagement.Model.AuthenticationResponse;
import com.example.StudentManagement.Model.RegisterRequest;
import com.example.StudentManagement.Model.StudentAuthRequest;
import com.example.StudentManagement.Service.AuthService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin-registration")
    public ResponseEntity<AuthenticationResponse> userRegistration(@RequestBody RegisterRequest registerRequest) {
        System.out.println("request: "+registerRequest);
        AuthenticationResponse accessToken = userService.saveUser(registerRequest);
        return new ResponseEntity<>(accessToken, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> userLogin(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse accessToken = userService.authentication(authenticationRequest);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @PostMapping("student-login")
    public ResponseEntity<AuthenticationResponse> studentLogin(@RequestBody StudentAuthRequest authenticationRequest){
        AuthenticationResponse accessToken = userService.studentAuthentication(authenticationRequest);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    @GetMapping("api-testing")
    public String test(){
        return "you are in Spring boot";
    }

}
