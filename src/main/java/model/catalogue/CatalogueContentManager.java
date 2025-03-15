package model.catalogue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;

import model.Ingredient;

public class CatalogueContentManager {
    final String DIRECTORY_NAME = "data";
    final Path BASE_PATH = Paths.get(DIRECTORY_NAME);

    String fileName;
    Path filePath;

    public CatalogueContentManager(String catalogueName) {
        fileName = catalogueName + ".txt";
        filePath = BASE_PATH.resolve(fileName);
    }

    public List<String> loadRawCatalogueContent() {
        try {
            checkDirectoryExistence();

            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
//                for (String line : lines) {
//                    String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
//                    if (parts.length == 2) {
//                        String itemName = parts[0].trim();
//                        int quantity = Integer.parseInt(parts[1].trim());
//                    }
//                }
                System.out.println("Catalogue loaded from file.");
                return lines;
            }
        } catch (Exception e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
        return null;
    }

    public void saveCatalogue(String content) {
        try {
            checkDirectoryExistence();
            checkFileExistence();

            // Write content to file
            Files.writeString(filePath, content + "\n", StandardOpenOption.WRITE);
            System.out.println("Data written to file.");
        } catch (Exception e) {
            System.err.println("Error handling file: " + e.getMessage());
        }
    }

    private void checkDirectoryExistence() throws IOException {
        // Ensure the directory exists
        if (!Files.exists(BASE_PATH)) {
            Files.createDirectories(BASE_PATH);
            System.out.println("Directory created: " + BASE_PATH.toAbsolutePath());
        }
    }

    private void checkFileExistence() throws IOException {
        // Ensure the file exists
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            System.out.println("File created: " + filePath.toAbsolutePath());
        }
    }
}
