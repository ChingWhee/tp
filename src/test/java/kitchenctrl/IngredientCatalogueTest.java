package kitchenctrl;

import commands.CommandResult;
import model.Ingredient;
import model.catalogue.IngredientCatalogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testAddValidIngredientNew() {
        CommandResult result = catalogue.addItem(flour, false);
        assertEquals("2x Flour added to test.", result.getFeedbackToUser());
    }

    @Test
    public void testAddInvalidIngredientNull() {
        CommandResult result = catalogue.addItem(null, false);
        assertTrue(result.getFeedbackToUser().contains("Ingredient is null"));
    }

    @Test
    public void testAddInvalidIngredientEmptyName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("   ", 1); // Invalid: empty name
        });
        assertEquals("Ingredient name cannot be null or empty.", e.getMessage());
    }

    @Test
    public void testAddInvalidIngredientNegativeQty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Sugar", -5); // Invalid: quantity < 1
        });
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void testSearchSimilarIngredientMatch() {
        catalogue.addItem(flour, false);
        ArrayList<Ingredient> result = catalogue.searchSimilarIngredient(flourCopy);
        assertEquals(1, result.size());
        assertEquals("Flour", result.get(0).getIngredientName());
    }

    @Test
    public void testGetItemByNameCaseInsensitive() {
        catalogue.addItem(flour, false);
        Ingredient result = catalogue.getItemByName("fLoUr");
        assertNotNull(result);
        assertEquals("Flour", result.getIngredientName());
    }

    @Test
    public void testDeleteIngredientNotFound() {
        Ingredient notExist = new Ingredient("Honey", 1);
        CommandResult result = catalogue.deleteItem(notExist);
        assertTrue(result.getFeedbackToUser().contains("does not exist"));
    }

    @Test
    public void testRemoveAllIngredients() {
        catalogue.addItem(new Ingredient("Flour", 1), false);
        catalogue.addItem(new Ingredient("Eggs", 2), false);

        CommandResult result = catalogue.removeAllIngredients();

        assertEquals("All ingredients removed from test.", result.getFeedbackToUser());
        assertNull(catalogue.getItemByName("Flour"));
        assertNull(catalogue.getItemByName("Eggs"));
    }


    @Test
    public void testEditIngredientInvalidName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("", 1); // Invalid name
        });
        assertEquals("Ingredient name cannot be null or empty.", e.getMessage());
    }

    @Test
    public void testEditIngredientQuantityNegative() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Flour", -2); // Invalid quantity
        });
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void testEditIngredientItemNotFound() {
        CommandResult result = catalogue.editItem(new Ingredient("Vanilla", 5));
        assertTrue(result.getFeedbackToUser().contains("does not exist"));
    }

    @Test
    public void testFindItemKeywordSearch() {
        catalogue.addItem(flour, false);
        catalogue.addItem(eggs, false);
        CommandResult result = catalogue.findItem("egg");
        assertTrue(result.getFeedbackToUser().toLowerCase().contains("eggs"));
    }
}
