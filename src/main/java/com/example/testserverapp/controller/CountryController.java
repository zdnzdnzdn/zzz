package com.example.testserverapp.controller;

import com.example.testserverapp.model.request.CountryRequest;
import com.example.testserverapp.model.response.CountryResponse;
import com.example.testserverapp.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryResponse> getAllCountries() {
        return countryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> getCountryById(@PathVariable Integer id) {
        CountryResponse countryResponse = countryService.getById(id);
        if (countryResponse != null) {
            return ResponseEntity.ok(countryResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CountryResponse createCountry(@RequestBody CountryRequest countryRequest) {
        return countryService.create(countryRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> updateCountry(@PathVariable Integer id, @RequestBody CountryRequest countryRequest) {
        CountryResponse countryResponse = countryService.update(id, countryRequest);
        if (countryResponse != null) {
            return ResponseEntity.ok(countryResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}