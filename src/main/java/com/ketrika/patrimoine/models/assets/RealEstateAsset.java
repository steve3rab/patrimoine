package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Real estate asset.
 */
public final class RealEstateAsset implements IAsset {

  private final String name;
  private final String address;
  private final String propertyType;
  private final BigDecimal landAreaSqM;
  private final BigDecimal buildingAreaSqM;
  private final int yearBuilt;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<RealEstateAsset> valuation;
  private final Instant createdAt;

  private RealEstateAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.address = builder.address;
    this.propertyType = builder.propertyType;
    this.landAreaSqM = builder.landAreaSqM;
    this.buildingAreaSqM = builder.buildingAreaSqM;
    this.yearBuilt = builder.yearBuilt;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String address;
    private String propertyType;
    private BigDecimal landAreaSqM;
    private BigDecimal buildingAreaSqM;
    private int yearBuilt;
    private Currency currency;
    private List<String> tags;
    private IValuation<RealEstateAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder propertyType(String propertyType) {
      this.propertyType = propertyType;
      return this;
    }

    public Builder landAreaSqM(BigDecimal landAreaSqM) {
      this.landAreaSqM = landAreaSqM;
      return this;
    }

    public Builder buildingAreaSqM(BigDecimal buildingAreaSqM) {
      this.buildingAreaSqM = buildingAreaSqM;
      return this;
    }

    public Builder yearBuilt(int yearBuilt) {
      this.yearBuilt = yearBuilt;
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

    public Builder valuation(IValuation<RealEstateAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public RealEstateAsset build() {
      return new RealEstateAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getAddress() {
    return address;
  }

  public String getPropertyType() {
    return propertyType;
  }

  public BigDecimal getLandAreaSqM() {
    return landAreaSqM;
  }

  public BigDecimal getBuildingAreaSqM() {
    return buildingAreaSqM;
  }

  public int getYearBuilt() {
    return yearBuilt;
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
