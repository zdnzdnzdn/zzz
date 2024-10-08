package com.example.testserverapp.model.request;
import lombok.Data;

@Data
public class CountryRequest {
    private Integer id;   
    private String code; 
    private String name; 
    private RegionRequest region;
}