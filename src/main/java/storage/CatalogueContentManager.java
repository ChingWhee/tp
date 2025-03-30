package storage;

import model.Ingredient;

import model.catalogue.Catalogue;

import model.catalogue.Inventory;
import model.catalogue.RecipeBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;
import java.util.function.Supplier;

public class CatalogueContentManager {
    String directoryName = "data";
    String inventoryFileName = "inventory.txt";
    String recipeFileName = "recipe_book.txt";

    Path basePath = Paths.get(directoryName);
    Path inventoryFilePath = basePath.resolve(inventoryFileName);
    Path recipeFilePath = basePath.resolve(recipeFileName);

    public CatalogueContentManager() {

    }

    // TODO: Combine similar methods into one method
    public Inventory loadInventory() throws IOException {
        checkDirectoryExistence();
        checkFileExistence(inventoryFilePath);

        assert inventoryFilePath.toFile().exists();

        return loadConsumablesCatalogue(inventoryFilePath, Inventory::new);
    }


    public <T extends Inventory> T loadConsumablesCatalogue(Path filePath, Supplier<T> catalogue) {
        List<String> lines = loadRawCatalogueContent(filePath);
        T ingredientCatalogue = catalogue.get();

        if (lines == null || lines.isEmpty()) {
            return ingredientCatalogue;
        }

        for (String line : lines) {
            String[] parts = line.split("\\s*\\(\\s*|\\s*\\)\\s*");
            if (parts.length == 2) {
                try {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    Ingredient i = new Ingredient(itemName, quantity);
                    ingredientCatalogue.addItem(i);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        }
        return ingredientCatalogue;
    }

    // TODO: Define the text format for Recipe.
    public RecipeBook loadRecipeBook() throws IOException {
        checkDirectoryExistence();
        checkFileExistence(recipeFilePath);

        assert recipeFilePath.toFile().exists();

        List<String> lines = loadRawCatalogueContent(recipeFilePath);
        RecipeBook storageRecipe = new RecipeBook();

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

    public void saveToFile(Catalogue catalogue) {
        try {
            Path filePath = null;
            String catalogueName = catalogue.getName();
            switch (catalogueName) {
            case "Inventory":
                filePath = inventoryFilePath;
                break;
            case "RecipeBook":
                filePath = recipeFilePath;
                break;
            default:
            }

            // Check the existence again in case the directory or file was deleted
            checkDirectoryExistence();
            checkFileExistence(filePath);

            String content = catalogue.getCatalogueContent();

            assert filePath != null;
            Files.writeString(filePath, content + "\n", StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error handling file: " + e.getMessage());
        }
    }

    private void checkDirectoryExistence() throws IOException {
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }

    private void checkFileExistence(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }
}
