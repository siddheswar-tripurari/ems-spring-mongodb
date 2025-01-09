package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void createEmployee(Employee employee){
        if(employeeRepository.existsById(employee.getId())){
            throw new RuntimeException("Error: Employee with ID " + employee.getId() + " already exists.");
        }
        employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(int id) {
            return employeeRepository.findById(id);
    }

    public void deleteEmployee(int id){
        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("Employee Not Found. Deleted unsuccessfully.");
        }
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(int id, Employee employee1){
        try {
            Employee update = employeeRepository.findById(id).orElseThrow(()-> new Exception("Error"));
            update.setFirstName(employee1.getFirstName());
            update.setLastName(employee1.getLastName());
            update.setEmailId(employee1.getEmailId());
            update.setRole(employee1.getRole());
            update.setSupervisor(employee1.getSupervisor());
            employeeRepository.save(update);
        } catch (Exception e){
            throw new RuntimeException("Employee with ID not found. Update unsuccessful.");
        }
    }
}
