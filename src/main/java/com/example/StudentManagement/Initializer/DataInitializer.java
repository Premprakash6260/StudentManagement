package com.example.StudentManagement.Initializer;

import com.example.StudentManagement.Entity.Role;
import com.example.StudentManagement.Entity.User;
import com.example.StudentManagement.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Check if admin already exists
        if (userRepository.findByEmail("admin").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Default admin user created: email=admin, password=admin");
        } else {
            System.out.println("Admin already exists. Skipping admin creation.");
        }
    }
}
