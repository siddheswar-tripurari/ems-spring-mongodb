package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,Integer> {

    @Query("{'active' : true}")
    List<Employee> findAllActiveEmployees();

    @Query(value = "{}", sort = "{'_id': -1}")
    List<Employee> getEmployeesInDescendingOrder();

}
