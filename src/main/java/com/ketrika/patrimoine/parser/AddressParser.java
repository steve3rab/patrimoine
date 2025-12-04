package com.ketrika.patrimoine.parser;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ketrika.patrimoine.models.generals.Address;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public final class AddressParser {

  private static final Logger LOGGER = LogManager.getLogger(AddressParser.class);

  private AddressParser() {}

  public static Optional<Address> parse(JsonNode node) {
    if (UtilsJson.missing(node)) {
      return Optional.empty();
    }

    var street = UtilsJson.text(node, "street").orElse(null);
    var city = UtilsJson.text(node, "city").orElse(null);
    var state = UtilsJson.text(node, "state").orElse(null);
    var zip = UtilsJson.text(node, "zipCode").orElse(null);
    var country = UtilsJson.text(node, "country").orElse(null);

    if (street == null || city == null || state == null || zip == null || country == null) {
      LOGGER.error("Skipping address: missing required fields");
      return Optional.empty();
    }

    try {
      return Optional.of(new Address(street, city, state, zip, country));
    } catch (Exception e) {
      LOGGER.error("Invalid address: {}", e.getMessage());
      return Optional.empty();
    }
  }
}
