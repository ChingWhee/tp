package kitchenctrl;

import commands.CookableRecipesCommand;
import model.Ingredient;
import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CookableRecipesTest {

    private Inventory inventory;
    private RecipeBook recipeBook;
    private CookableRecipesCommand cookableRecipesCommand;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        recipeBook = new RecipeBook();
        cookableRecipesCommand = new CookableRecipesCommand();
    }

    @Test
    void testAllRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 2), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);
        cake.addItem(new Ingredient("Sugar", 1), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertEquals(1, result.size());
        assertTrue(result.contains(cake));
    }

    @Test
    void testNoRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSomeRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 2), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);
        cake.addItem(new Ingredient("Sugar", 1), false);

        Recipe omelette = new Recipe("Omelette");
        omelette.addItem(new Ingredient("Egg", 2), false);

        recipeBook.addItem(cake, false);
        recipeBook.addItem(omelette, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertEquals(1, result.size());
        assertTrue(result.contains(cake));
    }

    @Test
    void testInsufficientIngredientQuantity() {
        inventory.addItem(new Ingredient("Flour", 1), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);
        cake.addItem(new Ingredient("Sugar", 1), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptyRecipeBook() {
        inventory.addItem(new Ingredient("Flour", 2), false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEmptyInventory() {
        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, new Inventory());
        assertTrue(result.isEmpty());
    }

    @Test
    void testCaseInsensitiveIngredientMatching() {
        inventory.addItem(new Ingredient("flour", 2), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("FLOUR", 2), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertEquals(1, result.size());
        assertTrue(result.contains(cake));
    }

    @Test
    void testIngredientZeroQuantityThrowsException() {
        inventory.addItem(new Ingredient("Flour", 2), false);

        Recipe cake = new Recipe("Cake");

        // Expect an exception when adding an ingredient with zero quantity
        assertThrows(IllegalArgumentException.class, () -> {
            cake.addItem(new Ingredient("Flour", 0), false);
        });
    }

    @Test
    void testDuplicateIngredientsInRecipe() {
        inventory.addItem(new Ingredient("Flour", 4), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);
        cake.addItem(new Ingredient("Flour", 2), false); // total 4

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertEquals(1, result.size());
        assertTrue(result.contains(cake));
    }

    @Test
    void testDuplicateIngredientsInInventory() {
        inventory.addItem(new Ingredient("Flour", 1), false);
        inventory.addItem(new Ingredient("Flour", 1), false); // Total 2, but depends on merge logic

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);

        recipeBook.addItem(cake, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertFalse(result.isEmpty());
    }

    @Test
    void testRecipeWithNoIngredientsIsCookable() {
        Recipe weird = new Recipe("Air Pie"); // no ingredients
        recipeBook.addItem(weird, false);

        ArrayList<Recipe> result = cookableRecipesCommand.getCookableRecipes(recipeBook, inventory);
        assertEquals(1, result.size());
        assertTrue(result.contains(weird));
    }
}
