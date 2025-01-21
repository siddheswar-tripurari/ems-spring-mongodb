package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createEmployee(Employee employee){
        id += 1;
        employee.setId(id);
        employee.setStatus("Active");
        employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(int id) {
            return employeeRepository.findById(id);
    }

    public void deleteEmployee(int id){
        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("Employee Not Found. Deleted unsuccessfully.");
        }
        Employee emp = employeeRepository.findById(id).get();
        emp.setStatus("In-Active");
        employeeRepository.save(emp);

    }

    public void updateEmployee(int id, Employee employee1){
        try {
            Employee update = employeeRepository.findById(id).orElseThrow(()-> new Exception("Error"));
            if(update.getStatus().equals("Active")){
                update.setFirstName(employee1.getFirstName());
                update.setLastName(employee1.getLastName());
                update.setEmailId(employee1.getEmailId());
                update.setRole(employee1.getRole());
                update.setSupervisor(employee1.getSupervisor());
                employeeRepository.save(update);
            }else{
                throw new RuntimeException("Employee with ID not found. Update unsuccessful.");
            }
        } catch (Exception e){
            throw new RuntimeException("Employee with ID not found. Update unsuccessful.");
        }
    }
}
