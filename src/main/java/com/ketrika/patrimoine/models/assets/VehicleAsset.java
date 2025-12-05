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

  private VehicleAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.registration = Objects.requireNonNull(builder.registration);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.ownerCompany = builder.ownerCompany;
    this.manufacturer = builder.manufacturer;
    this.model = builder.model;
    this.year = builder.year;
    this.fuelType = builder.fuelType;
    this.transmission = builder.transmission;
    this.seatingCapacity = builder.seatingCapacity;
    this.horsepower = builder.horsepower;
    this.purchasePrice = builder.purchasePrice;
    this.purchaseDate = builder.purchaseDate;
    this.odometerKm = builder.odometerKm;
    this.usageType = builder.usageType;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String registration;
    private IValuation<VehicleAsset> valuation;
    private String ownerCompany;
    private String manufacturer;
    private String model;
    private int year;
    private String fuelType;
    private String transmission;
    private int seatingCapacity;
    private int horsepower;
    private BigDecimal purchasePrice;
    private Instant purchaseDate;
    private long odometerKm;
    private String usageType;
    private Currency currency;
    private List<String> tags;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder registration(String registration) {
      this.registration = registration;
      return this;
    }

    public Builder valuation(IValuation<VehicleAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public Builder ownerCompany(String ownerCompany) {
      this.ownerCompany = ownerCompany;
      return this;
    }

    public Builder manufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
      return this;
    }

    public Builder model(String model) {
      this.model = model;
      return this;
    }

    public Builder year(int year) {
      this.year = year;
      return this;
    }

    public Builder fuelType(String fuelType) {
      this.fuelType = fuelType;
      return this;
    }

    public Builder transmission(String transmission) {
      this.transmission = transmission;
      return this;
    }

    public Builder seatingCapacity(int seatingCapacity) {
      this.seatingCapacity = seatingCapacity;
      return this;
    }

    public Builder horsepower(int horsepower) {
      this.horsepower = horsepower;
      return this;
    }

    public Builder purchasePrice(BigDecimal purchasePrice) {
      this.purchasePrice = purchasePrice;
      return this;
    }

    public Builder purchaseDate(Instant purchaseDate) {
      this.purchaseDate = purchaseDate;
      return this;
    }

    public Builder odometerKm(long odometerKm) {
      this.odometerKm = odometerKm;
      return this;
    }

    public Builder usageType(String usageType) {
      this.usageType = usageType;
      return this;
    }

    public Builder currency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Builder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public VehicleAsset build() {
      return new VehicleAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

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

  public String getRegistration() {
    return registration;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  @Override
  public Currency currency() {
    return currency != null ? currency : IAsset.super.currency();
  }

  @Override
  public List<String> tags() {
    return tags != null ? tags : IAsset.super.tags();
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
