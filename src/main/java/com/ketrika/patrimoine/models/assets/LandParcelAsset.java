package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents Land Parcel
 */
public final class LandParcelAsset implements IAsset {

  private final String name;
  private final double acreage;
  private final String zoningType;
  private final String location;
  private final String parcelId;
  private final Boolean roadAccess;
  private final BigDecimal purchasePrice;
  private final Instant acquiredAt;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<LandParcelAsset> valuation;
  private final Instant createdAt;

  private LandParcelAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.acreage = Objects.requireNonNull(builder.acreage);
    this.zoningType = Objects.requireNonNull(builder.zoningType);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.location = builder.location;
    this.parcelId = builder.parcelId;
    this.roadAccess = builder.roadAccess;
    this.purchasePrice = builder.purchasePrice;
    this.acquiredAt = builder.acquiredAt;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private Double acreage; // use Double for null check
    private String zoningType;
    private String location;
    private String parcelId;
    private Boolean roadAccess;
    private BigDecimal purchasePrice;
    private Instant acquiredAt;
    private Currency currency;
    private List<String> tags;
    private IValuation<LandParcelAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder acreage(double acreage) {
      this.acreage = acreage;
      return this;
    }

    public Builder zoningType(String zoningType) {
      this.zoningType = zoningType;
      return this;
    }

    public Builder location(String location) {
      this.location = location;
      return this;
    }

    public Builder parcelId(String parcelId) {
      this.parcelId = parcelId;
      return this;
    }

    public Builder roadAccess(Boolean roadAccess) {
      this.roadAccess = roadAccess;
      return this;
    }

    public Builder purchasePrice(BigDecimal purchasePrice) {
      this.purchasePrice = purchasePrice;
      return this;
    }

    public Builder acquiredAt(Instant acquiredAt) {
      this.acquiredAt = acquiredAt;
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

    public Builder valuation(IValuation<LandParcelAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public LandParcelAsset build() {
      return new LandParcelAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getLocation() {
    return location;
  }

  public String getParcelId() {
    return parcelId;
  }

  public Boolean getRoadAccess() {
    return roadAccess;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public Instant getAcquiredAt() {
    return acquiredAt;
  }

  public double getAcreage() {
    return acreage;
  }

  public String getZoningType() {
    return zoningType;
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
