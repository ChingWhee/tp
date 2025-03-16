package model.catalogue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;

import model.Ingredient;

public class CatalogueContentManager {
    @SuppressWarnings("checkstyle:MemberName")
    final String DIRECTORY_NAME = "data";
    @SuppressWarnings("checkstyle:MemberName")
    final Path BASE_PATH = Paths.get(DIRECTORY_NAME);

    String fileName;
    Path filePath;

    public CatalogueContentManager(String catalogueName) {
        fileName = catalogueName + ".txt";
        filePath = BASE_PATH.resolve(fileName);
    }

    public void loadCatalogue(IngredientCatalogue catalogue) {
        try {
            checkDirectoryExistence();

            List<String> lines = null;
            if (Files.exists(filePath)) {
                lines = Files.readAllLines(filePath);
                System.out.println("Catalogue loaded from file.");
            }

            if (lines == null || lines.isEmpty()) {
                System.out.println("No ingredients found.");
                return;
            }

            assert lines != null;
            for (String line : lines) {
                String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
                if (parts.length == 2) {
                    try {
                        String itemName = parts[0].trim();
                        int quantity = Integer.parseInt(parts[1].trim());
                        Ingredient i = new Ingredient(itemName, quantity);
                        catalogue.addItem(i);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid entry: " + line);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading file. Msg: " + e.getMessage());
        }
    }


    public void saveCatalogue(String content) {
        try {
            checkDirectoryExistence();
            checkFileExistence();

            // Write content to file
            Files.writeString(filePath, content + "\n", StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Data written to file.");
        } catch (Exception e) {
            System.err.println("Error writing file. Msg: " + e.getMessage());
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
