package kitchenctrl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import backend.storage.Ingredient;

public class InventoryTest {
    @Test
    public void testIngredient(){
        String expectedName = "Egg";
        int expectedQuantity = 2;
        Ingredient testIngredient = new Ingredient("Egg", 2);
        assertEquals(expectedName,testIngredient.getIngredientName());
        assertEquals(expectedQuantity,testIngredient.getQuantity());
    }
}

