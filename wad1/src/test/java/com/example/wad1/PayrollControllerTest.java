
package com.example.wad1;

import com.example.wad1.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
class PayrollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Test 1: Get IT employees
    @Test
    void testGetITEmployees() throws Exception {

        mockMvc.perform(get("/api/ITemployees"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(
                                MediaType.APPLICATION_JSON));
    }

    // Test 2: Add a new employee
    @Test
    void testAddEmployee() throws Exception {

        String employeeJson = """
        {
            "employeeId": "TEST901",
            "employeeName": "Arun Kumar",
            "department": "IT",
            "basicSalary": 35000,
            "designation": "Developer"
        }
        """;

        mockMvc.perform(post("/api/employee/addNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId")
                        .value("TEST901"))
                .andExpect(jsonPath("$.employeeName")
                        .value("Arun Kumar"));
    }

    @Test
    void testTotalNetSalary() throws Exception {
        mockMvc.perform(get("/api/analytics/totalNET"))
                .andExpect(status().isOk());
    }
}
