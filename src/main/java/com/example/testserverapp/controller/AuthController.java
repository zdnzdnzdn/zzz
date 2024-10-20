package com.example.testserverapp.controller;

import com.example.testserverapp.entity.Employee;
import com.example.testserverapp.entity.Role;
import com.example.testserverapp.model.request.LoginRequest;
import com.example.testserverapp.model.request.RegistrationRequest;
import com.example.testserverapp.model.response.LoginResponse;
import com.example.testserverapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Employee registration(@RequestBody RegistrationRequest registrationRequest) {
        return authService.registration(registrationRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/auth/{idEmployee}/roles")
    public Employee addRole(@PathVariable Integer idEmployee, @RequestBody Role role) {
        return authService.addRole(idEmployee, role);
    }

    @PutMapping("/auth/{idEmployee}/roles/{roleId}")
    public Employee updateRole(@PathVariable Integer idEmployee, @PathVariable Integer roleId, @RequestBody Role updatedRole) {
        return authService.updateRole(idEmployee, roleId, updatedRole);
    }

    @DeleteMapping("/auth/{idEmployee}/roles/{roleId}")
    public void deleteRole(@PathVariable Integer idEmployee, @PathVariable Integer roleId) {
        authService.deleteRole(idEmployee, roleId);
    }
}