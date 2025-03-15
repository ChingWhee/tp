package kitchenctrl;

import backend.storage.catalogue.IngredientCatalogue;
import backend.storage.catalogue.RecipeCatalogue;
import backend.storage.Ingredient;
import backend.storage.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class CatalogueTest {
    @Test
    public void testCatalogueInitialization() {
        IngredientCatalogue testInventoryCatalogue = new IngredientCatalogue() {};
        RecipeCatalogue testRecipeCatalogue = new RecipeCatalogue() {};
        ArrayList<Ingredient> expectedIngredients = new ArrayList<>();
        ArrayList<Recipe> expectedRecipes = new ArrayList<>();

        assertEquals(expectedIngredients, testInventoryCatalogue.getItems());
        assertEquals(expectedRecipes, testRecipeCatalogue.getItems());
    }
}
