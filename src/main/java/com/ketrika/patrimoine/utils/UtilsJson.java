package com.ketrika.patrimoine.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import tools.jackson.databind.JsonNode;

public class UtilsJson {

  public static Optional<String> text(JsonNode node, String field) {
    if (node == null) {
      return Optional.empty();
    }
    JsonNode n = node.get(field);
    if (missing(n)) {
      return Optional.empty();
    }
    String v = n.asString().trim();
    return v.isEmpty() ? Optional.empty() : Optional.of(v);
  }

  public static <T> List<T> parseArray(
      JsonNode node, String field,
      Function<JsonNode, Optional<T>> parser) {
    return StreamSupport.stream(node.withArray(field).spliterator(), false)
        .map(parser)
        .flatMap(Optional::stream)
        .toList();
  }

  public static boolean missing(JsonNode node) {
    return node == null || node.isMissingNode() || node.isNull();
  }
}
