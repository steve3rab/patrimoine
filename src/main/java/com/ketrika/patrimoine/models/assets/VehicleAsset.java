package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents a vehicle such as a car, motorcycle, or boat.
 */
public final class VehicleAsset implements IAsset {

  private final String name;
  private final String registration;
  private final IValuation<VehicleAsset> valuation;
  private final String ownerCompany;
  private final String manufacturer;
  private final String model;
  private final int year;
  private final String fuelType;
  private final String transmission;
  private final int seatingCapacity;
  private final int horsepower;
  private final BigDecimal purchasePrice;
  private final Instant purchaseDate;
  private final Instant createdAt;
  private final long odometerKm;
  private final String usageType;
  private final Currency currency;
  private final List<String> tags;

  /**
   * Constructs a VehicleAsset
   * 
   * @param name descriptive name
   * @param registration license plate or identifier
   * @param valuation the valuation(e.g depreciation)
   * @throws NullPointerException if any argument is null
   */
  public VehicleAsset(String name, String registration, IValuation<VehicleAsset> valuation) {
    this(name, registration, null, null, null, null, 0, null, null, 0, 0, null, null, 0L, registration, null, null, valuation);
  }

  /**
   * Full constructor including optional metadata.
   */
  public VehicleAsset(
      String name,
      String registration,
      Instant createdAt,
      String ownerCompany,
      String manufacturer,
      String model,
      int year,
      String fuelType,
      String transmission,
      int horsepower,
      int seatingCapacity,
      BigDecimal purchasePrice,
      Instant purchaseDate,
      long odometerKm,
      String usageType,
      Currency currency,
      List<String> tags, IValuation<VehicleAsset> valuation) {
    this.name = name;
    this.registration = registration;
    this.ownerCompany = ownerCompany;
    this.manufacturer = manufacturer;
    this.model = model;
    this.year = year;
    this.fuelType = fuelType;
    this.transmission = transmission;
    this.horsepower = horsepower;
    this.seatingCapacity = seatingCapacity;
    this.purchasePrice = purchasePrice;
    this.purchaseDate = purchaseDate;
    this.odometerKm = odometerKm;
    this.usageType = usageType;
    this.currency = currency;
    this.tags = tags != null ? List.copyOf(tags) : null;
    this.createdAt = Instant.now();
    this.valuation = Objects.requireNonNull(valuation);
  }

  public String getOwnerCompany() {
    return ownerCompany;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getModel() {
    return model;
  }

  public int getYear() {
    return year;
  }

  public String getFuelType() {
    return fuelType;
  }

  public String getTransmission() {
    return transmission;
  }

  public int getSeatingCapacity() {
    return seatingCapacity;
  }

  public int getHorsepower() {
    return horsepower;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public Instant getPurchaseDate() {
    return purchaseDate;
  }

  public long getOdometerKm() {
    return odometerKm;
  }

  public String getUsageType() {
    return usageType;
  }

  @Override
  public Currency currency() {
    return currency != null ? currency : IAsset.super.currency();
  }

  @Override
  public List<String> tags() {
    return tags != null ? tags : IAsset.super.tags();
  }

  public String getRegistration() {
    return registration;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  @Override
  public BigDecimal value() {
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
