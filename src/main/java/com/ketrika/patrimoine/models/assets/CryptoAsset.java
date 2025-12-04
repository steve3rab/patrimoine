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

  /**
   * Constructs a new CryptoAsset.
   * 
   * @param name
   * @param symbol
   * @param quantity
   * @param valuation
   * @throws NullPointerException if any argument is null
   */
  public CryptoAsset(String name, String symbol, BigDecimal quantity, IValuation<CryptoAsset> valuation) {
    this(name, symbol, quantity, null, null, null, null, null, null, valuation);
  }

  /**
   * Constructs a CryptoAsset with optional metadata
   */
  public CryptoAsset(
      String name,
      String symbol,
      BigDecimal quantity,
      String network,
      String walletAddress,
      Instant acquiredAt,
      BigDecimal purchasePrice,
      Currency currency,
      List<String> tags,
      IValuation<CryptoAsset> valuation) {
    this.name = Objects.requireNonNull(name);
    this.symbol = Objects.requireNonNull(symbol);
    this.quantity = Objects.requireNonNull(quantity);
    this.network = network;
    this.walletAddress = walletAddress;
    this.acquiredAt = acquiredAt;
    this.purchasePrice = purchasePrice;
    this.currency = currency;
    this.valuation = Objects.requireNonNull(valuation);
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
  public BigDecimal value() {
    return valuation.calculate(this);
  }

  @Override
  public String name() {
    return name;
  }
}
