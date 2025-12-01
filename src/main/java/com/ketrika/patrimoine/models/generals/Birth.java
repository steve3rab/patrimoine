package com.ketrika.patrimoine.models.generals;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents immutable birth information for an individual.
 */
public final class Birth {

  private final LocalDate date;
  private final Address location;

  /**
   * Constructs Birth.
   *
   * @param date the date of birth
   * @param location the detailed address of birth
   * @throws IllegalArgumentException if the birth date is in the future
   */
  public Birth(LocalDate date, Address location) {
    this.date = Objects.requireNonNull(date, "date cannot be null");
    this.location = Objects.requireNonNull(location, "location cannot be null");

    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("birth date cannot be in the future: " + date);
    }
  }

  public LocalDate getDate() {
    return date;
  }

  public Address getPlace() {
    return location;
  }
}
