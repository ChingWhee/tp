package commands;

import model.Ingredient;
import model.Recipe;
import model.catalogue.IngredientCatalogue;

import java.util.ArrayList;

public class getMissingIngredientsCommand {
    public static ArrayList<Ingredient> getMissingIngredients(IngredientCatalogue inventory, Recipe targetRecipe) {
        ArrayList<Ingredient> missingIngredients = new ArrayList<>();
        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> targetRecipeIngredients = targetRecipe.getItems();

        for (Ingredient requiredIngredient : targetRecipeIngredients) {
            if (!inventoryItems.contains(requiredIngredient)) {
                missingIngredients.add(requiredIngredient);
            } else {
                int index = inventoryItems.indexOf(requiredIngredient);
                Ingredient availableIngredient = inventoryItems.get(index);
                if (availableIngredient.getQuantity() < requiredIngredient.getQuantity()) {
                    int shortage = requiredIngredient.getQuantity() - availableIngredient.getQuantity();
                    missingIngredients.add(new Ingredient(requiredIngredient.getIngredientName(), shortage));
                }
            }
        }
        return missingIngredients;
    }


}
