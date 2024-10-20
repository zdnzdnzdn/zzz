package com.example.testserverapp.controller;

import com.example.testserverapp.entity.Region;
import com.example.testserverapp.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regions")
@PreAuthorize("hasRole('ADMIN')")
public class RegionController {

  private final RegionService regionService;

  @Autowired
  public RegionController(RegionService regionService) {
    this.regionService = regionService;
  }

  // Retrieve all regions
  @GetMapping
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public ResponseEntity<List<Region>> getAll() {
    List<Region> regions = regionService.getAll();
    return new ResponseEntity<>(regions, HttpStatus.OK);
  }

  // Get Region by ID
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public ResponseEntity<Region> getById(@PathVariable Integer id) {
    Region region = regionService.getById(id);
    return new ResponseEntity<>(region, HttpStatus.OK);
  }

  // Create a new Region
  @PostMapping
  @PreAuthorize("hasAuthority('CREATE_ADMIN')")
  public ResponseEntity<Region> create(@RequestBody Region region) {
    Region newRegion = regionService.create(region);
    return new ResponseEntity<>(newRegion, HttpStatus.CREATED);
  }

  // Update an existing Region
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
  public ResponseEntity<Region> update(@PathVariable Integer id, @RequestBody Region region) {
    Region updatedRegion = regionService.update(id, region);
    return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
  }

  // Delete a Region by ID
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('DELETE_ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    regionService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Search for regions by name
  @GetMapping("/native")
  @PreAuthorize("hasAuthority('READ_ADMIN')")
  public ResponseEntity<List<Region>> searchByName(@RequestParam String name) {
    List<Region> regions = regionService.searchAllNameNative(name);
    return new ResponseEntity<>(regions, HttpStatus.OK);
  }
}