package com.example.testserverapp.service;

import com.example.testserverapp.entity.Country;
import com.example.testserverapp.entity.Region;
import com.example.testserverapp.model.request.CountryRequest;
import com.example.testserverapp.model.response.CountryResponse;
import com.example.testserverapp.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final RegionService regionService;

    // Mendapatkan semua negara
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    // Mendapatkan negara berdasarkan ID
    public Country getById(Integer id) {
        return countryRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found!!!"));
    }

    // Mendapatkan negara dengan respon khusus
    public CountryResponse getByIdCustomResponse(Integer id) {
        Country country = getById(id);
        CountryResponse countryResponse = new CountryResponse();

        countryResponse.setCountryId(country.getId());
        countryResponse.setCountryCode(country.getCode());
        countryResponse.setCountryName(country.getName());
        countryResponse.setRegionId(country.getRegion().getId());
        countryResponse.setRegionName(country.getRegion().getName());
        return countryResponse;
    }

    // Mendapatkan negara dengan respon dalam bentuk Map
    public Map<String, Object> getByIdCustomMap(Integer id) {
        Country country = getById(id);
        Map<String, Object> result = new HashMap<>();

        result.put("cId", country.getId());
        result.put("cCode", country.getCode());
        result.put("cName", country.getName());
        result.put("rId", country.getRegion().getId());
        result.put("rName", country.getRegion().getName());

        return result;
    }

    // Membuat negara baru tanpa DTO
    public Country create(Country country) {
    if (!countryRepository.findByNameOrRegionName(country.getName(), country.getRegion().getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name is already exists!!!");
        }
        return countryRepository.save(country);
    }

    /*
    Kenapa bisa dan tidak bisa memasukkan nama yang sama dengan region?
    1. Bug: Kesalahan dalam kode ini adalah bahwa itu memungkinkan sebuah negara dibuat dengan nama yang sama dengan nama wilayah negara lain, dan sebaliknya. Ini karena metode findByNameOrRegionName hanya memeriksa apakah ada negara dengan nama yang sama atau nama wilayah, tetapi tidak memeriksa apakah nama wilayah negara baru sama dengan nama negara lain, atau apakah nama negara baru sama dengan nama wilayah negara lain.
    2. Penyebab: Penyebab kesalahan ini adalah logika dalam metode create. Metode hanya memeriksa apakah ada negara dengan nama yang sama atau nama wilayah, tetapi tidak mempertimbangkan skenario sebaliknya.
    3. Cara solve: Untuk memperbaiki kesalahan ini, kita perlu memodifikasi metode create untuk memeriksa kedua skenario: apakah nama negara baru sama dengan nama wilayah negara lain, dan apakah nama wilayah negara baru sama dengan nama negara lain. Kita dapat melakukan ini dengan menambahkan pemeriksaan tambahan:

    public Country create(Country country) {
        if (!countryRepository.findByName(country.getName()).isEmpty() || 
            !countryRepository.findByRegionName(country.getRegion().getName()).isEmpty() || 
            !countryRepository.findByNameOrRegionName(country.getName(), country.getRegion().getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name or region name is already exists!!!");
        }
        return countryRepository.save(country);
    }

    Dan perlu menambahkan dua metode tambahan ke interface CountryRepository:
    
    @Repository
    public interface CountryRepository extends JpaRepository<Country, Integer> {
        List<Country> findByName(String name);
        List<Country> findByRegionName(String regionName);
        List<Country> findByNameOrRegionName(String name, String regionName);
    } 
    */
     

    // Membuat negara baru dengan DTO secara manual
    public Country createWithDTOManual(CountryRequest countryRequest) {
        Country country = new Country();
        country.setCode(countryRequest.getCode());
        country.setName(countryRequest.getName());

        Region region = regionService.getById(countryRequest.getRegionId());
        country.setRegion(region);

        return countryRepository.save(country);
    }

    // Membuat negara baru dengan DTO secara otomatis
    public Country createWithDTOAuto(CountryRequest countryRequest) {
        if (!countryRepository.findByNameOrRegionName(countryRequest.getName(), String.valueOf(countryRequest.getRegionId())).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name is already exists!!!");
        }

        Country country = new Country();
        BeanUtils.copyProperties(countryRequest, country);

        Region region = regionService.getById(countryRequest.getRegionId());
        country.setRegion(region);

        return countryRepository.save(country);
    }

    // Memperbarui negara
    public Country update(Integer id, Country country) {
        getById(id); // Verifikasi apakah negara ada
        country.setId(id); // Set ID untuk pembaruan
        return countryRepository.save(country);
    }

    // Menghapus negara
    public Country delete(Integer id) {
        Country country = getById(id);
        countryRepository.delete(country);
        return country;
    }
}