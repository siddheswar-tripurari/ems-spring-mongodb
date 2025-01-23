package com.example.ems.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@Getter
@Setter
public class Employee {

    @Id
    private int id;

    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String supervisor;
    private boolean active;

}
