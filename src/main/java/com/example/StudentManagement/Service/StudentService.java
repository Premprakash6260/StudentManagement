package com.example.StudentManagement.Service;

import com.example.StudentManagement.Entity.Address;
import com.example.StudentManagement.Entity.AddressType;
import com.example.StudentManagement.Entity.Course;
import com.example.StudentManagement.Entity.User;
import com.example.StudentManagement.Model.StudentProfileDto;
import com.example.StudentManagement.Repository.CourseRepository;
import com.example.StudentManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentProfileDto getStudentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Comes from JWT subject

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return StudentProfileDto.builder()
                .email(student.getEmail())
                .mobile(student.getMobile())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .addresses(student.getAddresses())
                .build();
    }

    public StudentProfileDto updateStudentProfile(StudentProfileDto studentProfileDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setEmail(studentProfileDto.getEmail());
        student.setMobile(studentProfileDto.getMobile());
        student.setFatherName(studentProfileDto.getFatherName());
        student.setMotherName(studentProfileDto.getMotherName());

        if (studentProfileDto.getAddresses() != null && !studentProfileDto.getAddresses().isEmpty()) {
            // Convert to a Map for quick lookup
            Map<AddressType, Address> updatedAddressMap = studentProfileDto.getAddresses().stream()
                    .collect(Collectors.toMap(Address::getAddressType, addr -> {
                        addr.setUser(student);
                        return addr;
                    }));

            // Create a map of existing addresses for the student
            Map<AddressType, Address> currentAddressMap = student.getAddresses().stream()
                    .collect(Collectors.toMap(Address::getAddressType, Function.identity(), (a, b) -> b));

            // Merge updated addresses into the student entity
            for (AddressType type : updatedAddressMap.keySet()) {
                Address updated = updatedAddressMap.get(type);
                if (currentAddressMap.containsKey(type)) {
                    // Update existing address
                    Address existing = currentAddressMap.get(type);
                    existing.setStreet(updated.getStreet());
                    existing.setCity(updated.getCity());
                    existing.setState(updated.getState());
                    existing.setPincode(updated.getPincode());
                } else {
                    // Add new address
                    student.getAddresses().add(updated);
                }
            }
        }

        User saved = userRepository.save(student);

        return StudentProfileDto.builder()
                .email(saved.getEmail())
                .mobile(saved.getMobile())
                .fatherName(saved.getFatherName())
                .motherName(saved.getMotherName())
                .addresses(saved.getAddresses())
                .build();
    }

    public Set<Course> getCourses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getCourses(); // returns Set<Course>
    }

    public void leaveCourseByName(String courseName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findByName(courseName)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        boolean removed = student.getCourses().remove(course);
        course.getUsers().remove(student);

        if (!removed) {
            throw new RuntimeException("Student was not enrolled in course: " + courseName);
        }

        userRepository.save(student);
    }

}
