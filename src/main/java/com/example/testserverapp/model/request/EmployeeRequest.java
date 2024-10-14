package com.example.testserverapp.model.request;
import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private String email;
    private String phone;
}
