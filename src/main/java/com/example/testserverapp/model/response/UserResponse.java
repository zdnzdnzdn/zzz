package com.example.testserverapp.model.response;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private Set<RoleResponse> roles; 
}

