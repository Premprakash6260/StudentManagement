package com.example.StudentManagement.Repository;

import com.example.StudentManagement.Entity.Course;
import com.example.StudentManagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    public Optional<Course> findByName(String courseName);
}
