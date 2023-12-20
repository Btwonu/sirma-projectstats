package com.home.projectstats.project;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List<Project> getEmployeeProjects(long id) {
        return projectDao.getEmployeeProjects(id);
    }
}
