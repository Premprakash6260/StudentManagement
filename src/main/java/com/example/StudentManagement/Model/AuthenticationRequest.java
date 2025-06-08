package com.example.StudentManagement.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    private String email; //email
    private String password;
}
