package com.example.testserverapp.controller;

import com.example.testserverapp.model.request.RegionRequest;
import com.example.testserverapp.model.response.RegionResponse;
import com.example.testserverapp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<RegionResponse> getAllRegions() {
        return regionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponse> getRegionById(@PathVariable Integer id) {
        RegionResponse regionResponse = regionService.getById(id);
        if (regionResponse != null) {
            return ResponseEntity.ok(regionResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public RegionResponse createRegion(@RequestBody RegionRequest regionRequest) {
        return regionService.create(regionRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponse> updateRegion(@PathVariable Integer id, @RequestBody RegionRequest regionRequest) {
        RegionResponse regionResponse = regionService.update(id, regionRequest);
        if (regionResponse != null) {
            return ResponseEntity.ok(regionResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}