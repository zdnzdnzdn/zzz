package com.example.testserverapp.service.impl;

import com.example.testserverapp.entity.Country;
import com.example.testserverapp.exception.CustomException;
import com.example.testserverapp.model.request.CountryRequest;
import com.example.testserverapp.model.response.CountryResponse;
import com.example.testserverapp.repository.CountryRepository;
import com.example.testserverapp.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryResponse> getAll() {
        return countryRepository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public CountryResponse getById(Integer id) {
        return countryRepository.findById(id)
            .map(this::convertToResponse)
            .orElseThrow(() -> new CustomException(
                String.format("Country with ID %d not found", id),
                "HttpStatus.COUNTRY_NOT_FOUND",
                HttpStatus.NOT_FOUND.value()
            ));
    }

    @Override
    public CountryResponse create(CountryRequest request) {
        Country country = new Country();
        country.setName(request.getName());

        Country savedCountry = countryRepository.save(country);
        return convertToResponse(savedCountry);
    }

    @Override
    public CountryResponse update(Integer id, CountryRequest request) {
        Country country = countryRepository.findById(id)
            .orElseThrow(() -> new CustomException(
                String.format("Country with ID %d not found", id),
                "HttpStatus.COUNTRY_NOT_FOUND",
                HttpStatus.NOT_FOUND.value()
            ));

        country.setName(request.getName());

        Country savedCountry = countryRepository.save(country);
        return convertToResponse(savedCountry);
    }

    @Override
    public CountryResponse delete(Integer id) {
        CountryResponse countryToDelete = getById(id);
        countryRepository.deleteById(id);
        return countryToDelete;
    }

    private CountryResponse convertToResponse(Country country) {
        CountryResponse response = new CountryResponse();
        response.setId(country.getId());
        response.setName(country.getName());
        return response;
    }
}