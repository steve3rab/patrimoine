package com.ketrika.patrimoine.models.assets;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * A bank account asset.
 */
public final class BankAccountAsset implements IAsset {

  private final String name;
  private final String iban;
  private final String bic;
  private final String bankName;
  private final String accountHolder;
  private final Boolean jointAccount;
  private final IValuation<BankAccountAsset> valuation;
  private final Instant createdAt;
  private final Instant openedAt;
  private final Currency currency;
  private final List<String> tags;

  private BankAccountAsset(Builder builder) {
    this.name = Objects.requireNonNull(builder.name);
    this.iban = Objects.requireNonNull(builder.iban);
    this.valuation = Objects.requireNonNull(builder.valuation);

    this.bankName = builder.bankName;
    this.bic = builder.bic;
    this.accountHolder = builder.accountHolder;
    this.jointAccount = builder.jointAccount;
    this.openedAt = builder.openedAt;
    this.currency = builder.currency;
    this.tags = builder.tags != null ? List.copyOf(builder.tags) : null;

    this.createdAt = Instant.now();
  }

  // -------------------------
  // BUILDER
  // -------------------------
  public static class Builder {
    private String name;
    private String iban;
    private String bic;
    private String bankName;
    private String accountHolder;
    private Boolean jointAccount = Boolean.FALSE;
    private IValuation<BankAccountAsset> valuation;
    private Instant openedAt;
    private Currency currency;
    private List<String> tags;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder iban(String iban) {
      this.iban = iban;
      return this;
    }

    public Builder bankName(String bankName) {
      this.bankName = bankName;
      return this;
    }

    public Builder bic(String bic) {
      this.bic = bic;
      return this;
    }

    public Builder accountHolder(String accountHolder) {
      this.accountHolder = accountHolder;
      return this;
    }

    public Builder jointAccount(Boolean jointAccount) {
      this.jointAccount = jointAccount;
      return this;
    }

    public Builder openedAt(Instant openedAt) {
      this.openedAt = openedAt;
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

    public Builder valuation(IValuation<BankAccountAsset> valuation) {
      this.valuation = valuation;
      return this;
    }

    public BankAccountAsset build() {
      return new BankAccountAsset(this);
    }
  }

  // ---------------------------------
  // Getters and interface overrides
  // ---------------------------------

  public String getAccountHolder() {
    return accountHolder;
  }

  public String getBankName() {
    return bankName;
  }

  public String getBic() {
    return bic;
  }

  public Instant getOpenedAt() {
    return openedAt;
  }

  public boolean isJointAccount() {
    return jointAccount;
  }

  public String getIban() {
    return iban;
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
