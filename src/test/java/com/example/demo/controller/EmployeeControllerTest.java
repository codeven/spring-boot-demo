package com.example.demo.controller;

import com.example.demo.controller.dto.EmployeeDto;
import com.example.demo.controller.mapper.EmployeeModelToDtoMapperImpl;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @SpyBean
    GenericConversionService conversionService;

    @SpyBean
    EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        conversionService.addConverter(new EmployeeModelToDtoMapperImpl());
    }

    @Test
    void test_getEmployee_whenIdExists_thenReturnOk() throws Exception {
        // given
        final Long id = 123L;
        final Employee employee = Employee.builder()
                .employeeId(id)
                .email("jdoe99@webmail.com")
                .firstName("John")
                .lastName("Doe")
                .startDate(LocalDate.now().minusMonths(6))
                .endDate(LocalDate.now())
                .build();
        // when
        when(employeeService.getEmployee(eq(id))).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/".concat(id.toString())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(employee.getEmployeeId().toString())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())))
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.startDate", is(notNullValue())))
                .andExpect(jsonPath("$.endDate", is(notNullValue())));
        // then
        verify(employeeController, times(1)).getEmployeeById(eq(id));
        verify(conversionService, times(1)).convert(eq(employee), eq(EmployeeDto.class));
    }

    @Test
    void test_getEmployee_whenIdNotExists_thenReturnNotFound() throws Exception {
        // given
        final Long id = 333L;

        // when
        when(employeeService.getEmployee(eq(id))).thenThrow(new EmployeeNotFoundException(id));

        mockMvc.perform(get("/api/v1/employees/".concat(id.toString())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", is(notNullValue())))
                .andExpect(jsonPath("$.message", is("Employee not found with id 333")))
                .andExpect(jsonPath("$.details", is("uri=/api/v1/employees/333")));
        // then
        verify(employeeController, times(1)).getEmployeeById(eq(id));
        verify(conversionService, never()).convert(any(Employee.class), eq(EmployeeDto.class));
    }
}
