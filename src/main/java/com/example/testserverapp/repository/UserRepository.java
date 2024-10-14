package com.example.testserverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testserverapp.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}