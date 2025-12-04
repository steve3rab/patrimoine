package com.ketrika.patrimoine.parser;

import java.util.Optional;
import com.ketrika.patrimoine.models.generals.EGender;
import com.ketrika.patrimoine.models.generals.Person;
import com.ketrika.patrimoine.utils.UtilsJson;
import tools.jackson.databind.JsonNode;

public final class PersonParser implements IJsonParser<Person> {

  private final FinancialParsersRegistry registry = new FinancialParsersRegistry();

  @Override
  public Optional<Person> parse(JsonNode node) {

    var first = UtilsJson.text(node, "firstname").orElse(null);
    var last = UtilsJson.text(node, "lastname").orElse(null);
    if (first == null || last == null) {
      return Optional.empty();
    }

    EGender gender = switch (UtilsJson.text(node, "gender").orElse("")) {
      case "F" -> EGender.FEMININE;
      case "M" -> EGender.MASCULINE;
      default -> EGender.OTHER;
    };

    Person p = new Person(first, last, gender);

    ContactParser.parse(node.path("contact")).ifPresent(p::setContact);
    AddressParser.parse(node.path("address")).ifPresent(p::setAddress);
    BirthParser.parse(node.path("birth")).ifPresent(p::setBirth);

    p.addAssets(UtilsJson.parseArray(node, "assets", registry::parseAsset));
    p.addLiabilities(UtilsJson.parseArray(node, "liabilities", registry::parseLiability));
    p.addParticipations(UtilsJson.parseArray(node, "participations", registry::parseParticipation));

    return Optional.of(p);
  }
}
