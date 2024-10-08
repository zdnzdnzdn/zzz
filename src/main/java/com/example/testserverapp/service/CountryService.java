package com.example.testserverapp.service;

import com.example.testserverapp.model.request.CountryRequest;
import com.example.testserverapp.model.response.CountryResponse;

import java.util.List;

public interface CountryService {
    List<CountryResponse> getAll();
    CountryResponse getById(Integer id);
    CountryResponse create(CountryRequest request);
    CountryResponse update(Integer id, CountryRequest request);
    CountryResponse delete(Integer id);
}