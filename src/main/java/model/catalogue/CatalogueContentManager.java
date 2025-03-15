package model.catalogue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;

public class CatalogueContentManager {
    final String DIRECTORY_NAME = "data";
    final Path BASE_PATH = Paths.get(DIRECTORY_NAME);

    String fileName;
    Path filePath;

    public CatalogueContentManager(String catalogueName) {
        fileName = catalogueName + ".txt";
        filePath = BASE_PATH.resolve(fileName);
    }

    public void loadCatalogue() {
        try {
            // Ensure the directory exists
            if (!Files.exists(BASE_PATH)) {
                Files.createDirectories(BASE_PATH);
                System.out.println("Directory created: " + BASE_PATH.toAbsolutePath());
            }

            // Ensure the file exists
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("New file created: " + filePath.toAbsolutePath());
            } else {
                // Read and display file contents
                List<String> lines = Files.readAllLines(filePath);
                System.out.println("Catalogue contents:");
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    public void saveCatalogue(String content) {
        try {
            // Ensure the directory exists
            if (!Files.exists(BASE_PATH)) {
                Files.createDirectories(BASE_PATH);
                System.out.println("Directory created: " + BASE_PATH.toAbsolutePath());
            }

            // Ensure the file exists
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("File created: " + filePath.toAbsolutePath());
            }

            // Write content to file
            Files.writeString(filePath, content + "\n", StandardOpenOption.APPEND);
            System.out.println("Data written to file.");

        } catch (Exception e) {
            System.err.println("Error handling file: " + e.getMessage());
        }
    }
}
