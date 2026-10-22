
package com.example.wad1.controller;

import com.example.wad1.model.Employee;
import com.example.wad1.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(
            EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET: Display IT employees
    @GetMapping("/ITemployees")
    public List<Employee> getITEmployees() {
        return employeeRepository
                .findByDepartmentIgnoreCase("IT");
    }

    // POST: Add a new employee
    @PostMapping("/employee/addNew")
    public Employee addEmployee(
            @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
}
