package com.example.StudentManagement.Model;

import com.example.StudentManagement.Entity.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateDto {
    private String name;
    private String email;
    private String gender;
    private String studentCode;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private List<Address> addresses;
}
