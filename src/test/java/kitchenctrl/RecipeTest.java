package kitchenctrl;

import model.Ingredient;
import model.catalogue.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commands.CommandResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeTest {
    private Recipe emptyRecipe;
    private Recipe namedRecipe;
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeEach
    public void setUp() {
        emptyRecipe = new Recipe();
        namedRecipe = new Recipe("Pancakes");

        ingredient1 = new Ingredient("Flour", 2);
        ingredient2 = new Ingredient("Eggs", 3);
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(emptyRecipe.getRecipeName());
        assertEquals("Recipe", emptyRecipe.getType());
        assertTrue(emptyRecipe.listItems().getFeedbackToUser().contains("No ingredients"));
    }

    @Test
    public void testNameOnlyConstructor() {
        assertEquals("Pancakes", namedRecipe.getRecipeName());
    }

    @Test
    public void testNameAndIngredientsConstructor() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        Recipe recipe = new Recipe("Omelette", ingredients);

        assertEquals("Omelette", recipe.getRecipeName());
        assertTrue(recipe.toString().contains("Flour"));
        assertTrue(recipe.toString().contains("Eggs"));
    }

    @Test
    public void testAddIngredientNewItem() {
        CommandResult result = namedRecipe.addItem(ingredient1, false);
        assertTrue(result.getFeedbackToUser().contains("2x Flour added to recipe"));
    }

    @Test
    public void testListItemsWithIngredients() {
        namedRecipe.addItem(ingredient1, false);
        namedRecipe.addItem(ingredient2, false);

        String result = namedRecipe.listItems().getFeedbackToUser();
        assertTrue(result.contains("1. 2x Flour"));
        assertTrue(result.contains("2. 3x Eggs"));
    }

    @Test
    public void testToString() {
        namedRecipe.addItem(ingredient1, false);
        String result = namedRecipe.toString();
        assertTrue(result.startsWith("Pancakes"));
        assertTrue(result.contains("Flour"));
    }

    @Test
    public void testSearchSimilarIngredient() {
        namedRecipe.addItem(ingredient1, false);
        Ingredient search = new Ingredient("flour", 1);
        ArrayList<Ingredient> results = namedRecipe.searchSimilarIngredient(search);

        assertEquals(1, results.size());
        assertEquals("Flour", results.get(0).getIngredientName());
    }

    @Test
    public void testGetItemByNameCaseInsensitive    () {
        namedRecipe.addItem(ingredient1, false);
        Ingredient result = namedRecipe.getItemByName("flOuR");

        assertNotNull(result);
        assertEquals("Flour", result.getIngredientName());
    }
}
