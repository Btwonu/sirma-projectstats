package com.home.projectstats;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjectDao {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EmployeePairDto> getOverlappingProjects() {
        String query = """
            SELECT
            a.employee_id AS employeeA_id,
            b.employee_id AS employeeB_id,
            ARRAY_AGG(a.project_id) AS overlapping_project_ids,
            ARRAY_AGG(
                LEAST(
                    a.to_date - a.from_date,
                    b.to_date - b.from_date,
                    a.to_date - b.from_date,
                    b.to_date - a.from_date
                )
            ) AS overlapping_durations,
            SUM(
                LEAST(
                    a.to_date - a.from_date,
                    b.to_date - b.from_date,
                    a.to_date - b.from_date,
                    b.to_date - a.from_date
                )
            ) AS total_overlap
            FROM employee_project a
            JOIN employee_project b ON a.project_id = b.project_id
            WHERE a.employee_id < b.employee_id
            AND (a.from_date, a.to_date) OVERLAPS (b.from_date, b.to_date)
            GROUP BY a.employee_id, b.employee_id
            ORDER BY total_overlap DESC;
        """;

        return jdbcTemplate.query(query, new EmployeeProjectRowMapper());
    }
}
