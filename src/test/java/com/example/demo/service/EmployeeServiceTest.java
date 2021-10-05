package com.example.demo.service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dao.entity.EmployeeEntity;
import com.example.demo.model.Employee;
import com.example.demo.service.exception.EmployeeNotFoundException;
import com.example.demo.service.mapper.EmployeeEntityToModelMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    private EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @SpyBean
    GenericConversionService conversionService;

    @BeforeEach
    void setUp() {
        conversionService.addConverter(new EmployeeEntityToModelMapperImpl());
        employeeService = new EmployeeService(employeeRepository, conversionService);
    }

    @Test
    void test_getEmployee_whenIdExists() {
        // given
        final long employeeId = 111;
        final EmployeeEntity entity = buildEntity(employeeId);
        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(entity));
        final Employee employee = employeeService.getEmployee(employeeId);
        // then
        assertNotNull(employee);
        verify(conversionService, times(1)).convert(eq(entity), eq(Employee.class));
    }

    @Test
    void test_getEmployee_whenIdNotExists() {
        // given
        final long employeeId = 777;
        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        final EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployee(employeeId));
        // then
        assertEquals("Employee not found with id " + employeeId, exception.getMessage());
        verify(conversionService, never()).convert(any(EmployeeEntity.class), eq(Employee.class));
    }

    private EmployeeEntity buildEntity(long id) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(id);
        entity.setEmail("jdoe99@webmail.com");
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setStartDate(LocalDate.now().minusMonths(3));
        return entity;
    }
}
