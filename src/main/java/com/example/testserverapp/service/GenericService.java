package com.example.testserverapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    JpaRepository<T, ID> getRepository();
    List<T> getAll();
    Optional<T> getById(ID id);
    T create(T entity);
    T update(T entity);
    void delete(ID id);
}