package kitchenctrl;

import commands.CommandResult;
import commands.CookRecipeCommand;
import controller.ScreenState;
import model.Recipe;
import model.catalogue.IngredientCatalogue;
import model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static controller.ScreenState.RECIPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class LogicTest {
    private IngredientCatalogue testInventory;
    private ScreenState testScreen;

    @BeforeEach
    public void setUp() {
        testInventory = new IngredientCatalogue();
        testScreen = RECIPE;
    }

    @Test
    public void insufficientIngredients() {
        testInventory.addItem(new Ingredient("Flour", 1000));
        testInventory.addItem(new Ingredient("Eggs", 4));
        testInventory.addItem(new Ingredient("Milk", 250)); // Not enough!

        Recipe targetRecipe = new Recipe("Pancakes");
        targetRecipe.addItem(new Ingredient("Flour", 500));
        targetRecipe.addItem(new Ingredient("Eggs", 2));
        targetRecipe.addItem(new Ingredient("Milk", 300));

        CookRecipeCommand command = new CookRecipeCommand(testScreen, targetRecipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 50));

        assertEquals(expectedMissing, missingIngredients);
    }

    @Test
    public void sufficientIngredients() {
        testInventory.addItem(new Ingredient("Flour", 1000));
        testInventory.addItem(new Ingredient("Eggs", 4));
        testInventory.addItem(new Ingredient("Milk", 350));

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Flour", 500));
        recipe.addItem(new Ingredient("Eggs", 2));
        recipe.addItem(new Ingredient("Milk", 300));

        CookRecipeCommand command = new CookRecipeCommand(testScreen, recipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);

        assertTrue(missingIngredients.isEmpty(), "There should be enough ingredients");
    }

    @Test
    public void ingredientNotInInventory() {
        testInventory.addItem(new Ingredient("Flour", 1000));
        testInventory.addItem(new Ingredient("Eggs", 4));
        // No Milk in inventory

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Flour", 500));
        recipe.addItem(new Ingredient("Milk", 300));
        recipe.addItem(new Ingredient("Eggs", 2));

        CookRecipeCommand command = new CookRecipeCommand(testScreen, recipe);
        ArrayList<Ingredient> missingIngredients = command.getMissingIngredients(testInventory);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 300));

        assertEquals(expectedMissing, missingIngredients, "Should return True as 300 Milk is missing");
    }

    @Test
    public void cookRecipeTest() {
        testInventory.addItem(new Ingredient("Milk", 300));
        testInventory.addItem(new Ingredient("Flour", 1000));
        testInventory.addItem(new Ingredient("Eggs", 5));

        Recipe recipe = new Recipe("Pancakes");
        recipe.addItem(new Ingredient("Milk", 200));
        recipe.addItem(new Ingredient("Flour", 300));
        recipe.addItem(new Ingredient("Eggs", 2));

        CookRecipeCommand command = new CookRecipeCommand(testScreen, recipe);
        CommandResult result = command.execute(testInventory);

        assertEquals("Recipe successfully cooked: Pancakes", result.getFeedbackToUser());

        assertEquals(100, testInventory.getItemByName("Milk").getQuantity());
        assertEquals(700, testInventory.getItemByName("Flour").getQuantity());
        assertEquals(3, testInventory.getItemByName("Eggs").getQuantity());
    }
}
