package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests{

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

//    private Employee employee;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        employee = new Employee();
//        employee.setId(109756);
//        employee.setFirstName("Siddhu");
//        employee.setLastName("T");
//        employee.setEmailId("siddhu@gmail.com");
//        employee.setRole("SET");
//        employee.setSupervisor("Ejaz Ansari");
//    }

    @Test
    void testCreateEmployee_Successfully(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        //Mocking
        when(employeeRepository.existsById(employee.getId())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        //Act
        employeeService.createEmployee(employee);

        //Assert
        Assertions.assertThat(employee.getId()).isEqualTo(109756);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployees(){
        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        // Mocking
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        // Act
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // Assert
        Assertions.assertThat(allEmployees.size()).isEqualTo(1);
    }

    @Test
    void testGetEmployeeById(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        //Mocking
        when(employeeRepository.findById(109756)).thenReturn(Optional.of(employee));

        // Act
        Optional<Employee> emp = employeeService.getEmployeeById(109756);

        Assertions.assertThat(emp.get().getFirstName()).isEqualTo("Siddhu");
        Assertions.assertThat(emp.get().getLastName()).isNotEmpty();
    }

    @Test
    void testGetEmployeeById_NotFound(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        //Mock
        when(employeeRepository.findById(109757)).thenReturn(Optional.empty());

        //Act
        Optional<Employee> emp = employeeService.getEmployeeById(109757);

        //Assert
        Assertions.assertThat(emp).isEmpty();
    }

    @Test
    void testDeleteEmployee(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        //Mock
        when(employeeRepository.existsById(109756)).thenReturn(true);

        //Act
        employeeService.deleteEmployee(109756);

        // Assert
        verify(employeeRepository, times(1)).deleteById(109756);
    }

    @Test
    void testUpdateEmployee(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        // Mock
        when(employeeRepository.findById(109756)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updated = new Employee();
        updated.setFirstName("Siddheswar");
        updated.setLastName("Tripurari");
        updated.setEmailId("siddhu@gmail.com");
        updated.setRole("SET");
        updated.setSupervisor("Ejaz Ansari");

        //Act
        employeeService.updateEmployee(109756, updated);

        //Assert
        Assertions.assertThat(employee.getFirstName()).isEqualTo("Siddheswar");
        Assertions.assertThat(employee.getLastName()).isNotEqualTo("T");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testCreateEmployeeThrowsExceptionWhenEmployeeAlreadyExists(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        // Mock
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);

        //Act and Assert
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->employeeService.createEmployee(employee));

    }

    @Test
    void testDeleteEmployeeThrowsExceptionWhenEmployeeNotFound(){

        // Arrange
        Employee employee = new Employee();
        employee.setId(109756);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");

        when(employeeRepository.existsById(109757)).thenReturn(false);

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->employeeService.deleteEmployee(employee.getId()));
    }

    @Test
    void testUpdateEmployeeThrowsExceptionWhenEmployeeNotFound(){

        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("siddhu");

        when(employeeRepository.findById(109757)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->employeeService.updateEmployee(109757,
                updateEmployee)
        );
    }

    @Test
    void testDeleteEmployeeThrowsEmployeeNotFoundException(){
        when(employeeRepository.existsById(109756)).thenReturn(false);
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,()->employeeService.deleteEmployee(109756));
    }


}
