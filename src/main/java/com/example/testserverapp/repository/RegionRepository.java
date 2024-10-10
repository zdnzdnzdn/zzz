package com.example.testserverapp.repository;

import com.example.testserverapp.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    // Native query untuk memeriksa apakah nama region sudah ada
    @Query(value = "SELECT COUNT(*) FROM regions WHERE name = :name", nativeQuery = true)
    int countByName(@Param("name") String name);

    // Native query untuk mencari region berdasarkan nama
    @Query(value = "SELECT * FROM regions r WHERE r.name LIKE ?1", nativeQuery = true)
    List<Region> searchAllNameNative(String name);

    // Method untuk mencari region berdasarkan nama tanpa menggunakan query native
    Optional<Region> findByName(String name);
}
