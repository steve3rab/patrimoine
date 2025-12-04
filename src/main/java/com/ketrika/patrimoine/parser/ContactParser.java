package com.ketrika.patrimoine.parser;

import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ketrika.patrimoine.models.generals.Contact;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public final class ContactParser {

  private static final Logger LOGGER = LogManager.getLogger(ContactParser.class);

  private ContactParser() {}

  public static Optional<Contact> parse(JsonNode node) {
    if (UtilsJson.missing(node)) {
      return Optional.empty();
    }

    var email = UtilsJson.text(node, "email").orElse(null);
    var phone = UtilsJson.text(node, "phone").orElse(null);
    var secondary = UtilsJson.text(node, "secondaryPhone").orElse(null);

    if (email == null || phone == null) {
      LOGGER.error("Skipping contact: missing email or phone.");
      return Optional.empty();
    }

    try {
      return Optional.of(new Contact(email, phone, secondary));
    } catch (Exception e) {
      LOGGER.error("Invalid contact data: {}", e.getMessage());
      return Optional.empty();
    }
  }
}
