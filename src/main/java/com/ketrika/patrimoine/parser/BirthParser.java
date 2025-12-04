package com.ketrika.patrimoine.parser;

import java.time.LocalDate;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ketrika.patrimoine.models.generals.Address;
import com.ketrika.patrimoine.models.generals.Birth;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public final class BirthParser {

  private static final Logger LOGGER = LogManager.getLogger(BirthParser.class);

  private BirthParser() {}

  public static Optional<Birth> parse(JsonNode node) {
    if (UtilsJson.missing(node)) {
      return Optional.empty();
    }

    var dateOpt = UtilsJson.text(node, "date");
    if (dateOpt.isEmpty()) {
      LOGGER.error("Skipping birth: missing date");
      return Optional.empty();
    }

    LocalDate date;
    try {
      date = LocalDate.parse(dateOpt.get());
    } catch (Exception e) {
      LOGGER.error("Skipping birth: invalid date format {}", e.getMessage());
      return Optional.empty();
    }

    Optional<Address> placeOpt = AddressParser.parse(node.path("place"));
    if (placeOpt.isEmpty()) {
      LOGGER.error("Skipping birth: invalid place address");
      return Optional.empty();
    }

    try {
      return Optional.of(new Birth(date, placeOpt.get()));
    } catch (Exception e) {
      LOGGER.error("Invalid birth entry: {}", e.getMessage());
      return Optional.empty();
    }
  }
}
