package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents collectible items such as art, wine, jewelry or rare objects.
 */
public final class CollectibleAsset implements IAsset {

  private final String name;
  private final String category;
  private final String manufacturer;
  private final String model;
  private final String serialNumber;
  private final Boolean limitedEdition;
  private final Integer editionNumber;
  private final Instant acquiredAt;
  private final BigDecimal purchasePrice;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<CollectibleAsset> valuation;
  private final Instant createdAt;

  /**
   * Constructs a new CollectibleAsset.
   * 
   * @param name the name
   * @param category the category
   * @param valuation that returns current balance
   * @throws NullPointerException if any argument is null
   */
  public CollectibleAsset(String name, String category, IValuation<CollectibleAsset> valuation) {
    this(name, category, null, null, null, null, null, null, null, null, null, valuation);
  }

  /**
   * Constructs a full CollectibleAsset with optional metadata.
   */
  public CollectibleAsset(
      String name,
      String category,
      String manufacturer,
      String model,
      String serialNumber,
      Boolean limitedEdition,
      Integer editionNumber,
      Instant acquiredAt,
      BigDecimal purchasePrice,
      Currency currency,
      List<String> tags,
      IValuation<CollectibleAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.category = Objects.requireNonNull(category);
    this.manufacturer = manufacturer;
    this.model = model;
    this.serialNumber = serialNumber;
    this.limitedEdition = limitedEdition;
    this.editionNumber = editionNumber;
    this.acquiredAt = acquiredAt;
    this.purchasePrice = purchasePrice;
    this.currency = currency;
    this.tags = tags != null ? List.copyOf(tags) : null;
    this.valuation = Objects.requireNonNull(valuation);
    this.createdAt = Instant.now();
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getModel() {
    return model;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public Boolean getLimitedEdition() {
    return limitedEdition;
  }

  public Integer getEditionNumber() {
    return editionNumber;
  }

  public Instant getAcquiredAt() {
    return acquiredAt;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public IValuation<CollectibleAsset> getValuation() {
    return valuation;
  }

  public String getCategory() {
    return category;
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
