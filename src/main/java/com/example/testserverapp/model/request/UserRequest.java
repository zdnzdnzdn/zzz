package com.example.testserverapp.model.request;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Long employeeId; 
}

