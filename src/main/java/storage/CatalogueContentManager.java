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

import static ui.inputparser.Parser.parseName;
import static ui.inputparser.Parser.parseQuantity;

//@@author J030104
/**
 * Handles loading and saving operations for various catalogue-related data structures,
 * such as {@link Inventory} and {@link RecipeBook}.
 * <p>
 * This class manages file paths, checks directory/file existence, and provides methods
 * for serializing/deserializing data from/to plain text files.
 */
public class CatalogueContentManager {
    private final String directoryName = "data";
    private final String inventoryFileName = "inventory.txt";
    private final String recipeBookFileName = "recipe_book.txt";

    private Path basePath = Paths.get(directoryName);
    private Path inventoryFilePath = basePath.resolve(inventoryFileName);
    private Path recipeBookFilePath = basePath.resolve(recipeBookFileName);

    public CatalogueContentManager() {

    }

    /**
     * Loads the inventory from the inventory file.
     *
     * @return the loaded {@link Inventory} object
     * @throws IOException if an I/O error occurs while accessing the file
     */
    public Inventory loadInventory() throws IOException {
        checkDirectoryExistence();
        checkFileExistence(inventoryFilePath);

        assert inventoryFilePath.toFile().exists();

        return loadConsumablesCatalogue(inventoryFilePath, Inventory::new);
    }

    /**
     * Loads a consumable-type catalogue (like inventory list) from a specified file.
     *
     * @param filePath  the path to the file containing the catalogue data
     * @param catalogue a supplier for the catalogue type (e.g., {@code Inventory::new})
     * @param <T>       the type of catalogue extending {@link Inventory}
     * @return the populated catalogue
     */
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
                    String itemName = parseName(parts[0].trim());
                    int quantity = parseQuantity(parts[1].trim());
                    if (quantity > 0) {
                        Ingredient i = new Ingredient(itemName, quantity);
                        ingredientCatalogue.addItem(i, true);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid entry: " + line);
                }
            }
        }
        return ingredientCatalogue;
    }

    /**
     * Loads the recipe book from the recipe book file.
     *
     * @return the loaded {@link RecipeBook}
     * @throws IOException if an I/O error occurs while accessing the file
     */
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
                    storageRecipe.addItem(new Recipe(currentRecipeName, currentIngredients), true);
                    currentRecipeName = null;
                    currentIngredients = new ArrayList<>();
                }
            } else if (currentRecipeName == null) {
                // First non-empty line: Recipe name
                currentRecipeName = line;
            } else {
                // Ingredient line: Inline parsing
                String regex = "^(.+?)\\s*\\((\\d+)\\)$";// Match ingredient (quantity) format
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    String ingredientName = parseName(matcher.group(1));  // Ingredient name
                    int quantity = parseQuantity(matcher.group(2));  // Ingredient quantity
                    if (quantity > 0) {
                        currentIngredients.add(new Ingredient(ingredientName, quantity));
                    }
                } else {
                    // Handle invalid format if necessary
                    System.out.println("Invalid ingredient format: " + line);
                }
            }
        }

        // Add the last recipe if the file doesn't end with a blank line
        if (currentRecipeName != null) {
            storageRecipe.addItem(new Recipe(currentRecipeName, currentIngredients), true);
        }

        return storageRecipe;
    }

    /**
     * Reads raw lines from a catalogue file.
     *
     * @param filePath the path to the file
     * @return a list of strings read from the file, or {@code null} if reading fails
     */
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

    /**
     * Saves a catalogue to its corresponding file.
     * Supported catalogue types: Inventory, RecipeBook.
     *
     * @param catalogue the {@link Catalogue} to save
     */
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

    /**
     * Ensures that the base directory exists, creating it if necessary.
     *
     * @throws IOException if directory creation fails
     */
    private void checkDirectoryExistence() throws IOException {
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }

    /**
     * Ensures that the specified file exists, creating it if necessary.
     *
     * @param filePath the path to the file to check
     * @throws IOException if file creation fails
     */
    private void checkFileExistence(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Sets the base directory path where data files are stored.
     *
     * @param basePath the base directory path
     */
    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    /**
     * Sets the file path for the inventory file.
     *
     * @param inventoryFilePath the path to the inventory file
     */
    public void setInventoryFilePath(Path inventoryFilePath) {
        this.inventoryFilePath = inventoryFilePath;
    }

    /**
     * Sets the file path for the recipe book file.
     *
     * @param recipeBookFilePath the path to the recipe book file
     */
    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        this.recipeBookFilePath = recipeBookFilePath;
    }

    /**
     * Returns the base path used for storing data files.
     *
     * @return the base path
     */
    public Path getBasePath() {
        return basePath;
    }

    /**
     * Returns the file path for the inventory data file.
     *
     * @return the inventory file path
     */
    public Path getInventoryFilePath() {
        return inventoryFilePath;
    }

    /**
     * Returns the file path for the recipe book data file.
     *
     * @return the recipe book file path
     */
    public Path getRecipeBookFilePath() {
        return recipeBookFilePath;
    }
}
