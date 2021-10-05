package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Employee {

    private final Long employeeId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDate startDate;
    private LocalDate endDate;
}
