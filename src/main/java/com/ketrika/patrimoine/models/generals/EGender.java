package com.ketrika.patrimoine.models.generals;

/**
 * Enumeration representing the gender of a user.
 */
public enum EGender {

  /** Male gender */
  MASCULINE("Masculine"),

  /** Female gender */
  FEMININE("Feminine"),

  /** Other / Non-binary / undisclosed gender. Used for inclusivity. */
  OTHER("Other");

  private final String displayName;

  /**
   * Constructs a gender enum.
   *
   * @param displayName name of the gender
   */
  EGender(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
