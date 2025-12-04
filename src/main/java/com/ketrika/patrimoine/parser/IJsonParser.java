package com.ketrika.patrimoine.parser;

import java.util.Optional;
import tools.jackson.databind.JsonNode;

public interface IJsonParser<T> {
  Optional<T> parse(JsonNode node);
}
