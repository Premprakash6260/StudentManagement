package com.example.StudentManagement.Model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignCourseRequest {
    private String studentCode;
    private String courseName;
}
