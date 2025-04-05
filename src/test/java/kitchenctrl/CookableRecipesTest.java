package kitchenctrl;

import commands.CookableRecipesCommand;
import model.Ingredient;
import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CookableRecipesTest {

    private Inventory inventory;
    private RecipeBook testBook;
    private CookableRecipesCommand cookableRecipesCommand;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        testBook = new RecipeBook();
        cookableRecipesCommand = new CookableRecipesCommand();
    }

    @Test
    void testAllRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 2), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);
        inventory.addItem(new Ingredient("Egg", 3), false);

        Recipe recipe1 = new Recipe("Cake");
        recipe1.addItem(new Ingredient("Flour", 2), false);
        recipe1.addItem(new Ingredient("Sugar", 1), false);

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(recipe1, false);

        ArrayList<Recipe> cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertEquals(1, cookableRecipes.size());
        assertTrue(cookableRecipes.contains(recipe1));
    }

    @Test
    void testNoRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 1), false);

        Recipe recipe1 = new Recipe("Cake");
        recipe1.addItem(new Ingredient("Flour", 2), false); // Requires 2, only 1 in inventory

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(recipe1, false);

        ArrayList<Recipe> cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.isEmpty());
    }

    @Test
    void testOnlySomeRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 2), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false);
        cake.addItem(new Ingredient("Sugar", 1), false);

        Recipe omelette = new Recipe("Omelette");
        omelette.addItem(new Ingredient("Egg", 2), false);
        omelette.addItem(new Ingredient("Milk", 1), false); // Missing in inventory

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(cake, false);
        testBook.addItem(omelette, false);

        ArrayList<Recipe> cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertEquals(1, cookableRecipes.size());
        assertTrue(cookableRecipes.contains(cake));
    }

    @Test
    void testRecipesWithInsufficientIngredientsCannotBeCooked() {
        inventory.addItem(new Ingredient("Flour", 1), false); // Not enough flour
        inventory.addItem(new Ingredient("Sugar", 1), false);

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2), false); // Needs 2, only 1 available
        cake.addItem(new Ingredient("Sugar", 1), false);

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(cake, false);

        ArrayList<Recipe> cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.isEmpty());
    }

    @Test
    void testEmptyRecipeBookReturnsNoRecipes() {
        inventory.addItem(new Ingredient("Flour", 2), false);
        inventory.addItem(new Ingredient("Sugar", 1), false);
        RecipeBook testBook = new RecipeBook();

        ArrayList<Recipe> cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.isEmpty());
    }
}
