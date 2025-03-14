package kitchenctrl;

import backend.storage.catalogue.*;
import backend.storage.Ingredient;
import backend.storage.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class CatalogueTest {
    @Test
    public void testCatalogueInitialization() {
        InventoryCatalogue testInventoryCatalogue = new InventoryCatalogue() {};
        RecipeCatalogue testRecipeCatalogue = new RecipeCatalogue() {};
        ShoppingCatalogue testShoppingCatalogue = new ShoppingCatalogue() {};
        ArrayList<Ingredient> expectedIngredients = new ArrayList<>();
        ArrayList<Recipe> expectedRecipes = new ArrayList<>();

        assertEquals(expectedIngredients, testInventoryCatalogue.getItems());
        assertEquals(expectedIngredients, testShoppingCatalogue.getItems());
        assertEquals(expectedRecipes, testRecipeCatalogue.getItems());
    }
}
