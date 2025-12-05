package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents a savings account, often interest-bearing.
 */
public final class SavingsAccountAsset implements IAsset {

  private final String name;
  private final String accountNumber;
  private final String bankName;
  private final BigDecimal interestRateAnnual;
  private final BigDecimal balance;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<SavingsAccountAsset> valuation;
  private final Instant createdAt;

  private SavingsAccountAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.accountNumber = builder.accountNumber;
    this.bankName = builder.bankName;
    this.interestRateAnnual = builder.interestRateAnnual;
    this.balance = builder.balance;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String accountNumber;
    private String bankName;
    private BigDecimal interestRateAnnual;
    private BigDecimal balance;
    private Currency currency;
    private List<String> tags;
    private IValuation<SavingsAccountAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder accountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
      return this;
    }

    public Builder bankName(String bankName) {
      this.bankName = bankName;
      return this;
    }

    public Builder interestRateAnnual(BigDecimal interestRateAnnual) {
      this.interestRateAnnual = interestRateAnnual;
      return this;
    }

    public Builder balance(BigDecimal balance) {
      this.balance = balance;
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

    public Builder valuation(IValuation<SavingsAccountAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public SavingsAccountAsset build() {
      return new SavingsAccountAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public BigDecimal getInterestRateAnnual() {
    return interestRateAnnual;
  }

  public BigDecimal getBalance() {
    return balance;
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
