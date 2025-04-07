package commands;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;
import model.catalogue.Inventory;
import model.catalogue.RecipeBook;

import java.util.ArrayList;

/**
 * Represents a command to cook a recipe by consuming ingredients from the inventory.
 *
 * This command checks if all required ingredients are available in sufficient quantity
 * and deducts them from the {@link Inventory}. If ingredients are missing,
 * the recipe cannot be cooked.
 */
public class CookRecipeCommand extends Command {

    private final Recipe targetRecipe;

    /**
     * Constructs a {@code CookRecipeCommand} with the specified recipe.
     *
     * @param targetRecipe The recipe to be cooked.
     * @throws AssertionError if the recipeToCook is null.
     */
    public CookRecipeCommand(Recipe targetRecipe) {

        assert targetRecipe != null : "Recipe to cook must not be null";
        this.targetRecipe = targetRecipe;
    }

    /**
     * Retrieves the missing ingredients needed to cook the recipe.
     *
     * @param inventory The inventory catalogue to check available ingredients.
     * @return A list of missing ingredients required to cook the recipe.
     */
    public ArrayList<Ingredient> getMissingIngredients(Inventory inventory) {
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
     * This method first checks if all required ingredients are available in the inventory.
     * If any ingredient is missing, it returns a {@code CommandResult} listing the missing ingredients.
     * Otherwise, it deducts the necessary quantities from the inventory and confirms the recipe has been cooked.
     *
     * @return A {@code CommandResult} indicating success gor failure with a list of missing ingredients.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        if (!(catalogue instanceof RecipeBook)) {
            return new CommandResult("Command only executable in RecipeBook screen!");
        }
        Inventory inventory = KitchenCTRL.getInventory();

        ArrayList<Ingredient> recipeIngredients = targetRecipe.getItems();
        if (recipeIngredients.isEmpty()) {
            return new CommandResult("Recipe does not contain any ingredients!");
        }

        ArrayList<Ingredient> missingIngredients = getMissingIngredients(inventory);

        if (!missingIngredients.isEmpty()) {
            StringBuilder message = new StringBuilder("Missing ingredients:\n");
            for (int i = 0; i < missingIngredients.size(); i++) {
                Ingredient ingredient = missingIngredients.get(i);
                message.append(i + 1)
                        .append(". ")
                        .append(ingredient.getQuantity())
                        .append("x ")
                        .append(ingredient.getIngredientName())
                        .append("\n");
            }
            return new CommandResult(message.toString().trim());
        }

        ArrayList<Ingredient> inventoryItems = inventory.getItems();

        System.out.println("Cooking items...");
        //subtracts the item from inventory
        for (Ingredient requiredIngredient : recipeIngredients) {
            inventory.decreaseQuantity(inventory.getItemByName(requiredIngredient.getIngredientName()), requiredIngredient);
        }

        return new CommandResult("Recipe successfully cooked: " + targetRecipe.getRecipeName()
            + ". Ingredients have been deducted from inventory.");
    }
}
