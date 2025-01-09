package com.example.ems.repository;

import com.example.ems.model.Employee;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void testFindById(){
        //Arrange
        Employee emp = new Employee();
        emp.setId(111);
        emp.setFirstName("John");
        emp.setLastName("Wick");
        emp.setRole("SDE-II");
        emp.setEmailId("john.wick@gmail.com");
        emp.setSupervisor("Continental");

        employeeRepository.save(emp);

        // Act
        Optional<Employee> employee = employeeRepository.findById(emp.getId());
        // or Employee emp = employeeRepository.findBy(emp.getId()).get();
        // System.out.println(employee);

        //Assert
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(111,employee.get().getId());
    }

    @Test
    void testSaveEmployee(){
        //Arrange
        Employee saveEmployee = new Employee();
        saveEmployee.setId(11111);
        saveEmployee.setFirstName("John");
        saveEmployee.setLastName("Smith");
        saveEmployee.setEmailId("johnsmith@gmail.com");
        saveEmployee.setRole("SDE");
        saveEmployee.setSupervisor("Alex Smith");

        //Act
        Employee savedEmployee = employeeRepository.save(saveEmployee);

        //Assert
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(savedEmployee.getFirstName(),"John");
    }

    @Test
    void testDeleteEmployee() {
        //Arrange
        Employee emp = new Employee();
        emp.setId(1);

        employeeRepository.save(emp);

        //Act
        employeeRepository.deleteById(emp.getId());

        //Assert
        Assertions.assertFalse(employeeRepository.existsById(1));
    }

    @Test
    void testFindAllEmployees(){
        // Arrange
        Employee employee1 = new Employee();
        employee1.setId(1111);
        employee1.setFirstName("test");
        employee1.setLastName("001");
        employee1.setEmailId("test@gmail.com");
        employee1.setRole("SRE");
        employee1.setSupervisor("John");

        Employee employee2 = new Employee();
        employee2.setSupervisor("Alex");
        employee2.setRole("SDE");
        employee2.setId(111011);
        employee2.setEmailId("test@vonage.com");
        employee2.setFirstName("test02");
        employee2.setLastName("002");

        Employee savedEmployee1 = employeeRepository.save(employee1);
        Employee savedEmployee2 = employeeRepository.save(employee2);

        //Act
        List<Employee> allEmployees = employeeRepository.findAll();

        //Assert
        Assertions.assertNotNull(allEmployees);
        Assertions.assertTrue(allEmployees.size() > 0);
    }

}
