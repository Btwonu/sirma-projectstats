package com.home.projectstats.dashboard;

import com.home.projectstats.employee.Employee;

import java.util.Map;

public record EmployeePairDto(
        Employee employeeA,
        Employee employeeB,
        int totalDaysOverlap,
        Map<Long, Integer> overlappingProjects
) {}
