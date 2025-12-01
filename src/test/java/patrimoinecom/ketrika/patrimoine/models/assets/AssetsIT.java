package patrimoinecom.ketrika.patrimoine.models.assets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ketrika.patrimoine.models.assets.BankAccountAsset;
import com.ketrika.patrimoine.models.assets.CollectibleAsset;
import com.ketrika.patrimoine.models.assets.CryptoAsset;
import com.ketrika.patrimoine.models.assets.FixedValuation;
import com.ketrika.patrimoine.models.assets.MultiplicativeValuation;
import com.ketrika.patrimoine.models.assets.TrustBeneficiaryAsset;
import com.ketrika.patrimoine.models.assets.TrustBeneficiaryValuation;
import com.ketrika.patrimoine.models.generals.Address;
import com.ketrika.patrimoine.models.generals.AssetsCalculation;
import com.ketrika.patrimoine.models.generals.Birth;
import com.ketrika.patrimoine.models.generals.Contact;
import com.ketrika.patrimoine.models.generals.EGender;
import com.ketrika.patrimoine.models.generals.Person;

public class AssetsIT {

  private Person person;
  private BankAccountAsset bank;
  private CollectibleAsset collectible;
  private CryptoAsset crypto;
  private TrustBeneficiaryAsset trust;

  @BeforeEach
  void setUp() {

    // Address
    var address = new Address(
        "123H",
        "Antanetibe",
        "Antehiroka",
        "105",
        "Madagascar");

    // Birth
    var birth = new Birth(
        LocalDate.of(1990, 5, 15),
        address);

    // Contact
    var contact = new Contact(
        "fenitra.fita@example.com",
        "+2615512344");

    // Person
    person = new Person("Fenitra", "Fita", EGender.MASCULINE);
    person.setAddress(address);
    person.setContact(contact);
    person.setBirth(birth);

    // BankAccountAsset with FixedValuation
    bank = new BankAccountAsset(
        "Checking Account",
        "MG89370400440532013000",
        new FixedValuation<>(BigDecimal.valueOf(1000)));

    // Collectible with FixedValuation
    collectible = new CollectibleAsset(
        "Vintage Watch",
        "Luxury",
        new FixedValuation<>(BigDecimal.valueOf(5000)));

    // Crypto using MultiplicativeValuation
    crypto = new CryptoAsset(
        "Bitcoin",
        "BTC",
        BigDecimal.valueOf(2),
        new MultiplicativeValuation<>(
            BigDecimal.valueOf(30000),
            a -> a.getQuantity().doubleValue()));

    // TrustBeneficiaryAsset using TrustBeneficiaryValuation
    trust = new TrustBeneficiaryAsset(
        "Family Trust",
        BigDecimal.valueOf(50000),
        10,
        new TrustBeneficiaryValuation(BigDecimal.valueOf(0.05)));

    person.addAssets(List.of(bank, collectible, crypto, trust));
  }

  // ---------------------------------------------------------
  // PERSON DATA TESTS
  // ---------------------------------------------------------

  @Test
  void testPersonBasicInfo() {
    assertEquals("Fenitra", person.getFirstName());
    assertEquals("Fita", person.getLastName());
    assertEquals(EGender.MASCULINE, person.getGender());
  }

  @Test
  void testPersonContactAddressBirth() {
    assertEquals("fenitra.fita@example.com", person.getContact().getEmail());
    assertEquals("123H", person.getAddress().getStreet());
    assertEquals(LocalDate.of(1990, 5, 15), person.getBirth().getDate());
  }

  // ---------------------------------------------------------
  // INDIVIDUAL ASSET TESTS
  // ---------------------------------------------------------

  @Test
  void testBankAccountValue() {
    assertTrue(bank.value().compareTo(BigDecimal.valueOf(1000)) == 0);
  }

  @Test
  void testCollectibleValue() {
    assertEquals(BigDecimal.valueOf(5000), collectible.value());
  }

  @Test
  void testCryptoValue() {
    // 2 BTC × 30000 = 60000
    assertTrue(crypto.value().compareTo(BigDecimal.valueOf(60000)) == 0);
  }

  @Test
  void testTrustValue() {
    // PV = 50000 / (1.05^10)
    BigDecimal expected = BigDecimal.valueOf(50000)
        .divide(BigDecimal.valueOf(1.05).pow(10), 2, java.math.RoundingMode.HALF_UP);

    assertTrue(trust.value().compareTo(expected) == 0);
  }

  // ---------------------------------------------------------
  // TOTAL ASSETS CALCULATION
  // ---------------------------------------------------------

  @Test
  void testTotalAssets() {
    BigDecimal total = person.calculate(new AssetsCalculation());

    BigDecimal expected = bank.value()
        .add(collectible.value())
        .add(crypto.value())
        .add(trust.value());

    assertEquals(expected, total);
  }
}
