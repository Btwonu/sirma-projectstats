package com.home.projectstats.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    public Optional<Employee> getOneEmployee(long id) {
        return employeeDao.getOne(id);
    }

    public int createOneEmployee(long id) {
        return employeeDao.createOne(id);
    }

    public void deleteOneEmployee(long id) {
        employeeDao.deleteOne(id);
    }
}
