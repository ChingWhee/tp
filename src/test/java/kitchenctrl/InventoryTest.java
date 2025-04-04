package kitchenctrl;
import org.junit.jupiter.api.Test;

import model.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryTest {

    @Test
    public void testIngredient() {
        String expectedName = "Egg";
        int expectedQuantity = 2;
        Ingredient testIngredient = new Ingredient("Egg", 2);
        assertEquals(expectedName, testIngredient.getIngredientName());
        assertEquals(expectedQuantity, testIngredient.getQuantity());
    }

    @Test
    public void testNegativeQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("Sugar", -3);
        });

        assertEquals("Quantity must be a positive integer.", exception.getMessage());
    }
}
