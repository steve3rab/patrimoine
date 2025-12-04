package com.ketrika.patrimoine.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import tools.jackson.databind.JsonNode;

/**
 * Utility helper methods for safely extracting values from {@link JsonNode} structures.
 * <p>
 * This class provides convenience methods for reading optional text fields, parsing arrays into
 * typed lists, and checking whether a JSON node is missing. All methods are null-safe and avoid
 * throwing exceptions for absent fields.
 */
public class UtilsJson {

  /**
   * Extracts a textual field from a JSON node as an {@link Optional}.
   * <p>
   * Returns {@code Optional.empty()} if:
   * <ul>
   * <li>the parent node is {@code null}</li>
   * <li>the field does not exist, is missing, or is explicitly {@code null}</li>
   * <li>the textual value is blank or empty after trimming</li>
   * </ul>
   *
   * @param node the JSON node to read from (may be {@code null})
   * @param field the field name to extract
   * @return an {@code Optional<String>} containing the trimmed value if present
   */
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

  /**
   * Parses an array field from a JSON object into a typed {@link List}.
   * <p>
   * Each element of the array is processed using the provided parser function, which may return a
   * present or empty {@link Optional}. Only present values are included in the resulting list.
   * <p>
   * If the array does not exist, {@code node.withArray(field)} creates an empty array node, resulting
   * in an empty list.
   *
   * @param <T> the element type to produce
   * @param node the parent JSON node
   * @param field the array field name to read
   * @param parser a function converting each element node into an {@code Optional<T>}
   * @return a list of successfully parsed elements (never {@code null})
   */
  public static <T> List<T> parseArray(
      JsonNode node, String field,
      Function<JsonNode, Optional<T>> parser) {
    return StreamSupport.stream(node.withArray(field).spliterator(), false)
        .map(parser)
        .flatMap(Optional::stream)
        .toList();
  }

  /**
   * Determines whether a JSON node is effectively missing.
   *
   * @param node the node to check
   * @return {@code true} if the node represents a missing value
   */
  public static boolean missing(JsonNode node) {
    return node == null || node.isMissingNode() || node.isNull();
  }
}
