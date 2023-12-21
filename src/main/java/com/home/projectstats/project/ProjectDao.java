package com.home.projectstats.project;

import com.home.projectstats.employee.Employee;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ProjectRowMapper projectRowMapper;

    public ProjectDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, ProjectRowMapper projectRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.projectRowMapper = projectRowMapper;
    }

    public List<Project> getEmployeeProjects(long id) {
        String query = """
            SELECT project.*
            FROM project
            JOIN employee_project ON project.id = employee_project.project_id
            WHERE employee_project.employee_id = :employeeId;
        """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("employeeId", id);

        return namedParameterJdbcTemplate.query(query, params, projectRowMapper);
    }

    public Optional<Project> getOne(long id) {
        String query = "SELECT * FROM project WHERE id = ?";

        try {
            Project project = jdbcTemplate.queryForObject(query, projectRowMapper, id);
            return Optional.ofNullable(project);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int createOne(long id) {
        String query = "INSERT INTO project (id) VALUES (?)";

        try {
            return jdbcTemplate.update(query, id);
        } catch (Exception e) {
            System.out.println("Duplicate row for project");
            return 0;
        }
    }
}
