package patrimoinecom.ketrika.patrimoine.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.ketrika.patrimoine.utils.UtilsCurrency;

class UtilsCurrencyTest {

  private static final Currency MGA = Currency.getInstance("MGA");
  private static final Currency EUR = Currency.getInstance("EUR");
  private static final Currency GBP = Currency.getInstance("GBP");

  // ------------------------------------------------------------
  // convert(amount, from, to, rate)
  // ------------------------------------------------------------

  @Test
  @DisplayName("Converts using a valid rate")
  void testConvertWithRate() {
    BigDecimal amount = new BigDecimal("100");
    BigDecimal rate = new BigDecimal("0.85");

    BigDecimal result = UtilsCurrency.convert(amount, MGA, EUR, rate);

    assertEquals(new BigDecimal("85.00"), result);
  }

  @Test
  @DisplayName("No conversion when source and target currencies are the same")
  void testConvertSameCurrency() {
    BigDecimal amount = new BigDecimal("123.45");

    BigDecimal result = UtilsCurrency.convert(amount, MGA, MGA, new BigDecimal("1000"));

    assertEquals(amount, result);
  }

  @Test
  @DisplayName("Throws when exchange rate is zero or negative")
  void testConvertInvalidRate() {
    BigDecimal amount = new BigDecimal("100");

    assertThrows(IllegalArgumentException.class,
        () -> UtilsCurrency.convert(amount, MGA, EUR, BigDecimal.ZERO));

    assertThrows(IllegalArgumentException.class,
        () -> UtilsCurrency.convert(amount, MGA, EUR, new BigDecimal("-1")));
  }

  @Test
  @DisplayName("Throws when arguments are null")
  void testConvertNullArguments() {
    BigDecimal rate = new BigDecimal("1");

    assertAll(
        () -> assertThrows(NullPointerException.class,
            () -> UtilsCurrency.convert(null, MGA, EUR, rate)),
        () -> assertThrows(NullPointerException.class,
            () -> UtilsCurrency.convert(BigDecimal.ONE, null, EUR, rate)),
        () -> assertThrows(NullPointerException.class,
            () -> UtilsCurrency.convert(BigDecimal.ONE, MGA, null, rate)));
  }

  @Test
  @DisplayName("Uses HALF_UP rounding to 2 decimals")
  void testRoundingBehavior() {
    BigDecimal amount = new BigDecimal("10");
    BigDecimal rate = new BigDecimal("3.33333");

    BigDecimal result = UtilsCurrency.convert(amount, MGA, EUR, rate);

    assertEquals(new BigDecimal("33.33"), result);
  }

  @Test
  @DisplayName("Handles large values correctly")
  void testLargeNumbers() {
    BigDecimal amount = new BigDecimal("1000000000000.00"); // 1 trillion
    BigDecimal rate = new BigDecimal("0.91");

    BigDecimal result = UtilsCurrency.convert(amount, MGA, EUR, rate);

    assertEquals(new BigDecimal("910000000000.00"), result);
  }

  // ------------------------------------------------------------
  // convert(amount, from, to, ratesMap)
  // ------------------------------------------------------------

  @Test
  @DisplayName("Converts using a rate map")
  void testConvertWithRatesMap() {
    Map<String, BigDecimal> map = new HashMap<>();
    map.put(UtilsCurrency.key(MGA, EUR), new BigDecimal("0.90"));

    BigDecimal amount = new BigDecimal("200");

    BigDecimal result = UtilsCurrency.convert(amount, MGA, EUR, map);

    assertEquals(new BigDecimal("180.00"), result);
  }

  @Test
  @DisplayName("Throws when rate is missing from map")
  void testMissingMapEntry() {
    Map<String, BigDecimal> map = new HashMap<>();

    assertThrows(IllegalArgumentException.class,
        () -> UtilsCurrency.convert(new BigDecimal("50"), MGA, EUR, map));
  }

  // ------------------------------------------------------------
  // key(Currency, Currency)
  // ------------------------------------------------------------

  @Test
  @DisplayName("Generates correct rate map keys")
  void testKeyGeneration() {
    String key = UtilsCurrency.key(MGA, GBP);
    assertEquals("MGA->GBP", key);
  }
}
