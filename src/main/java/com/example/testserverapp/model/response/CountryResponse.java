package com.example.testserverapp.model.response;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {

  private Integer countryId;
  private String countryCode;
  private String countryName;
  private Integer regionId;
  private String regionName;
}