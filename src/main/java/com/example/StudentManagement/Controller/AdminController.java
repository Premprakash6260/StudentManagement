package com.example.StudentManagement.Controller;


import com.example.StudentManagement.Entity.Address;
import com.example.StudentManagement.Entity.Course;
import com.example.StudentManagement.Entity.User;
import com.example.StudentManagement.Model.AssignCourseRequest;
import com.example.StudentManagement.Model.StudentCreateDto;
import com.example.StudentManagement.Repository.UserRepository;
import com.example.StudentManagement.Service.AdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("admin")
    public String getMessage(){
        return "I am an Admin only admin can access this";
    }

    @PostMapping("add-student")
    public ResponseEntity<StudentCreateDto> addStudent(@RequestBody StudentCreateDto studentCreateDto){
        StudentCreateDto student = adminService.addStudent(studentCreateDto);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/add-course")
    public ResponseEntity<Course> uploadCourse(@RequestBody Course course) {
        System.out.println("Course: " + course);
        Course savedCourse = adminService.uploadCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PostMapping("/courses-assign")
    public ResponseEntity<String> assignCourseToStudent(@RequestBody AssignCourseRequest request) {
        String responseMessage = adminService.assignCourseToStudent(request);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/get-student/{name}")
    public ResponseEntity<List<User>> getStudentsByName(@PathVariable String name) {
        List<User> students = adminService.getStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get-by-course/{courseName}")
    public ResponseEntity<List<User>> getStudentsByCourse(@PathVariable String courseName) {
        List<User> students = adminService.getStudentsByCourseName(courseName);
        return ResponseEntity.ok(students);
    }
}
