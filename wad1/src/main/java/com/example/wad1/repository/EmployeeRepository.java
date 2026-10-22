package com.example.wad1.repository;

import com.example.wad1.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
    List<Employee> findByDepartmentIgnoreCase(String department);
}
