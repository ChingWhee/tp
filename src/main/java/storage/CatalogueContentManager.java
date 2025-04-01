package storage;

import model.Ingredient;

import model.catalogue.Catalogue;

import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatalogueContentManager {
    String directoryName = "data";
    String inventoryFileName = "inventory.txt";
    String recipeBookFileName = "recipe_book.txt";
    String shoppingCatalogueFileName = "shopping_catalogue.txt";

    Path basePath = Paths.get(directoryName);
    Path inventoryFilePath = basePath.resolve(inventoryFileName);
    Path recipeBookFilePath = basePath.resolve(recipeBookFileName);
    Path shoppingCatalogueFilePath = basePath.resolve(shoppingCatalogueFileName);

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

    String currentRecipeName = null;
    List<String> currentIngredients = new ArrayList<>();

    public RecipeBook loadRecipeBook() throws IOException {
        checkDirectoryExistence();
        checkFileExistence(recipeBookFilePath);

        assert recipeBookFilePath.toFile().exists();

        List<String> lines = loadRawCatalogueContent(recipeBookFilePath);
        RecipeBook storageRecipe = new RecipeBook();

        if (lines == null || lines.isEmpty()) {
            return storageRecipe;
        }

        String currentRecipeName = null;
        ArrayList<Ingredient> currentIngredients = new ArrayList<Ingredient>();

        for (String line : lines) {
            line = line.trim();  // Remove leading and trailing whitespaces

            if (line.isEmpty()) {
                // Blank line: End of a recipe, so add the current recipe to storage
                if (currentRecipeName != null) {
                    storageRecipe.addItem(new Recipe(currentRecipeName, currentIngredients));
                    currentRecipeName = null;
                    currentIngredients = new ArrayList<>();
                }
            } else if (currentRecipeName == null) {
                // First non-empty line: Recipe name
                currentRecipeName = line;
            } else {
                // Ingredient line: Inline parsing
                String regex = "^(.*)\\s\\((\\d+)\\)$";  // Match ingredient (quantity) format
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    String ingredientName = matcher.group(1);  // Ingredient name
                    int quantity = Integer.parseInt(matcher.group(2));  // Ingredient quantity
                    currentIngredients.add(new Ingredient(ingredientName, quantity));
                } else {
                    // Handle invalid format if necessary
                    System.out.println("Invalid ingredient format: " + line);
                }
            }
        }

        // Add the last recipe if the file doesn't end with a blank line
        if (currentRecipeName != null) {
            storageRecipe.addItem(new Recipe(currentRecipeName, currentIngredients));
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
            String catalogueName = catalogue.getType();
            switch (catalogueName) {
            case "Inventory":
                filePath = inventoryFilePath;
                break;
            case "RecipeBook":
                filePath = recipeBookFilePath;
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
