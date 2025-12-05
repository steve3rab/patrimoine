package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents the cash-out value (or surrender value) of a life insurance policy.
 */
public final class LifeInsuranceAsset implements IAsset {

  private final String name;
  private final String contractNumber;
  private final String ownerName;
  private final String insuredPersonName;
  private final String beneficiaryName;
  private final String issuer;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<LifeInsuranceAsset> valuation;
  private final Instant createdAt;

  private LifeInsuranceAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.contractNumber = Objects.requireNonNull(builder.contractNumber);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.ownerName = builder.ownerName;
    this.insuredPersonName = builder.insuredPersonName;
    this.beneficiaryName = builder.beneficiaryName;
    this.issuer = builder.issuer;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String contractNumber;
    private String ownerName;
    private String insuredPersonName;
    private String beneficiaryName;
    private String issuer;
    private Currency currency;
    private List<String> tags;
    private IValuation<LifeInsuranceAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder contractNumber(String contractNumber) {
      this.contractNumber = contractNumber;
      return this;
    }

    public Builder ownerName(String ownerName) {
      this.ownerName = ownerName;
      return this;
    }

    public Builder insuredPersonName(String insuredPersonName) {
      this.insuredPersonName = insuredPersonName;
      return this;
    }

    public Builder beneficiaryName(String beneficiaryName) {
      this.beneficiaryName = beneficiaryName;
      return this;
    }

    public Builder issuer(String issuer) {
      this.issuer = issuer;
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

    public Builder valuation(IValuation<LifeInsuranceAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public LifeInsuranceAsset build() {
      return new LifeInsuranceAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getOwnerName() {
    return ownerName;
  }

  public String getInsuredPersonName() {
    return insuredPersonName;
  }

  public String getBeneficiaryName() {
    return beneficiaryName;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getContractNumber() {
    return contractNumber;
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
