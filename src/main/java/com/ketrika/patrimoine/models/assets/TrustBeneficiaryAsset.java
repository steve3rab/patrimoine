package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents Trust Beneficiary
 */
public final class TrustBeneficiaryAsset implements IAsset {

  private final String name;
  private final BigDecimal expectedPayout;
  private final int yearsUntilDistribution;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<TrustBeneficiaryAsset> valuation;
  private final Instant createdAt;

  private TrustBeneficiaryAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.expectedPayout = Objects.requireNonNull(builder.expectedPayout);
    this.yearsUntilDistribution = Objects.requireNonNull(builder.yearsUntilDistribution);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private BigDecimal expectedPayout;
    private Integer yearsUntilDistribution; // for null check
    private Currency currency;
    private List<String> tags;
    private IValuation<TrustBeneficiaryAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder expectedPayout(BigDecimal expectedPayout) {
      this.expectedPayout = expectedPayout;
      return this;
    }

    public Builder yearsUntilDistribution(int yearsUntilDistribution) {
      this.yearsUntilDistribution = yearsUntilDistribution;
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

    public Builder valuation(IValuation<TrustBeneficiaryAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public TrustBeneficiaryAsset build() {
      return new TrustBeneficiaryAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public BigDecimal getExpectedPayout() {
    return expectedPayout;
  }

  public int getYearsUntilDistribution() {
    return yearsUntilDistribution;
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
