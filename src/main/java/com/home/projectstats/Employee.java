package com.home.projectstats;

import java.util.HashMap;

public class Employee {
    private long id;
    private String name;
    private String email;
    private HashMap<Long, JobPeriod> jobPeriods;
}
