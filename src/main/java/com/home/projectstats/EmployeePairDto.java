package com.home.projectstats;

import java.util.Map;

public record EmployeePairDto(
        Employee employeeA,
        Employee employeeB,
        int totalDaysOverlap,
        Map<Long, Integer> overlappingProjects
) {}
