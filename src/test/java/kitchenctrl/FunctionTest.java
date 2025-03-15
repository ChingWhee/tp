package kitchenctrl;

import org.junit.jupiter.api.Test;
import backend.function.Functions;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import backend.storage.Ingredient;

import java.util.ArrayList;

public class FunctionTest {
    @Test
    public void insufficientIngredients() {
        ArrayList<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Flour", 500));
        recipe.add(new Ingredient("Eggs", 2));
        recipe.add(new Ingredient("Milk", 300));

        ArrayList<Ingredient> inventory = new ArrayList<>();
        inventory.add(new Ingredient("Flour", 1000));
        inventory.add(new Ingredient("Eggs", 4));
        inventory.add(new Ingredient("Milk", 250)); // Not enough!

        assertFalse(Functions.canCookRecipe(recipe,inventory), "Should not have enough ingredients");
    }

    @Test
    public void sufficientIngredients() {
        ArrayList<Ingredient> recipe = new ArrayList<>();
        recipe.add(new Ingredient("Flour", 500));
        recipe.add(new Ingredient("Eggs", 2));
        recipe.add(new Ingredient("Milk", 300));

        ArrayList<Ingredient> inventory = new ArrayList<>();
        inventory.add(new Ingredient("Flour", 1000));
        inventory.add(new Ingredient("Eggs", 4));
        inventory.add(new Ingredient("Milk", 350));

        assertTrue(Functions.canCookRecipe(recipe,inventory), "There should be enough ingredients");
    }
}
