package com.example.testserverapp.service;

import com.example.testserverapp.entity.Country;
import com.example.testserverapp.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends GenericService<Country, Integer> {
    public CountryService(CountryRepository countryRepository) {
        super(countryRepository);
    }
}