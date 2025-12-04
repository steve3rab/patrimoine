package com.ketrika.patrimoine.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.ketrika.patrimoine.models.assets.IAsset;
import com.ketrika.patrimoine.models.liabilities.ILiability;
import com.ketrika.patrimoine.models.participations.IParticipation;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public final class FinancialParsersRegistry {

  private final Map<String, ITypedJsonParser<IAsset>> assetParsers = new HashMap<>();
  private final Map<String, ITypedJsonParser<ILiability>> liabilityParsers = new HashMap<>();
  private final Map<String, ITypedJsonParser<IParticipation>> participationParsers = new HashMap<>();

  public FinancialParsersRegistry() {
    registerAsset(new BankAssetParser());
  }

  public void registerAsset(ITypedJsonParser<IAsset> parser) {
    assetParsers.put(parser.type(), parser);
  }

  public void registerLiability(ITypedJsonParser<ILiability> parser) {
    liabilityParsers.put(parser.type(), parser);
  }

  public void registerParticipation(ITypedJsonParser<IParticipation> parser) {
    participationParsers.put(parser.type(), parser);
  }

  public Optional<IAsset> parseAsset(JsonNode json) {
    return resolve(json, assetParsers);
  }

  public Optional<ILiability> parseLiability(JsonNode json) {
    return resolve(json, liabilityParsers);
  }

  public Optional<IParticipation> parseParticipation(JsonNode json) {
    return resolve(json, participationParsers);
  }

  private <T> Optional<T> resolve(JsonNode json, Map<String, ITypedJsonParser<T>> map) {
    return UtilsJson.text(json, "type")
        .map(map::get)
        .map(parser -> parser.parse(json))
        .orElse(Optional.empty());
  }
}
