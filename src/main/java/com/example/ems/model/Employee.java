package com.example.ems.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {

    @Id
    private int id;

    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String supervisor;


    //Setter

    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setSupervisor(String supervisor){
        this.supervisor = supervisor;
    }

    //Getter

    public int getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmailId(){
        return emailId;
    }

    public String getRole(){
        return role;
    }

    public String getSupervisor(){
        return supervisor;
    }
}
