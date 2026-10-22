package com.example.wad1.repository;

import com.example.wad1.model.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PayrollRepository extends MongoRepository<Payroll,String> {
    List<Payroll> findByEmployeeId(String employeeId);
}
