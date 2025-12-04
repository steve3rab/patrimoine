package patrimoinecom.ketrika.patrimoine.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.ketrika.patrimoine.core.ImportCommand;
import com.ketrika.patrimoine.models.generals.AssetsCalculation;
import com.ketrika.patrimoine.models.generals.EGender;
import com.ketrika.patrimoine.models.generals.Person;
import picocli.CommandLine;

class ImportCommandIT {

  @Test
  void testMissingFile() {
    ImportCommand command = new ImportCommand();
    CommandLine cli = new CommandLine(command);

    int exitCode = cli.execute("-f", "not-existing-file.json");
    assertEquals(1, exitCode, "Should fail when file does not exist");
    assertTrue(command.getPersons().isEmpty());
  }

  @Test
  void testImportFromTestResourceJson() throws Exception {

    // -------- Load JSON file from src/test/resources --------
    URL resource = getClass().getClassLoader().getResource("import.json");
    assertNotNull(resource, "import.json must exist under src/test/resources");

    File jsonFile = new File(resource.toURI());
    assertTrue(jsonFile.exists(), "JSON file must exist");

    // -------- Prepare command --------
    ImportCommand command = new ImportCommand();
    CommandLine cli = new CommandLine(command);

    // -------- Act --------
    int exitCode = cli.execute("-f", jsonFile.getAbsolutePath());

    // -------- Assert exit code --------
    assertEquals(0, exitCode, "Import should succeed");

    // -------- Assert persons imported --------
    List<Person> persons = command.getPersons();
    assertNotNull(persons);
    assertEquals(1, persons.size());

    Person person = persons.get(0);
    assertEquals("Alice", person.getFirstName());
    assertEquals("Brown", person.getLastName());
    assertEquals(EGender.FEMININE, person.getGender());

    // -------- Contact --------
    assertNotNull(person.getContact());
    assertEquals("alice@example.com", person.getContact().getEmail());
    assertEquals("111-222", person.getContact().getPhone());

    // -------- Address --------
    assertNotNull(person.getAddress());
    assertEquals("1 Main St", person.getAddress().getStreet());

    // -------- Birth --------
    assertNotNull(person.getBirth());
    assertEquals("1990-01-01", person.getBirth().getDate().toString());

    // -------- Assets --------
    assertEquals(1, person.getAssets().size());
    assertEquals("Primary Account", person.getAssets().get(0).name());

    // -------- Total Assets --------
    assertTrue(person.calculate(new AssetsCalculation()).compareTo(BigDecimal.valueOf(1500)) == 0);
  }
}
