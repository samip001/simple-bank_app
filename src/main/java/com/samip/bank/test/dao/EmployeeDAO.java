package com.samip.bank.test.dao;

import com.samip.bank.test.model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeDAO {

    Employee insertEmployee(Employee employee);

    Optional<Employee> findEmployee(Long id);

    Employee checkEmailExit(String email);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    boolean removeEmployee(Long id);
}
