package com.example.testserverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testserverapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}


