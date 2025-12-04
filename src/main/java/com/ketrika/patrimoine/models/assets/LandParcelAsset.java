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

  /**
   * Constructs a new LandParcelAsset.
   * 
   * @param name
   * @param acreage
   * @param zoningType
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public LandParcelAsset(String name, double acreage, String zoningType, IValuation<LandParcelAsset> valuation) {
    this(name, acreage, zoningType, null, null, null, null, null, null, null, valuation);
  }

  /**
   * Full constructor including optional metadata.
   */
  public LandParcelAsset(
      String name,
      Double acreage,
      String zoningType,
      String location,
      String parcelId,
      Boolean roadAccess,
      BigDecimal purchasePrice,
      Instant acquiredAt,
      Currency currency,
      List<String> tags,
      IValuation<LandParcelAsset> valuation

  ) {
    this.name = Objects.requireNonNull(name);
    this.acreage = acreage;
    this.zoningType = Objects.requireNonNull(zoningType);
    this.location = location;
    this.parcelId = parcelId;
    this.roadAccess = roadAccess;
    this.purchasePrice = purchasePrice;
    this.acquiredAt = acquiredAt;
    this.currency = currency;
    this.valuation = Objects.requireNonNull(valuation);
    this.tags = tags != null ? List.copyOf(tags) : null;
    this.createdAt = Instant.now();
  }

  @Override
  public Currency currency() {
    return currency != null ? currency : IAsset.super.currency();
  }

  @Override
  public List<String> tags() {
    return tags != null ? tags : IAsset.super.tags();
  }

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
  public BigDecimal value() {
    // Value = PricePerAcre × Acreage
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
