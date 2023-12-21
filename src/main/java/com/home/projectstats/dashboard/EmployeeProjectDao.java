package com.home.projectstats.dashboard;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class EmployeeProjectDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmployeeProjectDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    public int createOne(long employeeId, long projectId, LocalDate from, LocalDate to) {
        String query = """
            INSERT INTO employee_project (employee_id, project_id, from_date, to_date)
            VALUES (?, ?, ?, ?);
        """;

        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, employeeId);
            preparedStatement.setLong(2, projectId);
            preparedStatement.setDate(3, Date.valueOf(from));
            preparedStatement.setDate(4, Date.valueOf(to));

            return preparedStatement.executeUpdate();
        } catch (PSQLException e) {
            System.out.println("Duplicate row");
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating employee-project", e);
        }
    }
}
