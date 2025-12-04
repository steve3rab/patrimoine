package com.ketrika.patrimoine.parser;

import java.math.BigDecimal;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ketrika.patrimoine.models.assets.BankAccountAsset;
import com.ketrika.patrimoine.models.assets.FixedValuation;
import com.ketrika.patrimoine.models.assets.IAsset;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public class BankAssetParser implements ITypedJsonParser<IAsset> {

  private static final Logger LOGGER = LogManager.getLogger(BankAssetParser.class);

  @Override
  public Optional<IAsset> parse(JsonNode node) {
    if (UtilsJson.missing(node)) {
      return Optional.empty();
    }

    try {
      var name = UtilsJson.text(node, "name").orElseThrow();
      var iban = UtilsJson.text(node, "iban").orElseThrow();
      var value = new BigDecimal(UtilsJson.text(node, "value").orElse("0"));

      return Optional.of(new BankAccountAsset(
          name,
          iban,
          new FixedValuation<>(value)));

    } catch (Exception ex) {
      LOGGER.error("Invalid bank asset: {}", ex.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public String type() {
    return "bank";
  }

}
