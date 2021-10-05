package com.example.demo.controller.mapper;

import com.example.demo.controller.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface EmployeeDtoToModelMapper extends Converter<EmployeeDto, Employee> {
}
