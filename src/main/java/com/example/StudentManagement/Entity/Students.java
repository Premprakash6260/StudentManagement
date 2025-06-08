package com.example.StudentManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.*;

@Table(name = "students")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String studentCode;
    private String gender;

    private LocalDate dateOfBirth;


    //private List<Address> addresses = new ArrayList<>();

    //private Set<Course> courses = new HashSet<>();
}
