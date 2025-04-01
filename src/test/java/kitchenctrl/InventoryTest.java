package kitchenctrl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import model.Ingredient;

public class InventoryTest {
    @Test
    public void testIngredient(){
        String expectedName = "Egg";
        int expectedQuantity = 2;
        Ingredient testIngredient = new Ingredient("Egg", 2);
        assertEquals(expectedName,testIngredient.getIngredientName());
        assertEquals(expectedQuantity,testIngredient.getQuantity());
    }
    @Test
    public void testNegativeQuantity() {
        Ingredient testIngredient = new Ingredient("Sugar", -3);

        assertFalse(testIngredient.getQuantity() >= 0, "Quantity should not be negative");
    }
}
