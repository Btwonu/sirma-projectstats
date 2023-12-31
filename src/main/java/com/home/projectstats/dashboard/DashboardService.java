package com.home.projectstats.dashboard;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public int createOneEmployeeProject(long employeeId, long projectId, LocalDate from, LocalDate to) {
        return employeeProjectDao.createOne(employeeId, projectId, from, to);
    }
}
