package kitchenctrl;

import commands.CommandResult;
import commands.CookRecipeCommand;
import controller.KitchenCTRL;
import controller.ScreenState;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;
import model.catalogue.Inventory;
import model.Ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static controller.ScreenState.RECIPEBOOK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

public class LogicTest {
    private Inventory testInventory;
    private ScreenState testScreen;
    private Catalogue<?> currentCatalogue;

    @BeforeEach
    public void setUp() {
        // Assuming Main is the class containing the getters
        KitchenCTRL mainApp = new KitchenCTRL();  // Instantiate Main class
        mainApp.initializeCatalogues();
        testInventory = KitchenCTRL.getInventory();  // Get inventory from Main class
        testScreen = RECIPEBOOK;
        KitchenCTRL.setCurrentScreen(testScreen);
        currentCatalogue = mainApp.getCatalogueByScreen(testScreen);
    }

    @Test
    public void insufficientIngredients() {
        testInventory.removeAllIngredients();
        testInventory.addItem(new Ingredient("Flour", 1000), false);
        testInventory.addItem(new Ingredient("Eggs", 4), false);
        testInventory.addItem(new Ingredient("Milk", 250), false); // Not enough!

        Recipe targetRecipe = new Recipe("Pancakes");
        targetRecipe.addItem(new Ingredient("Flour", 500), false);
        targetRecipe.addItem(new Ingredient("Eggs", 2), false);
        targetRecipe.addItem(new Ingredient("Milk", 300), false);

        CookRecipeCommand command = new CookRecipeCommand(targetRecipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 50));

        assertEquals(expectedMissing, missingIngredients);
    }

    @Test
    public void sufficientIngredients() {
        testInventory.addItem(new Ingredient("Flour", 1000), false);
        testInventory.addItem(new Ingredient("Eggs", 4), false);
        testInventory.addItem(new Ingredient("Milk", 350), false);

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Flour", 500), false);
        recipe.addItem(new Ingredient("Eggs", 2), false);
        recipe.addItem(new Ingredient("Milk", 300), false);

        CookRecipeCommand command = new CookRecipeCommand(recipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);

        assertTrue(missingIngredients.isEmpty(), "There should be enough ingredients");
    }

    @Test
    public void ingredientNotInInventory() {
        testInventory.addItem(new Ingredient("Flour", 1000), false);
        testInventory.addItem(new Ingredient("Eggs", 4), false);
        // No Milk in inventory

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Flour", 500), false);
        recipe.addItem(new Ingredient("Milk", 300), false);
        recipe.addItem(new Ingredient("Eggs", 2), false);

        CookRecipeCommand command = new CookRecipeCommand(recipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 300));

        assertEquals(expectedMissing, missingIngredients, "Should return True as 300 Milk is missing");
    }

    @Test
    public void cookRecipeTest() {
        testInventory.removeAllIngredients();
        testInventory.addItem(new Ingredient("Milk", 300), false);
        testInventory.addItem(new Ingredient("Flour", 1000), false);
        testInventory.addItem(new Ingredient("Eggs", 5), false);

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Milk", 200), false);
        recipe.addItem(new Ingredient("Flour", 300), false);
        recipe.addItem(new Ingredient("Eggs", 2), false);

        CookRecipeCommand command = new CookRecipeCommand(recipe);
        CommandResult result = command.execute(currentCatalogue); // Pass the inventory

        assertEquals("Recipe successfully cooked: Pancakes", result.getFeedbackToUser());

        assertEquals(100, testInventory.getItemByName("Milk").getQuantity());
        assertEquals(700, testInventory.getItemByName("Flour").getQuantity());
        assertEquals(3, testInventory.getItemByName("Eggs").getQuantity());
    }
}
