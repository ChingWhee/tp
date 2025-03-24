package kitchenctrl;

import model.catalogue.IngredientCatalogue;
import model.catalogue.RecipeCatalogue;
import model.Ingredient;
import model.Recipe;

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
