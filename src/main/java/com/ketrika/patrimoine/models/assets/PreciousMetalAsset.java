package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Represents precious metal holdings such as gold, silver, or platinum.
 */
public final class PreciousMetalAsset implements IAsset {

  private final String name;
  private final BigDecimal weightInGrams;
  private final IValuation<PreciousMetalAsset> valuation;
  private final Currency currency;
  private final List<String> tags;
  private final Instant createdAt;

  private PreciousMetalAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.weightInGrams = Objects.requireNonNull(builder.weightInGrams);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private BigDecimal weightInGrams;
    private IValuation<PreciousMetalAsset> valuation;
    private Currency currency;
    private List<String> tags;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder weightInGrams(BigDecimal weightInGrams) {
      this.weightInGrams = weightInGrams;
      return this;
    }

    public Builder valuation(IValuation<PreciousMetalAsset> valuation) {
      this.valuation = valuation;
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

    public PreciousMetalAsset build() {
      return new PreciousMetalAsset(this);
    }
  }

  // -------------------------
  // GETTERS
  // -------------------------

  public BigDecimal getWeightInGrams() {
    return weightInGrams;
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
