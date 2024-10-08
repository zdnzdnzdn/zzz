package com.example.testserverapp.model.response;
import lombok.Data;

@Data
public class CountryResponse {
    private Integer id;   
    private String code; 
    private String name;  
    private RegionResponse region;
}