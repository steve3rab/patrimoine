package com.ketrika.patrimoine.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Utility class for validating entry
 */
public class UtilsValidator {

  // Strict but reasonable RFC-like email regex
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
      Pattern.CASE_INSENSITIVE);

  private UtilsValidator() {
    // utility class
  }

  /**
   * Validates an email address and returns an {@link Optional} containing the normalized (trimmed)
   * email if valid.
   *
   * @param email the email string to validate; may be null
   * @return an Optional containing the trimmed email if valid, empty otherwise
   */
  private static Optional<String> validateEmail(String email) {
    if (email == null) {
      return Optional.empty();
    }

    String normalized = email.trim();
    if (normalized.isEmpty()) {
      return Optional.empty();
    }

    return EMAIL_PATTERN.matcher(normalized).matches()
        ? Optional.of(normalized)
        : Optional.empty();
  }

  /**
   * Validates an email address and returns the normalized value if valid.
   * <p>
   * Throws {@link IllegalArgumentException} on invalid or null email.
   *
   * @param email the email string to validate
   * @return the trimmed, validated email
   * @throws IllegalArgumentException if the email is null or invalid
   */
  public static String requireValidEmail(String email) {
    Objects.requireNonNull(email, "email must not be null");
    return validateEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid email format: " + email));
  }
}
