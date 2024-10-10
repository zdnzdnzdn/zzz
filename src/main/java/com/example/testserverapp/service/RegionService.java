package com.example.testserverapp.service;

import com.example.testserverapp.entity.Region;
import com.example.testserverapp.repository.RegionRepository;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@AllArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    // Retrieve all regions
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    // Get Region by ID using Optional (Method 1)
    public Optional<Region> getByIdOptional(Integer id) {
        return regionRepository.findById(id);
    }

    // Get Region by ID with Exception Handling (Method 2)
    public Region getById(Integer id) {
        return regionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Region not found!!!")
                );
    }

    // Create a new Region
    public Region create(@Valid Region region) {
        if (regionRepository.findByName(region.getName()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Region name already exists!!!"
            );
        }
        return regionRepository.save(region);
    }

    // Update an existing Region
    public Region update(Integer id, @Valid Region region) {
        getById(id); // Ensure the Region exists
        region.setId(id); // Set the ID before updating
        return regionRepository.save(region);
    }

    // Delete a Region by ID
    public Region delete(Integer id) {
        Region region = getById(id); // Fetch and validate Region
        regionRepository.delete(region);
        return region;
    }

    // Native query to search for regions by name
    public List<Region> searchAllNameNative(String name) {
        String nameFormat = "%" + name + "%";
        log.info("Logging: {}", nameFormat);
        return regionRepository.searchAllNameNative(nameFormat);
    }
}
