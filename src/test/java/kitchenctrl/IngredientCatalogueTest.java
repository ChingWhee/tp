package kitchenctrl;

import commands.CommandResult;
import model.Ingredient;
import model.catalogue.IngredientCatalogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientCatalogueTest {

    private IngredientCatalogue catalogue;

    // Minimal implementation for testing
    private static class TestCatalogue extends IngredientCatalogue {
        @Override
        protected String getCatalogueLabel() {
            return "test";
        }

        @Override
        public String getType() {
            return "TestCatalogue";
        }
    }

    private Ingredient flour;
    private Ingredient eggs;
    private Ingredient flourCopy;

    @BeforeEach
    public void setUp() {
        catalogue = new TestCatalogue();
        flour = new Ingredient("Flour", 2);
        eggs = new Ingredient("Eggs", 3);
        flourCopy = new Ingredient("flour", 1); // same name, different case and quantity
    }

    @Test
    public void testAddValidIngredient_New() {
        CommandResult result = catalogue.addItem(flour);
        assertEquals("2x Flour added to test.", result.getFeedbackToUser());
    }

    @Test
    public void testAddInvalidIngredient_Null() {
        CommandResult result = catalogue.addItem(null);
        assertTrue(result.getFeedbackToUser().contains("Ingredient is null"));
    }

    @Test
    public void testAddInvalidIngredient_EmptyName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("   ", 1); // Invalid: empty name
        });
        assertEquals("Ingredient name cannot be null or empty.", e.getMessage());
    }

    @Test
    public void testAddInvalidIngredient_NegativeQuantity() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Sugar", -5); // Invalid: quantity < 1
        });
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void testSearchSimilarIngredient_MatchExists() {
        catalogue.addItem(flour);
        ArrayList<Ingredient> result = catalogue.searchSimilarIngredient(flourCopy);
        assertEquals(1, result.size());
        assertEquals("Flour", result.get(0).getIngredientName());
    }

    @Test
    public void testGetItemByName_CaseInsensitive() {
        catalogue.addItem(flour);
        Ingredient result = catalogue.getItemByName("fLoUr");
        assertNotNull(result);
        assertEquals("Flour", result.getIngredientName());
    }

    @Test
    public void testDeleteIngredient_NotFound() {
        Ingredient notExist = new Ingredient("Honey", 1);
        CommandResult result = catalogue.deleteItem(notExist);
        assertTrue(result.getFeedbackToUser().contains("does not exist"));
    }

    @Test
    public void testRemoveAllIngredients() {
        catalogue.addItem(new Ingredient("Flour", 1));
        catalogue.addItem(new Ingredient("Eggs", 2));

        CommandResult result = catalogue.removeAllIngredients();

        assertEquals("All ingredients removed from test.", result.getFeedbackToUser());
        assertNull(catalogue.getItemByName("Flour"));
        assertNull(catalogue.getItemByName("Eggs"));
    }


    @Test
    public void testEditIngredient_InvalidName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("", 1); // Invalid name
        });
        assertEquals("Ingredient name cannot be null or empty.", e.getMessage());
    }

    @Test
    public void testEditIngredient_QuantityNegative() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Flour", -2); // Invalid quantity
        });
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void testEditIngredient_ItemNotFound() {
        CommandResult result = catalogue.editItem(new Ingredient("Vanilla", 5));
        assertTrue(result.getFeedbackToUser().contains("does not exist"));
    }

    @Test
    public void testFindItemKeywordSearch() {
        catalogue.addItem(flour);
        catalogue.addItem(eggs);
        CommandResult result = catalogue.findItem("egg");
        assertTrue(result.getFeedbackToUser().toLowerCase().contains("eggs"));
    }
}
