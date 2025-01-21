package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.limit;

public interface EmployeeRepository extends MongoRepository<Employee,Integer> {

    @Query("{'active' : true}")
    List<Employee> findAllActiveEmployees();

    @Query(value = "{}", sort = "{'_id': -1}")
    List<Employee> getEmployeesInDescendingOrder();

}
