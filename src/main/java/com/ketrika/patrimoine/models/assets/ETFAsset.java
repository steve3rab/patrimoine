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

  private ETFAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.ticker = Objects.requireNonNull(builder.ticker);
    this.shares = Objects.requireNonNull(builder.shares);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.exchange = builder.exchange;
    this.fundManager = builder.fundManager;
    this.expenseRatio = builder.expenseRatio;
    this.purchasePrice = builder.purchasePrice;
    this.acquiredAt = builder.acquiredAt;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String ticker;
    private Integer shares; // use Integer to allow null check
    private String exchange;
    private String fundManager;
    private BigDecimal expenseRatio;
    private BigDecimal purchasePrice;
    private Instant acquiredAt;
    private List<String> tags;
    private IValuation<ETFAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder ticker(String ticker) {
      this.ticker = ticker;
      return this;
    }

    public Builder shares(int shares) {
      this.shares = shares;
      return this;
    }

    public Builder exchange(String exchange) {
      this.exchange = exchange;
      return this;
    }

    public Builder fundManager(String fundManager) {
      this.fundManager = fundManager;
      return this;
    }

    public Builder expenseRatio(BigDecimal expenseRatio) {
      this.expenseRatio = expenseRatio;
      return this;
    }

    public Builder purchasePrice(BigDecimal purchasePrice) {
      this.purchasePrice = purchasePrice;
      return this;
    }

    public Builder acquiredAt(Instant acquiredAt) {
      this.acquiredAt = acquiredAt;
      return this;
    }

    public Builder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public Builder valuation(IValuation<ETFAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public ETFAsset build() {
      return new ETFAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

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
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
