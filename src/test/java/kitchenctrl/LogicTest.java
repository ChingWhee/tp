package kitchenctrl;

import model.Recipe;
import model.catalogue.IngredientCatalogue;
import org.junit.jupiter.api.Test;
import logic.commands.Commands;
import model.Ingredient;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LogicTest {
    @Test
    public void insufficientIngredients() {
        IngredientCatalogue testInventory = new IngredientCatalogue();
        Commands.addIngredient(testInventory,"Flour", 1000);
        Commands.addIngredient(testInventory,"Eggs", 4);
        Commands.addIngredient(testInventory,"Milk", 250); // Not enough!

        Recipe targetRecipe = new Recipe();
        targetRecipe.addItem(new Ingredient("Flour", 500));
        targetRecipe.addItem(new Ingredient("Eggs", 2));
        targetRecipe.addItem(new Ingredient("Milk", 300));

        ArrayList<Ingredient> missingIngredients = Commands.getMissingIngredients(testInventory, targetRecipe);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 50));
        assertEquals(missingIngredients, expectedMissing);
    }

    @Test
    public void sufficientIngredients() {
        IngredientCatalogue testInventory = new IngredientCatalogue();
        Commands.addIngredient(testInventory,"Flour", 1000);
        Commands.addIngredient(testInventory,"Eggs", 4);
        Commands.addIngredient(testInventory,"Milk", 350);

        Recipe recipe = new Recipe();
        recipe.addItem(new Ingredient("Flour", 500));
        recipe.addItem(new Ingredient("Eggs", 2));
        recipe.addItem(new Ingredient("Milk", 300));

        ArrayList<Ingredient> missingIngredients = Commands.getMissingIngredients(testInventory, recipe);
        assertTrue(missingIngredients.isEmpty(), "There should be enough ingredients");
    }

    @Test
    public void ingredientNotInInventory() {
        IngredientCatalogue testInventory = new IngredientCatalogue();
        Commands.addIngredient( testInventory, "Flour", 1000);
        Commands.addIngredient( testInventory, "Eggs", 4);
        // No Milk in inventory

        Recipe recipe = new Recipe();
        recipe.addItem(new Ingredient("Flour", 500));
        recipe.addItem(new Ingredient("Milk", 300));
        recipe.addItem(new Ingredient("Eggs", 2));

        ArrayList<Ingredient> missingIngredients = Commands.getMissingIngredients(testInventory, recipe);
        ArrayList<Ingredient> expectedMissing = new ArrayList<>();
        expectedMissing.add(new Ingredient("Milk", 300));
        assertEquals(missingIngredients, expectedMissing, "Should return True as 300 Milk is missing");
    }

    public boolean areIngredientsEqual(Ingredient ingredient1, Ingredient ingredient2) {
        return ingredient1.getIngredientName().equals(ingredient2.getIngredientName()) &&
                ingredient1.getQuantity() == ingredient2.getQuantity();
    }

    @Test
    public void cookRecipeTest(){
        IngredientCatalogue testInventory = new IngredientCatalogue();
        Commands.addIngredient( testInventory, "Milk", 300);
        Commands.addIngredient( testInventory, "Flour", 1000);
        Commands.addIngredient( testInventory, "Eggs", 5);

        Recipe recipe = new Recipe();
        recipe.addItem(new Ingredient("Milk", 200));
        recipe.addItem(new Ingredient("Flour", 300));
        recipe.addItem(new Ingredient("Eggs", 2));

        Commands.cookRecipe(testInventory, recipe);

        IngredientCatalogue expectedInventoryAfterCook = new IngredientCatalogue();
        expectedInventoryAfterCook.addItem(new Ingredient("Milk", 100));
        expectedInventoryAfterCook.addItem(new Ingredient("Flour", 700));
        expectedInventoryAfterCook.addItem(new Ingredient("Eggs", 3));

        for (int i = 0; i < expectedInventoryAfterCook.getItems().size(); i++) {
            Ingredient expected = expectedInventoryAfterCook.getItems().get(i);
            Ingredient actual = testInventory.getItems().get(i);
            assertTrue(areIngredientsEqual(expected, actual), "should be equal in name and quantity");
        }
    }
}
