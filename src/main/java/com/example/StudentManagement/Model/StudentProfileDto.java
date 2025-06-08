package com.example.StudentManagement.Model;

import com.example.StudentManagement.Entity.Address;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileDto {
    private String email;
    private String mobile;
    private String fatherName;
    private String motherName;

    private List<Address> addresses;
}
