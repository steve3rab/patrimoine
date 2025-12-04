package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents Franchise Right
 */
public final class FranchiseRightAsset implements IAsset {

  private final String name;
  private final int yearsRemaining;
  private final BigDecimal annualProfit;
  private final String franchisor;
  private final String contractId;
  private final Instant contractStart;
  private final Instant contractEnd;
  private final Boolean renewable;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<FranchiseRightAsset> valuation;
  private final Instant createdAt;

  /**
   * Constructs a new FranchiseRightAsset.
   * 
   * @param name
   * @param yearsRemaining
   * @param annualProfit
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public FranchiseRightAsset(String name, int yearsRemaining, BigDecimal annualProfit, IValuation<FranchiseRightAsset> valuation) {
    this(name, yearsRemaining, annualProfit, null, null, null, null, null, null, null, valuation);
  }

  /**
   * Constructor with optional metadata
   */
  public FranchiseRightAsset(
      String name,
      Integer yearsRemaining,
      BigDecimal annualProfit,
      String franchisor,
      String contractId,
      Instant contractStart,
      Instant contractEnd,
      Boolean renewable,
      Currency currency,
      List<String> tags,
      IValuation<FranchiseRightAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.yearsRemaining = yearsRemaining;
    this.annualProfit = Objects.requireNonNull(annualProfit);
    this.franchisor = franchisor;
    this.contractId = contractId;
    this.contractStart = contractStart;
    this.contractEnd = contractEnd;
    this.renewable = renewable;
    this.valuation = Objects.requireNonNull(valuation);
    this.currency = currency;
    this.tags = tags != null ? List.copyOf(tags) : null;
    this.createdAt = Instant.now();
  }

  public String getFranchisor() {
    return franchisor;
  }

  public String getContractId() {
    return contractId;
  }

  public Instant getContractStart() {
    return contractStart;
  }

  public Instant getContractEnd() {
    return contractEnd;
  }

  public Boolean getRenewable() {
    return renewable;
  }

  public int getYearsRemaining() {
    return yearsRemaining;
  }

  public BigDecimal getAnnualProfit() {
    return annualProfit;
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
    // Value = AnnualProfit × YearsRemaining
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
