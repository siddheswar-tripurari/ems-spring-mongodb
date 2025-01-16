package com.example.ems.controller;


import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setup(){
        employee = new Employee();
        employee.setId(11011);
        employee.setFirstName("Siddhu");
        employee.setLastName("T");
        employee.setEmailId("siddhu@gmail.com");
        employee.setRole("SET");
        employee.setSupervisor("Ejaz Ansari");
    }


//    @Test
//    void testCreateEmployeeSuccessfully() throws Exception {
//
//        doNothing().when(employeeService).createEmployee(employee);
//
//        mockMvc.perform(post("/api/employee/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"id\":11011," +
//                                "\"firstName\":\"Siddhu\"," +
//                                "\"lastName\":\"T\"," +
//                                "\"emailId\":\"siddhu@gmail.com\"," +
//                                "\"role\":\"SET\"," +
//                                "\"supervisor\":\"Ejaz Ansari\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Employee Created Successfully."));
//
//        verify(employeeService, times(1)).createEmployee(any(Employee.class));
//    }

    @Test
    void testGetAllEmployeesSuccessfully() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeByIdSuccessfully() throws Exception {

        when(employeeService.getEmployeeById(11011)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employee/get/11011"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Siddhu"));

        verify(employeeService, times(1)).getEmployeeById(11011);
    }

    @Test
    void testDeleteEmployeeSuccessfully() throws Exception {
        doNothing().when(employeeService).deleteEmployee(11011);

        mockMvc.perform(delete("/api/employee/delete/11011"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee Deleted Successfully."));

        verify(employeeService, times(1)).deleteEmployee(11011);
    }

    @Test
    void testUpdateEmployeeSuccessfully() throws Exception {
        doNothing().when(employeeService).updateEmployee(eq(11011), any(Employee.class));

        mockMvc.perform(put("/api/employee/update/11011")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Siddheswar\",\"lastName\":\"Tripurari\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully."));

        verify(employeeService, times(1)).updateEmployee(eq(11011), any(Employee.class));
    }

    @Test
    void testCreateEmployeeThrowsExceptionWhenCreatingEmployee() throws Exception {
        doThrow(new RuntimeException("Employee already exists")).when(employeeService).createEmployee(any(Employee.class));

        mockMvc.perform(post("/api/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":11011,\"firstName\":\"Siddhu\",\"lastName\":\"T\",\"emailId\":\"siddhu@gmail.com\",\"role\":\"SET\",\"supervisor\":\"Ejaz Ansari\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee already exists"));

        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }

    @Test
    void testGetEmployeeByIdThrowsExceptionWhenEmployeeNotFound() throws Exception {
        when(employeeService.getEmployeeById(11011)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employee/get/11011"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee not found."));

        verify(employeeService, times(1)).getEmployeeById(11011);
    }

    @Test
    void testDeleteEmployeeThrowsExceptionWhenNotFound() throws Exception {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployee(11011);

        mockMvc.perform(delete("/api/employee/delete/11011"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee not found"));

        verify(employeeService, times(1)).deleteEmployee(11011);
    }

    @Test
    void testUpdateEmployee_ThrowsExceptionWhenNotFound() throws Exception {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).updateEmployee(eq(11011), any(Employee.class));

        mockMvc.perform(put("/api/employee/update/11011")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"emailId\":\"jane.doe@example.com\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee not found"));

        verify(employeeService, times(1)).updateEmployee(eq(11011), any(Employee.class));
    }



}
