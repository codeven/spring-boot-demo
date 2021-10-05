package com.example.demo.controller;

import com.example.demo.controller.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ConversionService conversionService;

    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("requesting employees list");
        final List<EmployeeDto> employees = employeeService.getEmployees()
                .stream()
                .map(item -> conversionService.convert(item, EmployeeDto.class))
                .collect(Collectors.toList());
        log.info("employees found {}", employees.size());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        final Employee employee = employeeService.getEmployee(employeeId);
        return ResponseEntity.ok().body(conversionService.convert(employee, EmployeeDto.class));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto) {
        final Long employeeId = employeeService.addEmployee(conversionService.convert(employeeDto, Employee.class));
        return ResponseEntity.created(URI.create("/api/v1/employees/".concat(employeeId.toString()))).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.removeEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
