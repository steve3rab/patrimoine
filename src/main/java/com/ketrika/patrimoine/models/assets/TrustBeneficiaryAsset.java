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

  /**
   * Constructs a new TrustBeneficiaryAsset.
   * 
   * @param name
   * @param expectedPayout
   * @param yearsUntilDistribution
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public TrustBeneficiaryAsset(String name, BigDecimal expectedPayout, int yearsUntilDistribution, IValuation<TrustBeneficiaryAsset> valuation) {
    this(name, expectedPayout, yearsUntilDistribution, null, null, valuation);
  }

  /**
   * Full constructor including optional metadata.
   */
  public TrustBeneficiaryAsset(String name, BigDecimal expectedPayout, int yearsUntilDistribution,
      Currency currency,
      List<String> tags, IValuation<TrustBeneficiaryAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.expectedPayout = Objects.requireNonNull(expectedPayout);
    this.yearsUntilDistribution = yearsUntilDistribution;
    this.valuation = Objects.requireNonNull(valuation);
    this.currency = currency;
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
  public BigDecimal value() {
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
