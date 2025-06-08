package com.example.StudentManagement.Service;

import com.example.StudentManagement.Entity.Address;
import com.example.StudentManagement.Entity.Course;
import com.example.StudentManagement.Entity.Role;
import com.example.StudentManagement.Entity.User;
import com.example.StudentManagement.Model.AssignCourseRequest;
import com.example.StudentManagement.Model.StudentCreateDto;
import com.example.StudentManagement.Repository.CourseRepository;
import com.example.StudentManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentCreateDto addStudent(StudentCreateDto studentCreateDto) {
        User user = User.builder()
                .name(studentCreateDto.getName())
                .email(studentCreateDto.getEmail())
                .dateOfBirth(studentCreateDto.getDateOfBirth())
                .studentCode(studentCreateDto.getStudentCode())
                .gender(studentCreateDto.getGender())
                .role(Role.valueOf("STUDENT"))
                .build();

        List<Address> addresses = studentCreateDto.getAddresses().stream()
                .map(address -> {
                    address.setUser(user);
                    return address;
                }).toList();

        user.setAddresses(addresses);
        userRepository.save(user);
        return studentCreateDto;
    }

    public Course uploadCourse(Course course) {
        course.setId(null);
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }

    public String assignCourseToStudent(AssignCourseRequest request) {
        User student = userRepository.findByStudentCode(request.getStudentCode())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findByName(request.getCourseName())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Prevent duplicate assignment
        if (student.getCourses().contains(course)) {
            return "Course already assigned to this student.";
        }

        student.getCourses().add(course);
        userRepository.save(student);

        return "Course assigned to student successfully.";
    }

    public List<User> getStudentsByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> getStudentsByCourseName(String courseName) {
        Course course = courseRepository.findByName(courseName)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return new ArrayList<>(course.getUsers());
    }
}
