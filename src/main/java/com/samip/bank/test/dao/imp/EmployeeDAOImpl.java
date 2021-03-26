package com.samip.bank.test.dao.imp;

import com.samip.bank.test.dao.EmployeeDAO;
import com.samip.bank.test.model.Employee;
import com.samip.bank.test.repository.EmplyoeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeDAOImpl implements EmployeeDAO {

    private EmplyoeeRepository emplyoeeRepository;

    @Autowired
    public EmployeeDAOImpl(EmplyoeeRepository emplyoeeRepository){
        this.emplyoeeRepository = emplyoeeRepository;
    }

    @Override
    public Employee insertEmployee(Employee employee) {
        return emplyoeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findEmployee(Long id) {
        return emplyoeeRepository.findById(id);
    }

    @Override
    public Employee checkEmailExit(String email) {
        return emplyoeeRepository.checkEmailExits(email);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return emplyoeeRepository.findAll();
    }


    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> storeEmployee = emplyoeeRepository.findById(employee.getId());
        if(storeEmployee.isPresent()){
            return emplyoeeRepository.save(employee);
        }
        throw new RuntimeException("Employee not found");
    }

    @Override
    public boolean removeEmployee(Long id) {
        if(emplyoeeRepository.existsById(id)){
            System.out.println(emplyoeeRepository.findById(id).get().getEmail());
            emplyoeeRepository.delete(emplyoeeRepository.findById(id).get());
            return true;
        }
        return false;
    }
}
