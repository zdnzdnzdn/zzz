package com.example.testserverapp.service.impl;

import com.example.testserverapp.entity.Region;
import com.example.testserverapp.exception.CustomException;
import com.example.testserverapp.model.request.RegionRequest;
import com.example.testserverapp.model.response.RegionResponse;
import com.example.testserverapp.repository.RegionRepository;
import com.example.testserverapp.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public List<RegionResponse> getAll() {
        return regionRepository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public RegionResponse getById(Integer id) {
        return regionRepository.findById(id)
            .map(this::convertToResponse)
            .orElseThrow(() -> new CustomException(
                String.format("Region with ID %d not found", id),
                "HttpStatus.REGION_NOT_FOUND",
                HttpStatus.NOT_FOUND.value()
            ));
    }

    @Override
    public RegionResponse create(RegionRequest request) {
        Region region = new Region();
        region.setName(request.getName());

        Region savedRegion = regionRepository.save(region);
        return convertToResponse(savedRegion);
    }

    @Override
    public RegionResponse update(Integer id, RegionRequest request) {
        Region region = regionRepository.findById(id)
            .orElseThrow(() -> new CustomException(
                String.format("Region with ID %d not found", id),
                "HttpStatus.REGION_NOT_FOUND",
                HttpStatus.NOT_FOUND.value()
            ));

        region.setName(request.getName());

        Region savedRegion = regionRepository.save(region);
        return convertToResponse(savedRegion);
    }

    @Override
    public RegionResponse delete(Integer id) {
        RegionResponse regionToDelete = getById(id);
        regionRepository.deleteById(id);
        return regionToDelete;
    }

    private RegionResponse convertToResponse(Region region) {
        RegionResponse response = new RegionResponse();
        response.setId(region.getId());
        response.setName(region.getName());
        return response;
    }
}