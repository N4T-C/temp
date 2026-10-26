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


/*
package com.example.wad1.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AnalyticsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public double getTotalNetSalary() {
        Aggregation aggregation = Aggregation.newAggregation(
            // 1. Case-insensitive match for HR department
            match(Criteria.where("department").regex("^HR$", "i")),
            
            // 2. Lookup (join) employee with payroll on employeeId
            lookup("payroll", "employeeId", "employeeId", "payrolls"),
            
            // 3. Flatten payrolls array
            unwind("payrolls"),
            
            // 4. Group and sum netSalary
            group().sum("payrolls.netSalary").as("total")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
            aggregation, "employee", Document.class
        );

        Document doc = results.getUniqueMappedResult();
        if (doc != null && doc.get("total") != null) {
            return ((Number) doc.get("total")).doubleValue();
        }
        return 0.0;
    }
}
*/