package com.example.demo.service.exception;

public class EmployeeNotFoundException extends ResourceNotFoundException {

    private static final String ERROR_MESSAGE = "Employee not found";

    public EmployeeNotFoundException(Long employeeId) {
        super(String.format("%s with id %d", ERROR_MESSAGE, employeeId));
    }
}
