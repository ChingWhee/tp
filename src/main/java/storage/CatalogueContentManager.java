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
    String directoryName = "data";
    Path basePath = Paths.get(directoryName);

    String inventoryFileName = "inventory_catalogue.txt";
    Path inventoryFilePath;
    String shoppingFileName = "shopping_catalogue.txt";
    Path shoppingFilePath;
    String recipeFileName = "recipe_catalogue.txt";
    Path recipeFilePath;

    public CatalogueContentManager() {}

    public IngredientCatalogue loadIngredientCatalogue() {
        inventoryFilePath = basePath.resolve(inventoryFileName);

        return loadConsumablesCatalogue(inventoryFilePath);
    }

    public IngredientCatalogue loadShoppingCatalogue() {
        shoppingFilePath = basePath.resolve(shoppingFileName);

        return loadConsumablesCatalogue(shoppingFilePath);
    }

    public IngredientCatalogue loadConsumablesCatalogue(Path filePath) {
        List<String> lines = loadRawCatalogueContent(filePath);
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

    // TODO: Define the text format for Recipe.
    public RecipeCatalogue loadRecipeCatalogue() {
        recipeFilePath = basePath.resolve(recipeFileName);

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

    public List<String> loadRawCatalogueContent(Path filePath) {
        if (filePath == null) {
            System.err.println("Error: File path is null.");
            return null;
        }
        try {
            checkDirectoryExistence();

            if (Files.exists(filePath)) {
                return Files.readAllLines(filePath);
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

            Files.writeString(inventoryFilePath, content + "\n", StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error handling file: " + e.getMessage());
        }
    }

    private void checkDirectoryExistence() throws IOException {
        // Ensure the directory exists
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
            // System.out.println("Directory created: " + BASE_PATH.toAbsolutePath());
        }
    }

    private void checkInventoryFileExistence() throws IOException {
        if (inventoryFilePath == null) {
            inventoryFilePath = basePath.resolve(inventoryFileName);
        }

        if (!Files.exists(inventoryFilePath)) {
            Files.createFile(inventoryFilePath);
        }
    }
}
