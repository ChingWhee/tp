package kitchenctrl;

import model.Ingredient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    public void testIngredientCreation() {
        Ingredient ingredient = new Ingredient("Sugar", 5);
        assertNotNull(ingredient);
        assertEquals("Sugar", ingredient.getIngredientName());
        assertEquals(5, ingredient.getQuantity());
    }

    @Test
    public void testIngredientToString() {
        Ingredient ingredient = new Ingredient("Flour", 10);
        assertEquals("Flour (10)", ingredient.toString());
    }

    @Test
    public void testIngredientEquality() {
        Ingredient ingredient1 = new Ingredient("Salt", 5);
        Ingredient ingredient2 = new Ingredient("salt", 3);
        Ingredient ingredient3 = new Ingredient("Pepper", 5);

        assertEquals(ingredient1, ingredient2); // Case-insensitive match
        assertNotEquals(ingredient1, ingredient3);
    }

    @Test
    public void testAddQuantity() {
        Ingredient ingredient = new Ingredient("Butter", 5);
        ingredient.addQuantity(3);
        assertEquals(8, ingredient.getQuantity());
    }

    @Test
    public void testSubtractQuantity() {
        Ingredient ingredient = new Ingredient("Milk", 10);
        ingredient.subtractQuantity(4);
        assertEquals(6, ingredient.getQuantity());
    }
}
