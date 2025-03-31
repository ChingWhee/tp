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
        CommandResult result = recipeBook.addItem(recipe1);
        assertNotNull(result);
        assertEquals("Pasta added to recipe book.", result.getFeedbackToUser());
        assertTrue(recipeBook.getItems().contains(recipe1));
    }

    @Test
    void deleteItem_existingRecipe() {
        recipeBook.addItem(recipe1);
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
        recipeBook.addItem(recipe1);
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
        recipeBook.addItem(recipe1);
        recipeBook.addItem(new Recipe("Pizza"));
        CommandResult result = recipeBook.listItems();
        assertNotNull(result);
        assertTrue(result.getFeedbackToUser().contains("1. Pasta"));
        assertTrue(result.getFeedbackToUser().contains("2. Pizza"));
    }
}
