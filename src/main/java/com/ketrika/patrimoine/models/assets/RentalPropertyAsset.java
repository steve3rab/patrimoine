package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents Rental Property
 */
public final class RentalPropertyAsset implements IAsset {

  private final String name;
  private final String address;
  private final BigDecimal monthlyRent;
  private final BigDecimal areaSqM;
  private final int yearBuilt;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<RentalPropertyAsset> valuation;
  private final Instant createdAt;

  private RentalPropertyAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.address = Objects.requireNonNull(builder.address);
    this.monthlyRent = Objects.requireNonNull(builder.monthlyRent);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.areaSqM = builder.areaSqM;
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
    private BigDecimal monthlyRent;
    private BigDecimal areaSqM;
    private int yearBuilt;
    private Currency currency;
    private List<String> tags;
    private IValuation<RentalPropertyAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder monthlyRent(BigDecimal monthlyRent) {
      this.monthlyRent = monthlyRent;
      return this;
    }

    public Builder areaSqM(BigDecimal areaSqM) {
      this.areaSqM = areaSqM;
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

    public Builder valuation(IValuation<RentalPropertyAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public RentalPropertyAsset build() {
      return new RentalPropertyAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public BigDecimal getAreaSqM() {
    return areaSqM;
  }

  public int getYearBuilt() {
    return yearBuilt;
  }

  public String getAddress() {
    return address;
  }

  public BigDecimal getMonthlyRent() {
    return monthlyRent;
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
