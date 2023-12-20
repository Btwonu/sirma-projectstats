package com.home.projectstats.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping("")
    public String index(Model model) {
        List<EmployeePairDto> employeePairs = dashboardService.getEmployeePairs();

        model.addAttribute("employeePairs", employeePairs);
        return "dashboard";
    }
}
