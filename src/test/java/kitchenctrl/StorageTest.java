package kitchenctrl;

import model.Ingredient;
import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import storage.CatalogueContentManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private CatalogueContentManager manager;
    private Path dataDir;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = Files.createTempDirectory("testdata");
        manager = new CatalogueContentManager() {
            {
                // Override file paths to use temporary test directory
                setBasePath(dataDir);
                setInventoryFilePath(dataDir.resolve("inventory.txt"));
                setRecipeBookFilePath(dataDir.resolve("recipe_book.txt"));
            }
        };
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(dataDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testLoadEmptyInventory() throws IOException {
        Files.createFile(manager.getInventoryFilePath());
        Inventory inventory = manager.loadInventory();
        assertNotNull(inventory);
        assertTrue(inventory.getItems().isEmpty());
    }

    @Test
    public void testLoadInventoryWithItems() throws IOException {
        String content = "Milk (2)\nEggs (12)\n";
        Files.write(manager.getInventoryFilePath(), content.getBytes());

        Inventory inventory = manager.loadInventory();
        assertEquals(2, inventory.getItems().size());
        assertNotNull(inventory.getItemByName("Milk"));
        assertEquals(12, inventory.getItemByName("Eggs").getQuantity());
    }

    @Test
    public void testSaveInventoryToFile() throws IOException {
        Inventory inventory = new Inventory();
        inventory.addItem(new Ingredient("Flour", 5));
        manager.saveToFile(inventory);

        List<String> lines = Files.readAllLines(manager.getInventoryFilePath());
        assertEquals("Flour (5)", lines.get(0).trim());
    }

    @Test
    public void testLoadEmptyRecipeBook() throws IOException {
        Files.createFile(manager.getInventoryFilePath());
        RecipeBook book = manager.loadRecipeBook();
        assertNotNull(book);
        assertTrue(book.getItems().isEmpty());
    }

    @Test
    public void testLoadRecipeBookWithSingleRecipe() throws IOException {
        String content = """
                Pancakes
                Flour (2)
                Milk (1)

                """;
        Files.write(manager.getRecipeBookFilePath(), content.getBytes());

        RecipeBook book = manager.loadRecipeBook();
        assertEquals(1, book.getItems().size());
        Recipe recipe = book.getItemByName("Pancakes");
        assertNotNull(recipe);
        assertEquals(2, recipe.getItems().size());
    }
}
