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
        System.out.println("Temp dir created at: " + dataDir.toAbsolutePath());
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
        inventory.addItem(new Ingredient("Salt", 100));
        inventory.addItem(new Ingredient("Butter", 666));
        manager.saveToFile(inventory);

        List<String> lines = Files.readAllLines(manager.getInventoryFilePath());
        lines = lines.stream().map(String::trim).filter(line -> !line.isEmpty()).toList();
        String expectedContent = """
                Flour (5)
                Salt (100)
                Butter (666)
                """;
        List<String> expectedLines = List.of(expectedContent.split("\n"));
        assertEquals(expectedLines, lines);
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

    @Test
    public void testLoadRecipeBookWithMultipleRecipe() throws IOException {
        String content = """
                Pancakes
                Flour (2)
                Eggs (3)
                Milk (1)
                Sugar (1)
                
                Scrambled Eggs
                Eggs (4)
                Butter (1)
                Salt (1)
                
                Grilled Cheese
                Bread (2)
                Cheese (2)
                Butter (1)
                Spice (1)
                Sauce (2)
                """;
        Files.write(manager.getRecipeBookFilePath(), content.getBytes());

        RecipeBook book = manager.loadRecipeBook();
        assertEquals(3, book.getItems().size());

        Recipe recipe1 = book.getItemByName("Pancakes");
        assertNotNull(recipe1);
        assertEquals(4, recipe1.getItems().size());

        Recipe recipe2 = book.getItemByName("Scrambled Eggs");
        assertNotNull(recipe2);
        assertEquals(3, recipe2.getItems().size());

        Recipe recipe3 = book.getItemByName("Grilled Cheese");
        assertNotNull(recipe3);
        assertEquals(5, recipe3.getItems().size());
    }
}
