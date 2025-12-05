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
  private final String royaltyType; // e.g., "Music", "Patent", "Publishing"
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<RoyaltyStreamAsset> valuation;
  private final Instant createdAt;

  /**
   * Constructs a new RoyaltyStreamAsset.
   * 
   * @param name
   * @param annualRoyaltyIncome
   * @param yearsRemaining
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public RoyaltyStreamAsset(String name, BigDecimal annualRoyaltyIncome, int yearsRemaining, IValuation<RoyaltyStreamAsset> valuation) {
    this(name, annualRoyaltyIncome, yearsRemaining, null, null, null, valuation);
  }

  /**
   * Full constructor including metadata.
   */
  public RoyaltyStreamAsset(
      String name,
      BigDecimal annualRoyaltyIncome,
      int yearsRemaining,
      String royaltyType,
      Currency currency,
      List<String> tags,
      IValuation<RoyaltyStreamAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.annualRoyaltyIncome = Objects.requireNonNull(annualRoyaltyIncome);
    this.yearsRemaining = yearsRemaining;
    this.valuation = Objects.requireNonNull(valuation);
    this.royaltyType = royaltyType;
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
  public BigDecimal value() {
    // Value = AnnualRoyaltyIncome × YearsRemaining
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
