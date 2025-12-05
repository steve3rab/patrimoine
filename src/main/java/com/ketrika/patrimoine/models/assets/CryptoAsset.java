package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents cryptocurrency holdings.
 */
public final class CryptoAsset implements IAsset {

  private final String name;
  private final String symbol;
  private final BigDecimal quantity;
  private final String network;
  private final String walletAddress;
  private final Instant acquiredAt;
  private final BigDecimal purchasePrice;
  private final Currency currency;
  private final List<String> tags;
  private final IValuation<CryptoAsset> valuation;
  private final Instant createdAt;

  private CryptoAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.symbol = Objects.requireNonNull(builder.symbol);
    this.quantity = Objects.requireNonNull(builder.quantity);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.network = builder.network;
    this.walletAddress = builder.walletAddress;
    this.acquiredAt = builder.acquiredAt;
    this.purchasePrice = builder.purchasePrice;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String symbol;
    private BigDecimal quantity;
    private String network;
    private String walletAddress;
    private Instant acquiredAt;
    private BigDecimal purchasePrice;
    private Currency currency;
    private List<String> tags;
    private IValuation<CryptoAsset> valuation;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder symbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public Builder quantity(BigDecimal quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder network(String network) {
      this.network = network;
      return this;
    }

    public Builder walletAddress(String walletAddress) {
      this.walletAddress = walletAddress;
      return this;
    }

    public Builder acquiredAt(Instant acquiredAt) {
      this.acquiredAt = acquiredAt;
      return this;
    }

    public Builder purchasePrice(BigDecimal purchasePrice) {
      this.purchasePrice = purchasePrice;
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

    public Builder valuation(IValuation<CryptoAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public CryptoAsset build() {
      return new CryptoAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public String getNetwork() {
    return network;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  public Instant getAcquiredAt() {
    return acquiredAt;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getQuantity() {
    return quantity;
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
