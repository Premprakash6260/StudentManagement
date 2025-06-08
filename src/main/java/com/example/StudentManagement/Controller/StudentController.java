package com.example.StudentManagement.Controller;

import com.example.StudentManagement.Entity.Course;
import com.example.StudentManagement.Model.StudentProfileDto;
import com.example.StudentManagement.Repository.UserRepository;
import com.example.StudentManagement.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.StudentManagement.Entity.User;

import java.util.List;
import java.util.Set;

@Tag(name = "Student Profile", description = "Operations related to student profile")
@CrossOrigin
@RestController
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String getMessage(){
        return "I am an Student only student can access this";
    }

    @Operation(
            summary = "Get Student Profile",
            description = "Fetches the profile details of the currently logged-in student including personal info and addresses."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student profile"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have STUDENT role")
    })
    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDto> getStudentProfile() {
        StudentProfileDto profile = studentService.getStudentProfile();
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<StudentProfileDto> updateProfile(@RequestBody StudentProfileDto student) {
        StudentProfileDto profile = studentService.updateStudentProfile(student);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/courses")
    public ResponseEntity<Set<Course>> getMyCourses() {
        return ResponseEntity.ok(studentService.getCourses());
    }

    @DeleteMapping("/course/{courseName}")
    public ResponseEntity<String> removeCourse(@PathVariable String courseName) {
        studentService.leaveCourseByName(courseName);
        return ResponseEntity.ok("Successfully left course: " + courseName);
    }
}
