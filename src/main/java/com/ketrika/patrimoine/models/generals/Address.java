package com.ketrika.patrimoine.models.generals;

/**
 * Represents a physical postal address.
 */
public record Address(String street, String city, String state, String zipCode, String country) {

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getCountry() {
    return country;
  }
}
