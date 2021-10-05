package com.example.demo.service.mapper;

import com.example.demo.dao.entity.EmployeeEntity;
import com.example.demo.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface EmployeeEntityToModelMapper extends Converter<EmployeeEntity, Employee> {

    @Mapping(source = "id", target = "employeeId")
    @Override
    Employee convert(EmployeeEntity source);
}
