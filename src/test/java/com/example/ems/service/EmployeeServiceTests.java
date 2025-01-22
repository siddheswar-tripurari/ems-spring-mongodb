package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests{

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetAllEmployees(){

        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setRole("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        //Mock
        when(employeeRepository.findAllActiveEmployees()).thenReturn(List.of(employee));

        // Act
        List<Employee> getAllEmployees = employeeService.getAllEmployees();

        //Assert
        org.junit.jupiter.api.Assertions.assertEquals("John",getAllEmployees.getFirst().getFirstName());
        org.junit.jupiter.api.Assertions.assertEquals(true,getAllEmployees.getFirst().isActive());
        verify(employeeRepository,times(1)).findAllActiveEmployees();

    }

    @Test
    void testCreateEmployeeShouldCreateWithNewEmployeeId(){

        // Arrange

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setRole("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        when(employeeRepository.count()).thenReturn(0L);
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        employeeService.createEmployee(employee);

        // Assert
        org.junit.jupiter.api.Assertions.assertEquals(109751,employee.getId());
        verify(employeeRepository,times(1)).save(employee);
    }

    @Test
    void testCreateEmployeeShouldCreateEmployeeIdByTakingPreviousEmployeeID(){

        // Arrange

        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        Employee newEmp = new Employee();
        newEmp.setFirstName("John");
        newEmp.setLastName("Doe");
        newEmp.setEmailId("John.Doe@vonage.com");
        newEmp.setRole("Manager");
        newEmp.setSupervisor("Jane Doe");
        newEmp.setActive(true);

        List<Employee> prevEmployee = new ArrayList<>();
        prevEmployee.add(employee);

        //Mock
        when(employeeRepository.count()).thenReturn(1L);
        when(employeeRepository.getEmployeesInDescendingOrder()).thenReturn(prevEmployee);

        // Act
        employeeService.createEmployee(newEmp);

        // Assert
        Assertions.assertEquals(10002,newEmp.getId());
        verify(employeeRepository,times(1)).getEmployeesInDescendingOrder();
    }

    @Test
    void testGetEmployeeById(){

        // Arrange

        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        // Mock
        when(employeeRepository.findById(10001)).thenReturn(Optional.of(employee));

        // Act
        Optional<Employee> getEmployee = employeeService.getEmployeeById(10001);

        // Assert
        Assertions.assertEquals("John",getEmployee.get().getFirstName());
        Assertions.assertNotNull(getEmployee);
    }

    @Test
    void testDeleteEmployeeShouldMakeEmployeeInactive(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        // Mock
        when(employeeRepository.existsById(10001)).thenReturn(true);
        when(employeeRepository.findById(10001)).thenReturn(Optional.of(employee));

        // Act
        employeeService.deleteEmployee(10001);

        // Assert
        Assertions.assertEquals(false,employee.isActive());
    }

    @Test
    void testDeleteEmployeeShouldThrowException(){


        // Mock
        when(employeeRepository.existsById(10002)).thenReturn(false);

        // Act
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,()->employeeService.deleteEmployee(10002));

        // Assert
        Assertions.assertEquals("Employee Not Found. Deleted unsuccessfully.", exception.getMessage());
        verify(employeeRepository, never()).findById(10002);

    }

    @Test
    void testUpdateEmployeeShouldUpdateActiveEmployee(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("Jonah");
        updateEmployee.setLastName("Johnson");
        updateEmployee.setEmailId("Johnson.J@vonage.com");
        updateEmployee.setRole("Manager");
        updateEmployee.setSupervisor("Jane Doe");


        // Mock
        when(employeeRepository.findById(10001)).thenReturn(Optional.of(employee));

        // Act
        employeeService.updateEmployee(10001,updateEmployee);

        // Assert
        Assertions.assertEquals("Jonah",employee.getFirstName());
        Assertions.assertEquals("Johnson",employee.getLastName());
        verify(employeeRepository,times(1)).findById(10001);

    }

    @Test
    void testUpdateEmployeeShouldThrowErrorWhenUpdateInactiveEmployee(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(false);

        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("Jonah");
        updateEmployee.setLastName("Johnson");
        updateEmployee.setEmailId("Johnson.J@vonage.com");
        updateEmployee.setRole("Manager");
        updateEmployee.setSupervisor("Jane Doe");


        // Mock
        when(employeeRepository.findById(10001)).thenReturn(Optional.of(employee));

        // Act
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,()->employeeService.updateEmployee(10001,updateEmployee));

        // Assert

        Assertions.assertEquals("Employee with ID not found. Update unsuccessful.", exception.getMessage());

    }

    @Test
    void testEmployeeShouldThrowErrorWhenEmployeeNotFound(){

        // Arrange

        Employee employee = new Employee();
        employee.setId(10001);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmailId("John.Doe@vonage.com");
        employee.setRole("Manager");
        employee.setSupervisor("Jane Doe");
        employee.setActive(true);

        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("Jonah");
        updateEmployee.setLastName("Johnson");
        updateEmployee.setEmailId("Johnson.J@vonage.com");
        updateEmployee.setRole("Manager");
        updateEmployee.setSupervisor("Jane Doe");

        // Mock
        when(employeeRepository.findById(10002)).thenReturn(Optional.empty());

        // Act

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,()->employeeService.updateEmployee(10002,updateEmployee));

        // Assert

        Assertions.assertEquals("Employee with ID not found. Update unsuccessful.", exception.getMessage());
    }




}
