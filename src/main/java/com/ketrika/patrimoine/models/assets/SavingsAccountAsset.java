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

  /**
   * Constructs a new SavingsAccountAsset.
   * 
   * @param name
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public SavingsAccountAsset(String name, IValuation<SavingsAccountAsset> valuation) {
    this(name, null, null, null, null, null, null, valuation);
  }

  /**
   * Full constructor including metadata.
   */
  public SavingsAccountAsset(
      String name,
      String accountNumber,
      String bankName,
      BigDecimal interestRateAnnual,
      BigDecimal balance,
      Currency currency,
      List<String> tags,
      IValuation<SavingsAccountAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.valuation = Objects.requireNonNull(valuation);
    this.accountNumber = accountNumber;
    this.bankName = bankName;
    this.interestRateAnnual = interestRateAnnual;
    this.balance = balance;
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
  public BigDecimal value() {
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
