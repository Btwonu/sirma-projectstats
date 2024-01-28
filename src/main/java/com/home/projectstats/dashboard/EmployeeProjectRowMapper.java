package com.home.projectstats.dashboard;

import com.home.projectstats.employee.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeProjectRowMapper implements RowMapper<EmployeePairDto> {
    @Override
    public EmployeePairDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Map<Long, Integer> overlappingProjects = new HashMap<>();

        Array overlappingProjectIds = resultSet.getArray("overlapping_project_ids");
        Array overlappingDurations = resultSet.getArray("overlapping_durations");

        Long[] overlappingProjectIdsArray = (Long[]) overlappingProjectIds.getArray();
        Integer[] overlappingDurationsArray = (Integer[]) overlappingDurations.getArray();

        for (int i = 0; i < overlappingProjectIdsArray.length; i++) {
            Long projectId = overlappingProjectIdsArray[i];
            Integer duration = overlappingDurationsArray[i];

            if (overlappingProjects.containsKey(projectId)) {
                overlappingProjects.put(projectId, overlappingProjects.get(projectId) + duration);
                continue;
            }

            overlappingProjects.put(projectId, duration);
        }

        return new EmployeePairDto(
                mapEmployee(resultSet.getLong("employeea_id")),
                mapEmployee(resultSet.getLong("employeeb_id")),
                resultSet.getInt("total_overlap"),
                overlappingProjects
        );
    }

    public Employee mapEmployee(Long id) {
        return new Employee(id);
    }
}
