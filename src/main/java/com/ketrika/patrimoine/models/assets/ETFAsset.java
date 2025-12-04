package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Represents ETF.
 */
public final class ETFAsset implements IAsset {

  private final String name;
  private final String ticker;
  private final int shares;
  private final String exchange;
  private final String fundManager;
  private final BigDecimal expenseRatio;
  private final BigDecimal purchasePrice;
  private final Instant acquiredAt;
  private final List<String> tags;
  private final IValuation<ETFAsset> valuation;
  private final Instant createdAt;

  /**
   * Constructs a new ETFAsset.
   * 
   * @param name
   * @param ticker
   * @param shares
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public ETFAsset(String name, String ticker, int shares, IValuation<ETFAsset> valuation) {
    this(name, ticker, shares, null, null, null, null, null, null, valuation);
  }

  /**
   * Constructs an ETFAsset with optional metadata
   */
  public ETFAsset(
      String name,
      String ticker,
      int shares,
      String exchange,
      String fundManager,
      BigDecimal expenseRatio,
      BigDecimal purchasePrice,
      Instant acquiredAt,
      List<String> tags,
      IValuation<ETFAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.ticker = Objects.requireNonNull(ticker);
    this.shares = Objects.requireNonNull(shares);
    this.exchange = exchange;
    this.fundManager = fundManager;
    this.expenseRatio = expenseRatio;
    this.purchasePrice = purchasePrice;
    this.acquiredAt = acquiredAt;
    this.valuation = Objects.requireNonNull(valuation);
    this.tags = tags != null ? List.copyOf(tags) : null;
    this.createdAt = Instant.now();
  }

  public String getExchange() {
    return exchange;
  }

  public String getFundManager() {
    return fundManager;
  }

  public BigDecimal getExpenseRatio() {
    return expenseRatio;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public Instant getAcquiredAt() {
    return acquiredAt;
  }

  public String getTicker() {
    return ticker;
  }

  public int getShares() {
    return shares;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  @Override
  public List<String> tags() {
    return tags != null ? tags : IAsset.super.tags();
  }

  @Override
  public BigDecimal value() {
    // Value = PricePerShare × NumberOfShares
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
