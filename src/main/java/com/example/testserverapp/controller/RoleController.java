package com.example.testserverapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.testserverapp.entity.Role;
import com.example.testserverapp.model.request.RoleRequest;
import com.example.testserverapp.model.response.RoleResponse;
import com.example.testserverapp.service.RoleService;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Get all roles
    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Get role by ID
    @GetMapping("/id")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(mapToResponse(role));
    }

    // Create new role
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        Role role = mapToEntity(roleRequest);
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(mapToResponse(createdRole), HttpStatus.CREATED);
    }

    // Update role by ID
    @PutMapping("/id")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        Role roleDetails = mapToEntity(roleRequest);
        Role updatedRole = roleService.updateRole(id, roleDetails);
        return ResponseEntity.ok(mapToResponse(updatedRole));
    }

    // Delete role by ID
    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Mapping methods
    private RoleResponse mapToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }

    private Role mapToEntity(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
        return role;
    }
}
