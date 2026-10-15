package com.example.crud.service;

import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(String id) {
        return repository.findById(id);
    }

    public Student addStudent(Student student) {
        student.setId(null);
        return repository.save(student);
    }

    public Student updateStudent(String id, Student student) {
        student.setId(id);
        return repository.save(student);
    }

    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
}
