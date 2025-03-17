package storage;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;
import model.catalogue.RecipeCatalogue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;

public class CatalogueContentManager {
    final String DIRECTORY_NAME = "data";
    final Path BASE_PATH = Paths.get(DIRECTORY_NAME);

    String inventoryFileName;
    Path inventoryFilePath;
    String recipeFileName;
    Path recipeFilePath;
    String shoppingFileName;
    Path shoppingFilePath;

    public CatalogueContentManager() {}

    public IngredientCatalogue loadIngredientCatalogue(String catalogueName) {
        inventoryFileName = catalogueName + ".txt";
        inventoryFilePath = BASE_PATH.resolve(inventoryFileName);

        List<String> lines = loadRawCatalogueContent(inventoryFilePath);
        IngredientCatalogue storageInventory = new IngredientCatalogue();

        if (lines == null || lines.isEmpty()) {
            return storageInventory;
        }

        for (String line : lines) {
            String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
            if (parts.length == 2) {
                try {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    Ingredient i = new Ingredient(itemName, quantity);
                    storageInventory.addItem(i);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        }
        return storageInventory;
    }

    public RecipeCatalogue loadRecipeCatalogue(String catalogueName) {
        recipeFileName = catalogueName + ".txt";
        recipeFilePath = BASE_PATH.resolve(inventoryFileName);

        List<String> lines = loadRawCatalogueContent(recipeFilePath);
        RecipeCatalogue storageRecipe = new RecipeCatalogue();

        if (lines == null || lines.isEmpty()) {
            return storageRecipe;
        }

        for (String line : lines) {
            String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
            if (parts.length == 2) {
                try {
                    // Some method to parse recipe
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        }
        return storageRecipe;
    }

    public IngredientCatalogue loadShoppingCatalogue(String catalogueName) {
        shoppingFileName = catalogueName + ".txt";
        shoppingFilePath = BASE_PATH.resolve(inventoryFileName);

        List<String> lines = loadRawCatalogueContent(inventoryFilePath);
        IngredientCatalogue storageInventory = new IngredientCatalogue();

        if (lines == null || lines.isEmpty()) {
            return storageInventory;
        }

        for (String line : lines) {
            String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
            if (parts.length == 2) {
                try {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    Ingredient i = new Ingredient(itemName, quantity);
                    storageInventory.addItem(i);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        }
        return storageInventory;
    }

    public List<String> loadRawCatalogueContent(Path filePath) {
        try {
            checkDirectoryExistence();

            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                // System.out.println("Catalogue loaded from file.");
                return lines;
            }
        } catch (Exception e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
        return null;
    }

    public void saveInventoryCatalogue(String content) {
        try {
            checkDirectoryExistence();
            checkInventoryFileExistence();

            // Write content to file
            Files.writeString(inventoryFilePath, content + "\n", StandardOpenOption.TRUNCATE_EXISTING);
            // System.out.println("Data written to file.");
        } catch (Exception e) {
            System.err.println("Error handling file: " + e.getMessage());
        }
    }

    private void checkDirectoryExistence() throws IOException {
        // Ensure the directory exists
        if (!Files.exists(BASE_PATH)) {
            Files.createDirectories(BASE_PATH);
            // System.out.println("Directory created: " + BASE_PATH.toAbsolutePath());
        }
    }

    private void checkInventoryFileExistence() throws IOException {
        // Ensure the file exists
        if (!Files.exists(inventoryFilePath)) {
            Files.createFile(inventoryFilePath);
            // System.out.println("File created: " + inventoryFilePath.toAbsolutePath());
        }
    }
}
