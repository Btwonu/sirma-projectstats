package com.home.projectstats.employee;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

@Getter
public class Employee {
    private final long id;

    Employee(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", jobPeriods=" +
                '}';
    }
}
