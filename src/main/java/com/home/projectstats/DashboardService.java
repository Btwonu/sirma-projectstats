package com.home.projectstats;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    private final EmployeeProjectDao employeeProjectDao;

    public DashboardService(EmployeeProjectDao employeeProjectDao) {
        this.employeeProjectDao = employeeProjectDao;
    }

    public List<EmployeePairDto> getEmployeePairs() {
        return employeeProjectDao.getOverlappingProjects();
    }
}
