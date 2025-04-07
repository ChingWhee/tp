package kitchenctrl;

import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import commands.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeBookTest {
    private RecipeBook recipeBook;
    private Recipe recipe1;

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe("Pasta");
    }

    @Test
    void addItem_newRecipe() {
        CommandResult result = recipeBook.addItem(recipe1, false);
        assertNotNull(result);
        assertEquals("Pasta added to recipe book.", result.getFeedbackToUser());
        assertTrue(recipeBook.getItems().contains(recipe1));
    }

    @Test
    void deleteItem_existingRecipe() {
        recipeBook.addItem(recipe1, false);
        CommandResult result = recipeBook.deleteItem(recipe1);
        assertNotNull(result);
        assertEquals("Pasta removed from recipe book.", result.getFeedbackToUser());
        assertFalse(recipeBook.getItems().contains(recipe1));
    }

    @Test
    void deleteItem_nonExistentRecipe() {
        CommandResult result = recipeBook.deleteItem(recipe1);
        assertNotNull(result);
        assertEquals("Pasta does not exist in the recipe book.", result.getFeedbackToUser());
    }

    @Test
    void editItem_existingRecipe() {
        recipeBook.addItem(recipe1, false);
        Recipe updatedRecipe = new Recipe("Spaghetti");
        CommandResult result = recipeBook.editItem(recipe1, updatedRecipe);
        assertNotNull(result);
        assertEquals("Pasta updated to Spaghetti", result.getFeedbackToUser());
        assertFalse(recipeBook.getItems().contains(recipe1));
        assertTrue(recipeBook.getItems().contains(updatedRecipe));
    }

    @Test
    void editItem_nonExistentRecipe() {
        Recipe updatedRecipe = new Recipe("Spaghetti");
        CommandResult result = recipeBook.editItem(recipe1, updatedRecipe);
        assertNotNull(result);
        assertEquals("Recipe not found.", result.getFeedbackToUser());
    }

    @Test
    void listItems_emptyBook() {
        CommandResult result = recipeBook.listItems();
        assertNotNull(result);
        assertEquals("No recipes found.", result.getFeedbackToUser());
    }

    @Test
    void listItems_withRecipes() {
        recipeBook.addItem(recipe1, false);
        recipeBook.addItem(new Recipe("Pizza"), false);
        CommandResult result = recipeBook.listItems();
        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("1. Pasta"));
        assertTrue(result.getFeedbackToUser().contains("2. Pizza"));
    }
    @Test
    void findItem_singleMatch() {
        recipeBook.addItem(new Recipe("Tomato Soup"), false);
        recipeBook.addItem(new Recipe("Pasta Carbonara"), false);
        CommandResult result = recipeBook.findItem("Carbonara");
        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("Pasta Carbonara"));
    }

    @Test
    void findItem_multipleMatches() {
        recipeBook.addItem(new Recipe("Pasta Carbonara"), true);
        recipeBook.addItem(new Recipe("Pasta Bolognese"), true);
        CommandResult result = recipeBook.findItem("Pasta");
        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("Pasta Carbonara"));
        assertTrue(result.getFeedbackToUser().contains("Pasta Bolognese"));
    }

    @Test
    void findItem_noMatch() {
        recipeBook.addItem(new Recipe("Pizza"), false);
        CommandResult result = recipeBook.findItem("Burger");
        assertNotNull(result);
        assertEquals("No recipes found containing: Burger", result.getFeedbackToUser());
    }

    @Test
    void findItem_emptyQuery() {
        recipeBook.addItem(new Recipe("Burger"), false);
        CommandResult result = recipeBook.findItem("");
        assertNotNull(result);
        assertEquals("Please provide a keyword to search.", result.getFeedbackToUser());
    }

    @Test
    void addItem_duplicateRecipe_silentMode() {
        recipeBook.addItem(new Recipe("Pasta"), true);
        CommandResult result = recipeBook.addItem(new Recipe("Pasta"), true);
        assertEquals("Recipe with name \"Pasta\" already exists.", result.getFeedbackToUser());
    }

    @Test
    void addItem_nullName() {
        Recipe invalidRecipe = new Recipe(null);
        CommandResult result = recipeBook.addItem(invalidRecipe, true);
        assertEquals("Invalid recipe: name must be non-empty.", result.getFeedbackToUser());
    }

    @Test
    void addItem_nullRecipe() {
        CommandResult result = recipeBook.addItem(null, true);
        assertEquals("Invalid recipe: Recipe is null.", result.getFeedbackToUser());
    }

    @Test
    void getItemByName_withWhitespace() {
        recipeBook.addItem(new Recipe("Fried Rice"), false);
        Recipe result = recipeBook.getItemByName("  Fried Rice  ");
        assertNotNull(result);
        assertEquals("Fried Rice", result.getRecipeName());
    }

    @Test
    void getCatalogueContent_returnsFormattedString() {
        recipeBook.addItem(new Recipe("Noodles"), false);
        recipeBook.addItem(new Recipe("Fried Rice"), false);
        String content = recipeBook.getCatalogueContent();
        assertTrue(content.contains("Noodles"));
        assertTrue(content.contains("Fried Rice"));
    }

    @Test
    void getType_returnsCorrectType() {
        assertEquals("RecipeBook", recipeBook.getType());
    }

    @Test
    void searchSimilarRecipe_partialMatch() {
        recipeBook.addItem(new Recipe("Choco Cake"), false);
        Recipe target = new Recipe("Chocolate");
        assertFalse(recipeBook.searchSimilarRecipe(target).isEmpty());
    }
}
