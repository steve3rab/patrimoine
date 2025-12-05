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

  private FranchiseRightAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.yearsRemaining = Objects.requireNonNull(builder.yearsRemaining);
    this.annualProfit = Objects.requireNonNull(builder.annualProfit);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.franchisor = builder.franchisor;
    this.contractId = builder.contractId;
    this.contractStart = builder.contractStart;
    this.contractEnd = builder.contractEnd;
    this.renewable = builder.renewable;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private Integer yearsRemaining; // use Integer to allow null check
    private BigDecimal annualProfit;
    private String franchisor;
    private String contractId;
    private Instant contractStart;
    private Instant contractEnd;
    private Boolean renewable;
    private Currency currency;
    private List<String> tags;
    private IValuation<FranchiseRightAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder yearsRemaining(int yearsRemaining) {
      this.yearsRemaining = yearsRemaining;
      return this;
    }

    public Builder annualProfit(BigDecimal annualProfit) {
      this.annualProfit = annualProfit;
      return this;
    }

    public Builder franchisor(String franchisor) {
      this.franchisor = franchisor;
      return this;
    }

    public Builder contractId(String contractId) {
      this.contractId = contractId;
      return this;
    }

    public Builder contractStart(Instant contractStart) {
      this.contractStart = contractStart;
      return this;
    }

    public Builder contractEnd(Instant contractEnd) {
      this.contractEnd = contractEnd;
      return this;
    }

    public Builder renewable(Boolean renewable) {
      this.renewable = renewable;
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

    public Builder valuation(IValuation<FranchiseRightAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public FranchiseRightAsset build() {
      return new FranchiseRightAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

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
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
