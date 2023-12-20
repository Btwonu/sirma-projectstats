package com.home.projectstats.project;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ProjectMapper projectMapper;

    public ProjectDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, ProjectMapper projectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.projectMapper = projectMapper;
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

        return namedParameterJdbcTemplate.query(query, params, projectMapper);
    }
}
