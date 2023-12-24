package com.home.projectstats.util;

import com.home.projectstats.dashboard.DashboardService;
import com.home.projectstats.employee.EmployeeService;
import com.home.projectstats.project.ProjectService;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DataLoader {
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final DashboardService dashboardService;

    public DataLoader(EmployeeService employeeService, ProjectService projectService, DashboardService dashboardService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.dashboardService = dashboardService;
    }

    public void init() {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/input.csv"), true)) {
            String[] line;
            while ((line = csvReader.readLine()) != null) {
                long employeeId = Long.parseLong(line[0]);
                long projectId = Long.parseLong(line[1]);
                String from = line[2];
                String to = line[3];

                if (to.equals("NULL")) {
                    to = LocalDate.now().toString();
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate fromDate = null;
                LocalDate toDate = null;
                try {
                    fromDate = LocalDate.parse(from, formatter);
                    toDate = LocalDate.parse(to, formatter);
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }

                try {
                    employeeService.createOneEmployee(employeeId);
                } catch (Exception e) {
                    System.out.println("Employee already exists");
                }

                projectService.createOneProject(projectId);
                dashboardService.createOneEmployeeProject(employeeId, projectId, fromDate, toDate);
            }
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
