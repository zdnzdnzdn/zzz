package com.example.testserverapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.testserverapp.repository.RoleRepository;
import com.example.testserverapp.entity.Role;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get role by ID
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null); // Return null if not found
    }

    // Create new role
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Update role
    public Role updateRole(Long id, Role roleDetails) {
        Role role = getRoleById(id);
        if (role != null) {
            role.setName(roleDetails.getName());
            return roleRepository.save(role);
        } else {
            return null; // Return null if role not found
        }
    }

    // Delete role
    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        if (role != null) {
            roleRepository.delete(role);
        }
    }
}
