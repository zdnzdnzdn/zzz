package com.example.testserverapp.service;

import com.example.testserverapp.entity.Region;
import com.example.testserverapp.repository.RegionRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends GenericService<Region, Integer> {
    public RegionService(RegionRepository regionRepository) {
        super(regionRepository);
    }
}