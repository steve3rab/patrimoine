package com.ketrika.patrimoine.models.generals;

import java.util.Objects;

/**
 * Represents a physical postal address.
 */
public final class Address {

  private final String street;
  private final String city;
  private final String state;
  private final String zipCode;
  private final String country;

  /**
   * Creates Address.
   *
   * @param street street and number
   * @param city city
   * @param zipCode postal code
   * @param country country
   * @throws NullPointerException if a required field is null
   */
  public Address(String street, String city, String state, String zipCode, String country) {
    this.street = Objects.requireNonNull(street);
    this.city = Objects.requireNonNull(city);
    this.state = Objects.requireNonNull(state);
    this.zipCode = Objects.requireNonNull(zipCode);
    this.country = Objects.requireNonNull(country);
  }

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
