package com.home.projectstats.employee;

import com.home.projectstats.project.Project;
import com.home.projectstats.project.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeController(EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @RequestMapping({ "", "/" })
    public String index(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @RequestMapping("/{id}")
    public String getEmployee(@PathVariable long id, Model model, HttpServletResponse response) {
        Optional<Employee> optional = employeeService.getOneEmployee(id);
        List<Project> employeeProjects = projectService.getEmployeeProjects(id);

        if (optional.isPresent()) {
            model.addAttribute("employee", optional.get());
            model.addAttribute("projects", employeeProjects);
            return "employee";
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("message", "Employee not found");
            return "not-found";
        }
    }

    @PostMapping({ "", "/" })
    public String addEmployee(Employee employee, RedirectAttributes redirectAttributes) {
        try {
            employeeService.createOneEmployee(employee.getId());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/employees";
    }
}
