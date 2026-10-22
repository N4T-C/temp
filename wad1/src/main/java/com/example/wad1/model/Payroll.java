package com.example.wad1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employee")
public class Payroll {
    @Id
    private String id;
    private String payrollId;
    private String employeeId;
    private double bonus;
    private String deductions;
    private double netSalary;
    private String month;
}
