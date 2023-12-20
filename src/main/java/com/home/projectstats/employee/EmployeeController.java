package com.home.projectstats.employee;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }

    @RequestMapping({ "", "/" })
    public String index(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @RequestMapping("/{id}")
    public String getEmployee(@PathVariable long id, Model model, HttpServletResponse response) {
        Optional<Employee> optional = employeeService.getOneEmployee(id);

        if (optional.isPresent()) {
            model.addAttribute("employee", optional.get());
            return "employee";
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model.addAttribute("message", "Employee not found");
            return "not-found";
        }
    }
}
