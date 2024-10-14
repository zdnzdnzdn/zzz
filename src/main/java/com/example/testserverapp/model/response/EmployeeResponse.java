package com.example.testserverapp.model.response;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private UserResponse user; 
}

