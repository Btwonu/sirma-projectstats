package com.home.projectstats.employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDao {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getAll() {
        String query = "SELECT * FROM employee";

        return jdbcTemplate.query(query, employeeRowMapper);
    }

    public Optional<Employee> getOne(long id) {
        String query = "SELECT * FROM employee WHERE id = ?";

        try {
            Employee employee = jdbcTemplate.queryForObject(query, employeeRowMapper, id);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int createOne(long id) {
        String query = "INSERT INTO employee (id) VALUES (?)";

        try {
            return jdbcTemplate.update(query, id);
        } catch (Exception e) {
            System.out.println("Duplicate row for employee");
            return 0;
        }
    }

    public void deleteOne(long id) {
        String query = "DELETE FROM employee WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
