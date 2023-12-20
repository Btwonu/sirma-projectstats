package com.home.projectstats.project;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Project {
    private long id;

    public Project(long id) {
        this.id = id;
    }
}
