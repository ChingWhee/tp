package commands;

import model.Ingredient;
import model.Recipe;
import model.catalogue.Catalogue;
import model.catalogue.IngredientCatalogue;
import model.catalogue.InventoryCatalogue;
import model.catalogue.RecipeCatalogue;

import java.util.ArrayList;

import static commands.getMissingIngredientsCommand.getMissingIngredients;

public class cookRecipeCommand extends Command {

    public cookRecipeCommand() {};
    public static void cookRecipe(IngredientCatalogue inventory, Recipe recipeToCook) {
        ArrayList<Ingredient> missingIngredients = getMissingIngredients(inventory, recipeToCook);
        if (!missingIngredients.isEmpty()) {
            return;
        }

        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> ingredientsToCook = recipeToCook.getItems();

        for (Ingredient requiredIngredient : ingredientsToCook) {
            int index = inventoryItems.indexOf(requiredIngredient);
            Ingredient ingredientInInventory = inventoryItems.get(index);
            ingredientInInventory.subtractQuantity(requiredIngredient.getQuantity());
        }

    }

    @Override
    /**
     * Executes the command using the provided {@code Catalogue}.
     * <p>
     * Subclasses that manipulate data (e.g., inventory or shopping items) should override this method.
     *
     * @param catalogue The catalogue relevant to the current screen.
     * @return The result of the command execution as a {@code CommandResult}.
     * @throws UnsupportedOperationException If the method is not overridden in the subclass.
     */
    public CommandResult execute(InventoryCatalogue inventoryCatalogue) {
        cookRecipe();
    }
}
