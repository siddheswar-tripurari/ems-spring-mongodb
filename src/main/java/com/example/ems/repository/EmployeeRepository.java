package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,Integer> {

    @Query("{'status' : 'Active'}")
    List<Employee> findAllActiveEmployees();
}
