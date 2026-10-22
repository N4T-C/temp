
package com.example.wad1.controller;

import com.example.wad1.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // GET: Total net salary for HR employees
    @GetMapping("/totalNET")
    public double totalNET() {
        return analyticsService.getTotalNetSalary();
    }
}
