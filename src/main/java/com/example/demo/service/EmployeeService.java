package com.example.demo.service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.entity.EmployeeEntity;
import com.example.demo.model.Employee;
import com.example.demo.service.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    private final ConversionService conversionService;

    public List<Employee> getEmployees() {
        return repository.findAll()
                .stream()
                .map(entity -> conversionService.convert(entity, Employee.class))
                .collect(Collectors.toList());
    }

    public Employee getEmployee(final Long employeeId) {
        return repository.findById(employeeId)
                .map(entity -> conversionService.convert(entity, Employee.class))
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Long addEmployee(final Employee employee) {
        EmployeeEntity entity = conversionService.convert(employee, EmployeeEntity.class);
        return repository.save(entity).getId();
    }

    public void removeEmployee(final Long employeeId) {
        repository.deleteById(employeeId);
    }
}
