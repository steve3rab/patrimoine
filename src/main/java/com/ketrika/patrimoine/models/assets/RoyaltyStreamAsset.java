package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents Royalty Stream
 */
public final class RoyaltyStreamAsset implements IAsset {

  private final String name;
  private final BigDecimal annualRoyaltyIncome;
  private final int yearsRemaining;
  private final String royaltyType;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<RoyaltyStreamAsset> valuation;
  private final Instant createdAt;

  private RoyaltyStreamAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.annualRoyaltyIncome = Objects.requireNonNull(builder.annualRoyaltyIncome);
    this.yearsRemaining = Objects.requireNonNull(builder.yearsRemaining);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.royaltyType = builder.royaltyType;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private BigDecimal annualRoyaltyIncome;
    private Integer yearsRemaining; // for null check
    private String royaltyType;
    private Currency currency;
    private List<String> tags;
    private IValuation<RoyaltyStreamAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder annualRoyaltyIncome(BigDecimal annualRoyaltyIncome) {
      this.annualRoyaltyIncome = annualRoyaltyIncome;
      return this;
    }

    public Builder yearsRemaining(int yearsRemaining) {
      this.yearsRemaining = yearsRemaining;
      return this;
    }

    public Builder royaltyType(String royaltyType) {
      this.royaltyType = royaltyType;
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

    public Builder valuation(IValuation<RoyaltyStreamAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public RoyaltyStreamAsset build() {
      return new RoyaltyStreamAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getRoyaltyType() {
    return royaltyType;
  }

  public BigDecimal getAnnualRoyaltyIncome() {
    return annualRoyaltyIncome;
  }

  public int getYearsRemaining() {
    return yearsRemaining;
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
