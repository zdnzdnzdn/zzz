package com.example.testserverapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testserverapp.repository.RoleRepository;
import com.example.testserverapp.repository.UserRepository;
import com.example.testserverapp.entity.Role;
import com.example.testserverapp.entity.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    // Create new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User  not found with id " + id);
        }
    }

    // Delete user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    // Assign role to user
    public User assignRoleToUser (Long userId, Long roleId) {
        User user = getUserById(userId);
        if (user != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException("Role not found with id " + roleId));
            if (role != null) {
                user.getRoles().add(role);
                return userRepository.save(user);
            } else {
                throw new RoleNotFoundException("Role not found with id " + roleId);
            }
        } else {
            throw new UserNotFoundException("User  not found with id " + userId);
        }
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}