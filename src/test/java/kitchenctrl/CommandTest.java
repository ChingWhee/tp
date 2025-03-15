package kitchenctrl;

import org.junit.jupiter.api.Test;
import commands.Commands;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import backend.storage.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CommandTest {
    @Test
    public void insufficientIngredients() {
        Commands commands = new Commands();
        commands.addIngredient("Flour", 1000);
        commands.addIngredient("Eggs", 4);
        commands.addIngredient("Milk", 250); // Not enough!

        List<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Flour", 500));
        recipe.add(new Ingredient("Eggs", 2));
        recipe.add(new Ingredient("Milk", 300));

        assertFalse(commands.canCookRecipe(recipe), "Should not have enough ingredients");
    }

    @Test
    public void sufficientIngredients() {
        Commands commands = new Commands();
        commands.addIngredient("Flour", 1000);
        commands.addIngredient("Eggs", 4);
        commands.addIngredient("Milk", 350);

        List<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Flour", 500));
        recipe.add(new Ingredient("Eggs", 2));
        recipe.add(new Ingredient("Milk", 300));

        assertTrue(commands.canCookRecipe(recipe), "There should be enough ingredients");
    }

    @Test
    public void ingredientNotInInventory() {
        Commands commands = new Commands();
        commands.addIngredient("Flour", 1000);
        commands.addIngredient("Eggs", 4);
        // No Milk in inventory

        List<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Flour", 500));
        recipe.add(new Ingredient("Eggs", 2));
        recipe.add(new Ingredient("Milk", 300));

        assertFalse(commands.canCookRecipe(recipe), "Should return false because Milk is missing");
    }

    @Test
    public void addIngredientTest() {
        Commands commands = new Commands();
        commands.addIngredient("Sugar", 200);
        commands.addIngredient("Sugar", 100); // Should increase the quantity

        List<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Sugar", 300));

        assertTrue(commands.canCookRecipe(recipe), "Sugar should be sufficient");
    }

    @Test
    public void deleteIngredientTest() {
        Commands commands = new Commands();
        commands.addIngredient("Butter", 500);
        commands.deleteIngredient("Butter");

        List<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Butter", 100));

        assertFalse(commands.canCookRecipe(recipe), "Butter should be missing");
    }
}
