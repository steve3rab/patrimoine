package com.ketrika.patrimoine.core;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ketrika.patrimoine.models.generals.Person;
import com.ketrika.patrimoine.parser.PersonParser;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Command to import assets from a JSON file.
 */
@Command(name = "import", description = "Import assets from a JSON file")
public class ImportCommand implements Callable<Integer> {
  private static final Logger LOGGER = LogManager.getLogger(ImportCommand.class);

  @Option(names = {"-f", "--file"}, description = "Path to CSV file to import", required = true)
  private File jsonFile;

  private final PersonParser personParser = new PersonParser();
  private final List<Person> persons = new CopyOnWriteArrayList<>();

  @Override
  public Integer call() throws Exception {
    if (!jsonFile.exists()) {
      LOGGER.error("File not found: {}", jsonFile.getAbsolutePath());
      return 1;
    }

    JsonNode root;
    try {
      root = new ObjectMapper().readTree(jsonFile);
    } catch (Exception e) {
      LOGGER.error("Invalid JSON: {}", e.getMessage());
      return 1;
    }

    if (!root.isArray()) {
      LOGGER.error("JSON must be an array of Person");
      return 1;
    }

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      // Submit parsing tasks concurrently
      List<Future<Optional<Person>>> futures = StreamSupport
          .stream(root.spliterator(), false)
          .map(node -> executor.submit(() -> personParser.parse(node)))
          .toList();
      // Collect results
      for (Future<Optional<Person>> future : futures) {
        try {
          future.get().ifPresent(persons::add);
        } catch (ExecutionException ex) {
          LOGGER.error("Failed to parse a person entry: {}", ex.getCause().getMessage());
        }
      }
    }

    LOGGER.trace("Successfully imported: {}", persons.size());
    return 0;
  }

  public List<Person> getPersons() {
    return persons;
  }
}
