package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void testFindAllActiveEmployees(){

        // Arrange
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("John");
        newEmployee.setLastName("Doe");
        newEmployee.setEmailId("John.Doe@gmail.com");
        newEmployee.setRole("Manager");
        newEmployee.setSupervisor("Jane Doe");
        newEmployee.setActive(true);

        List<Employee> mockActiveEmployee = List.of(newEmployee);

        // Mocking
        when(employeeRepository.findAllActiveEmployees()).thenReturn(mockActiveEmployee);

        // Act
        List<Employee> allActiveEmployees = employeeRepository.findAllActiveEmployees();

        //Assert
        Assertions.assertNotNull(allActiveEmployees);
        Assertions.assertTrue(!allActiveEmployees.isEmpty());
        Assertions.assertEquals("John",allActiveEmployees.get(0).getFirstName());
        verify(employeeRepository,times(1)).findAllActiveEmployees();

    }

    @Test
    void testGetAllEmployeesInDescendingOrder(){

        // Arrange

        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmailId("John.Doe@gmail.com");
        employee1.setRole("Manager");
        employee1.setSupervisor("Jane Doe");
        employee1.setActive(true);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Doe");
        employee2.setEmailId("Jane.Doe@gmail.com");
        employee2.setRole("Manager");
        employee2.setSupervisor("Alex Doe");
        employee2.setActive(true);

        List<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(employee1);
        allEmployees.add(employee2);

        //Mocking
        when(employeeRepository.getEmployeesInDescendingOrder()).thenReturn(allEmployees.reversed());

        //Act
        List<Employee> employeeDescOrder = employeeRepository.getEmployeesInDescendingOrder();

        //Assert
        Assertions.assertEquals("Jane",employeeDescOrder.getFirst().getFirstName());
        verify(employeeRepository,times(1)).getEmployeesInDescendingOrder();

    }

}
