package com.home.projectstats.project;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List<Project> getEmployeeProjects(long id) {
        return projectDao.getEmployeeProjects(id);
    }

    public Optional<Project> getOneProject(long id) {
        return projectDao.getOne(id);
    }

    public int createOneProject(long id) {
        return projectDao.createOne(id);
    }
}
