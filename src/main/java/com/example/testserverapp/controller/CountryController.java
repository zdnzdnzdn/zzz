package com.example.testserverapp.controller;

import com.example.testserverapp.entity.Country;
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
    public List<Country> getAllCountries() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Integer id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryService.save(country);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Integer id, @RequestBody Country countryDetails) {
        return countryService.findById(id)
                .map(country -> {
                    country.setCode(countryDetails.getCode());
                    country.setName(countryDetails.getName());
                    country.setRegion(countryDetails.getRegion()); // Pastikan region yang diupdate valid
                    return ResponseEntity.ok(countryService.save(country));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}