package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    int id = 109750;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllActiveEmployees();
    }

    public void createEmployee(Employee newEmployee){
        if(employeeRepository.count() == 0){
            id += 1;
        }else{
            List<Employee> employees = employeeRepository.getEmployeesInDescendingOrder();
            id = employees.get(0).getId() + 1;
        }
        newEmployee.setId(id);
        newEmployee.setStatus(true);
        employeeRepository.save(newEmployee);
    }

    public Optional<Employee> getEmployeeById(int id) {
            return employeeRepository.findById(id);
    }

    public void deleteEmployee(int id){
        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("Employee Not Found. Deleted unsuccessfully.");
        }
        Employee deleteEmployee = employeeRepository.findById(id).get();
        deleteEmployee.setStatus(false);
        employeeRepository.save(deleteEmployee);
    }

    public void updateEmployee(int id, Employee updateEmployee){
        try {
            Employee updateEmployeeResponse = employeeRepository.findById(id).orElseThrow(()-> new Exception("Error"));
            if(updateEmployeeResponse.getStatus()){
                updateEmployeeResponse.setFirstName(updateEmployee.getFirstName());
                updateEmployeeResponse.setLastName(updateEmployee.getLastName());
                updateEmployeeResponse.setEmailId(updateEmployee.getEmailId());
                updateEmployeeResponse.setRole(updateEmployee.getRole());
                updateEmployeeResponse.setSupervisor(updateEmployee.getSupervisor());
                employeeRepository.save(updateEmployeeResponse);
            }else{
                throw new RuntimeException("Employee with ID not found. Update unsuccessful.");
            }
        } catch (Exception e){
            throw new RuntimeException("Employee with ID not found. Update unsuccessful.");
        }
    }
}
