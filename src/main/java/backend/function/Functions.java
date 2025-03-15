package backend.function;

import backend.storage.Recipe;
import backend.storage.catalogue.InventoryCatalogue;
import backend.storage.Ingredient;
import java.util.ArrayList;

public class Functions {
    public static boolean canCookRecipe(ArrayList<Ingredient> recipe, ArrayList<Ingredient> inventory) {
        for (Ingredient required : recipe) {
            if (!inventory.contains(required)) {
                return false; // Ingredient not found
            }
            // Get the actual ingredient from inventory to compare quantity
            int index = inventory.indexOf(required);
            if (inventory.get(index).getQuantity() < required.getQuantity()) {
                return false; // Insufficient quantity
            }
        }
        return true; // All ingredients are sufficient
    }
}
