package com.example.learn_junit_testing.repository;

import com.example.learn_junit_testing.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmailId(String emailId);
}
