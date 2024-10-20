package com.example.testserverapp.service;

import com.example.testserverapp.entity.User;
import com.example.testserverapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User  not found"));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Integer id, User user) {
        getById(id);
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}