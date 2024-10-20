package com.example.testserverapp.repository;

import java.util.Optional;
import com.example.testserverapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmployeeEmail(String username, String email);
}