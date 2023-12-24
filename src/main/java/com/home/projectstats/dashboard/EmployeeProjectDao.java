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

    public EmployeeProjectDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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

    public int createOne(long employeeId, long projectId, LocalDate from, LocalDate to) {
        String query = """
                    INSERT INTO employee_project (employee_id, project_id, from_date, to_date)
                    SELECT ?, ?, ?, ?
                    WHERE NOT EXISTS (
                       SELECT 1
                       FROM employee_project ep
                       WHERE ep.employee_id = ?
                         AND ep.project_id = ?
                         AND (?, ?) OVERLAPS (ep.from_date, ep.to_date)
                   )
                """;


        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int i = 1;
            preparedStatement.setLong(i++, employeeId);
            preparedStatement.setLong(i++, projectId);
            preparedStatement.setDate(i++, Date.valueOf(from));
            preparedStatement.setDate(i++, Date.valueOf(to));
            preparedStatement.setLong(i++, employeeId);
            preparedStatement.setLong(i++, projectId);
            preparedStatement.setDate(i++, Date.valueOf(from));
            preparedStatement.setDate(i++, Date.valueOf(to));

            return preparedStatement.executeUpdate();
        } catch (PSQLException e) {
            e.printStackTrace();
            System.out.println("Duplicate row for employee-project");
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating employee-project", e);
        }
    }
}
