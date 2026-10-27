package com.example.demo;

import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private final EmployeeRepository repo;
    private final MongoTemplate mongoTemplate;

    public EmployeeController(
            EmployeeRepository repo,
            MongoTemplate mongoTemplate) {
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }

    // Display employees with their payroll details
    @GetMapping("/with-payroll")
    public List<Document> getEmployeesWithPayroll() {

        LookupOperation lookup =
                lookup("payroll", "employeeId", "empId", "payrollDetails");

        Aggregation aggregation = newAggregation(
                lookup,
                match(org.springframework.data.mongodb.core.query.Criteria
                        .where("dept").is("CSE"))
        );

        AggregationResults<Document> results =
                mongoTemplate.aggregate(
                        aggregation, "employees", Document.class);

        return results.getMappedResults();
    }
}