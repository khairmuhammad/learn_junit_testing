package com.example.learn_junit_testing.controller;

import com.example.learn_junit_testing.entity.Student;
import com.example.learn_junit_testing.exception.NotFoundException;
import com.example.learn_junit_testing.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public Student addStudent(@RequestBody Student student) {

        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) throws NotFoundException {

        if(!studentRepository.findById(studentId).isPresent()) throw new NotFoundException("studentId: " + studentId + " not present.");
        studentRepository.deleteById(studentId);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) throws NotFoundException {

        if(student == null || student.getStudentId() == null) {
            throw new NotFoundException("Student record or ID must not be null");
        }
        Optional<Student> optionalStudent = studentRepository.findById(student.getStudentId());
        if (!optionalStudent.isPresent()) throw new NotFoundException("Student with ID: " + student.getStudentId() + " does not exists.");

        Student existingStudent = optionalStudent.get();
        BeanUtils.copyProperties(student, existingStudent);

        return studentRepository.save(student);
    }
}
