package com.example.StudentManagement.Repository;

import com.example.StudentManagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByCourses_Id(Long courseId);
    public Optional<User> findByStudentCode(String studentCode);
}
