package com.ketrika.patrimoine.models.generals;

import java.util.Objects;
import com.ketrika.patrimoine.utils.UtilsValidator;

/**
 * Represents a person's contact information.
 */
public final class Contact {

  private final String email;
  private final String phone;
  private final String secondaryPhone;

  /**
   * Constructs a Contact.
   * 
   * @param email
   * @param phone
   * @throws NullPointerException if any argument is null
   */
  public Contact(String email, String phone) {
    this(email, phone, null);
  }

  /**
   * Constructs a Contact with optional details.
   * 
   * @param email
   * @param phone
   * @param secondaryPhone
   * @throws NullPointerException if two first arguments are null
   */
  public Contact(String email, String phone, String secondaryPhone) {
    this.email = UtilsValidator.requireValidEmail(email);
    this.phone = Objects.requireNonNull(phone, "phone cannot be null");
    this.secondaryPhone = secondaryPhone;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getSecondaryPhone() {
    return secondaryPhone;
  }
}
