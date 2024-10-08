package com.example.testserverapp.service;

import com.example.testserverapp.model.request.RegionRequest;
import com.example.testserverapp.model.response.RegionResponse;

import java.util.List;

public interface RegionService {
    List<RegionResponse> getAll();
    RegionResponse getById(Integer id);
    RegionResponse create(RegionRequest request);
    RegionResponse update(Integer id, RegionRequest request);
    RegionResponse delete(Integer id);
}