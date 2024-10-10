package com.example.testserverapp.repository;

import com.example.testserverapp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
  List<Country> findByNameOrRegionName(String name, String regionName);
}

