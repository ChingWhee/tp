package commands;

import model.Ingredient;
import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        inventory.addItem(new Ingredient("Flour", 2));
        inventory.addItem(new Ingredient("Sugar", 1));
        inventory.addItem(new Ingredient("Egg", 3));

        Recipe recipe1 = new Recipe("Cake");
        recipe1.addItem(new Ingredient("Flour", 2));
        recipe1.addItem(new Ingredient("Sugar", 1));

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(recipe1);

        RecipeBook cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertEquals(1, cookableRecipes.getItems().size());
        assertTrue(cookableRecipes.getItems().contains(recipe1));
    }

    @Test
    void testNoRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 1));

        Recipe recipe1 = new Recipe("Cake");
        recipe1.addItem(new Ingredient("Flour", 2)); // Requires 2, only 1 in inventory

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(recipe1);

        RecipeBook cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.getItems().isEmpty());
    }

    @Test
    void testOnlySomeRecipesCanBeCooked() {
        inventory.addItem(new Ingredient("Flour", 2));
        inventory.addItem(new Ingredient("Sugar", 1));

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2));
        cake.addItem(new Ingredient("Sugar", 1));

        Recipe omelette = new Recipe("Omelette");
        omelette.addItem(new Ingredient("Egg", 2));
        omelette.addItem(new Ingredient("Milk", 1)); // Missing in inventory

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(cake);
        testBook.addItem(omelette);

        RecipeBook cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertEquals(1, cookableRecipes.getItems().size());
        assertTrue(cookableRecipes.getItems().contains(cake));
    }

    @Test
    void testRecipesWithInsufficientIngredientsCannotBeCooked() {
        inventory.addItem(new Ingredient("Flour", 1)); // Not enough flour
        inventory.addItem(new Ingredient("Sugar", 1));

        Recipe cake = new Recipe("Cake");
        cake.addItem(new Ingredient("Flour", 2)); // Needs 2, only 1 available
        cake.addItem(new Ingredient("Sugar", 1));

        RecipeBook testBook = new RecipeBook();
        testBook.addItem(cake);

        RecipeBook cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.getItems().isEmpty());
    }

    @Test
    void testEmptyRecipeBookReturnsNoRecipes() {
        inventory.addItem(new Ingredient("Flour", 2));
        inventory.addItem(new Ingredient("Sugar", 1));
        RecipeBook testBook = new RecipeBook();

        RecipeBook cookableRecipes = cookableRecipesCommand.getCookableRecipes(testBook, inventory);
        assertTrue(cookableRecipes.getItems().isEmpty());
    }
}
