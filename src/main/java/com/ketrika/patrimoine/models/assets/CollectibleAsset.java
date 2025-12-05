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

  private CollectibleAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.category = Objects.requireNonNull(builder.category);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.manufacturer = builder.manufacturer;
    this.model = builder.model;
    this.serialNumber = builder.serialNumber;
    this.limitedEdition = builder.limitedEdition;
    this.editionNumber = builder.editionNumber;
    this.acquiredAt = builder.acquiredAt;
    this.purchasePrice = builder.purchasePrice;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String category;
    private String manufacturer;
    private String model;
    private String serialNumber;
    private Boolean limitedEdition;
    private Integer editionNumber;
    private Instant acquiredAt;
    private BigDecimal purchasePrice;
    private Currency currency;
    private List<String> tags;
    private IValuation<CollectibleAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
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

    public Builder serialNumber(String serialNumber) {
      this.serialNumber = serialNumber;
      return this;
    }

    public Builder limitedEdition(Boolean limitedEdition) {
      this.limitedEdition = limitedEdition;
      return this;
    }

    public Builder editionNumber(Integer editionNumber) {
      this.editionNumber = editionNumber;
      return this;
    }

    public Builder acquiredAt(Instant acquiredAt) {
      this.acquiredAt = acquiredAt;
      return this;
    }

    public Builder purchasePrice(BigDecimal purchasePrice) {
      this.purchasePrice = purchasePrice;
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

    public Builder valuation(IValuation<CollectibleAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public CollectibleAsset build() {
      return new CollectibleAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

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
