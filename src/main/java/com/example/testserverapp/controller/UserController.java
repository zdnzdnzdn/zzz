package com.example.testserverapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.testserverapp.entity.User;
import com.example.testserverapp.model.request.UserRequest;
import com.example.testserverapp.model.response.RoleResponse;
import com.example.testserverapp.model.response.UserResponse;
import com.example.testserverapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Get user by ID
    @GetMapping("/id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(mapToResponse(user));
    }

    // Create new user
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = mapToEntity(userRequest);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(mapToResponse(createdUser), HttpStatus.CREATED);
    }

    // Update user by ID
    @PutMapping("/id")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User userDetails = mapToEntity(userRequest);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(mapToResponse(updatedUser));
    }

    // Assign role to user
    @PostMapping("/userId/roles/roleId")
    public ResponseEntity<UserResponse> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User updatedUser = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(mapToResponse(updatedUser));
    }

    // Mapping methods
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRoles(user.getRoles().stream().map(role -> {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            return roleResponse;
        }).collect(Collectors.toSet()));
        return response;
    }

    private User mapToEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
    
        return user;
    }
}
