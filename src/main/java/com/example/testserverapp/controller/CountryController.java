package com.example.testserverapp.controller;

import com.example.testserverapp.entity.Country;
import com.example.testserverapp.model.request.CountryRequest;
import com.example.testserverapp.model.response.CountryResponse;
import com.example.testserverapp.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // Mendapatkan semua negara
    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<List<Country>> getAll() {
        return new ResponseEntity<>(countryService.getAll(), HttpStatus.OK);
    }

    // Mendapatkan negara berdasarkan ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<Country> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(countryService.getById(id), HttpStatus.OK);
    }

    // Mendapatkan negara dengan respon khusus
    @GetMapping("/{id}/custom-response")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<CountryResponse> getByIdCustomResponse(@PathVariable Integer id) {
        return new ResponseEntity<>(countryService.getByIdCustomResponse(id), HttpStatus.OK);
    }

    // Mendapatkan negara dengan respon dalam bentuk Map
    @GetMapping("/{id}/custom-map")
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    public ResponseEntity<Map<String, Object>> getByIdCustomMap(@PathVariable Integer id) {
        return new ResponseEntity<>(countryService.getByIdCustomMap(id), HttpStatus.OK);
    }

    // Membuat negara baru tanpa DTO
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Country> create(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.create(country), HttpStatus.CREATED);
    }

    // Membuat negara baru dengan DTO secara manual
    @PostMapping("/manual")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Country> createWithDTOManual(@RequestBody CountryRequest countryRequest) {
        return new ResponseEntity<>(countryService.createWithDTOManual(countryRequest), HttpStatus.CREATED);
    }

    // Membuat negara baru dengan DTO secara otomatis
    @PostMapping("/auto")
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Country> createWithDTOAuto(@RequestBody CountryRequest countryRequest) {
        return new ResponseEntity<>(countryService.createWithDTOAuto(countryRequest), HttpStatus.CREATED);
    }

    // Memperbarui negara
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Integer id, @RequestBody Country country) {
        return new ResponseEntity<>(countryService.update(id, country), HttpStatus.OK);
    }

    // Menghapus negara
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Country> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(countryService.delete(id), HttpStatus.OK);
    }
}