package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/api/employee/create")
    public ResponseEntity<String> createEmployee(@RequestBody Employee newEmployee){
        try {
            employeeService.createEmployee(newEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Created Successfully.");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employeesList = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeesList);
    }

    @GetMapping("/api/employee/get/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id){
        try {
            Employee emp = employeeService.getEmployeeById(id).orElseThrow(()-> new RuntimeException("Employee not found."));
            return ResponseEntity.status(HttpStatus.OK).body(emp);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/employee/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        try{
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.OK).body("Employee Deleted Successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/api/employee/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employee){
        try {
            employeeService.updateEmployee(id,employee);
            return ResponseEntity.status(HttpStatus.OK).body("Employee updated successfully.");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
