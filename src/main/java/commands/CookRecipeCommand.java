package commands;

import controller.ScreenState;
import model.Ingredient;
import model.Recipe;
import model.catalogue.IngredientCatalogue;
import model.catalogue.Catalogue;

import java.util.ArrayList;

/**
 * Represents a command to cook a recipe by consuming ingredients from the inventory.
 * <p>
 * This command checks if all required ingredients are available in sufficient quantity
 * and deducts them from the {@link IngredientCatalogue}. If ingredients are missing,
 * the recipe cannot be cooked.
 * </p>
 */
public class CookRecipeCommand extends Command {

    private final Recipe targetRecipe;

    /**
     * Constructs a {@code CookRecipeCommand} with the specified recipe.
     *
     * @param screen       The current screen context where the command is issued.
     * @param targetRecipe The recipe to be cooked.
     * @throws AssertionError if the recipeToCook is null.
     */
    public CookRecipeCommand(ScreenState screen, Recipe targetRecipe) {
        super(screen);
        assert targetRecipe != null : "Recipe to cook must not be null";
        this.targetRecipe = targetRecipe;
    }

    /**
     * Retrieves the missing ingredients needed to cook the recipe.
     *
     * @param inventory The inventory catalogue to check available ingredients.
     * @return A list of missing ingredients required to cook the recipe.
     */
    public ArrayList<Ingredient> getMissingIngredients(IngredientCatalogue inventory) {
        ArrayList<Ingredient> missingIngredients = new ArrayList<>();
        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> recipeIngredients = targetRecipe.getItems();

        for (Ingredient requiredIngredient : recipeIngredients) {
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

    /**
     * Attempts to cook the recipe by deducting ingredient quantities from the inventory.
     *
     * @param catalogue The inventory catalogue to update.
     * @return A {@code CommandResult} indicating success or listing missing ingredients.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {

        IngredientCatalogue inventory = (IngredientCatalogue) catalogue;

        ArrayList<Ingredient> missingIngredients = getMissingIngredients(inventory);

        if (!missingIngredients.isEmpty()) {
            return new CommandResult("Missing ingredients: " + missingIngredients);
        }

        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> recipeIngredients = targetRecipe.getItems();

        for (Ingredient requiredIngredient : recipeIngredients) {
            int index = inventoryItems.indexOf(requiredIngredient);
            Ingredient ingredientInInventory = inventoryItems.get(index);
            ingredientInInventory.subtractQuantity(requiredIngredient.getQuantity());
        }

        return new CommandResult("Recipe successfully cooked: " + targetRecipe.getRecipeName());
    }
}
