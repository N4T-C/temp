package com.example.wad1.service;

import com.example.wad1.model.Employee;
import com.example.wad1.model.Payroll;
import com.example.wad1.repository.EmployeeRepository;
import com.example.wad1.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {
    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;

    public AnalyticsService(EmployeeRepository employeeRepository, PayrollRepository payrollRepository){
        this.employeeRepository = employeeRepository;
        this.payrollRepository = payrollRepository;
    }

    public double getTotalNetSalary(){
        double total = 0;
        List<Employee> employees = employeeRepository.findByDepartmentIgnoreCase("HR");
        for (Employee employee : employees){
            List<Payroll> payrolls = payrollRepository.findByEmployeeId(employee.getEmployeeId());

            for (Payroll payroll :payrolls){
                total += payroll.getNetSalary();
            }
        }
        return total;
    }
}
