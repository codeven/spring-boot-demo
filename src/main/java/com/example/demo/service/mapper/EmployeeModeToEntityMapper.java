package com.example.demo.service.mapper;

import com.example.demo.dao.entity.EmployeeEntity;
import com.example.demo.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface EmployeeModeToEntityMapper extends Converter<Employee, EmployeeEntity> {

    @Mapping(source = "employeeId", target = "id")
    @Override
    EmployeeEntity convert(Employee source);
}
