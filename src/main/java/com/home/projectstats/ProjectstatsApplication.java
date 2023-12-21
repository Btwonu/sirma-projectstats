package com.home.projectstats;

import com.home.projectstats.dashboard.DashboardService;
import com.home.projectstats.employee.Employee;
import com.home.projectstats.project.Project;
import com.home.projectstats.dashboard.EmployeePairDto;
import com.home.projectstats.dashboard.EmployeeProjectDao;
import com.home.projectstats.employee.EmployeeService;
import com.home.projectstats.project.ProjectService;
import com.home.projectstats.util.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.Option;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ProjectstatsApplication {
	private static EmployeeService employeeService;
	private static ProjectService projectService;
	private static DashboardService dashboardService;

	ProjectstatsApplication(EmployeeService employeeService, ProjectService projectService, DashboardService dashboardService) {
		ProjectstatsApplication.employeeService = employeeService;
		ProjectstatsApplication.projectService = projectService;
		ProjectstatsApplication.dashboardService = dashboardService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectstatsApplication.class, args);

		try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/input.csv"), true)) {
			String[] line;
			while ((line = csvReader.readLine()) != null) {
                long employeeId = Long.parseLong(line[0]);
                long projectId = Long.parseLong(line[1]);
                String from = line[2];
                String to = line[3];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate fromDate = null;
                LocalDate toDate = null;
                try {
                    fromDate = LocalDate.parse(from, formatter);
                    toDate = LocalDate.parse(to, formatter);

                    System.out.println("From Date: " + fromDate);
                    System.out.println("To Date: " + toDate);
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }

                employeeService.createOneEmployee(employeeId);
                projectService.createOneProject(projectId);
                dashboardService.createOneEmployeeProject(employeeId, projectId, fromDate, toDate);

                Employee employee = new Employee(employeeId);
            }
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}
}
