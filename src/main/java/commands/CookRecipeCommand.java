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
 * This command checks whether all required ingredients for a specified {@link Recipe}
 * are available in sufficient quantity in the {@link Inventory}. If so, it deducts the
 * necessary quantities from the inventory. Otherwise, it lists the missing ingredients.g
 */
public class CookRecipeCommand extends Command {

    private final Recipe targetRecipe;

    /**
     * Constructs a {@code CookRecipeCommand} with the specified recipe to cook.
     *
     * @param targetRecipe The recipe to be cooked.
     * @throws AssertionError if {@code targetRecipe} is {@code null}.
     */
    public CookRecipeCommand(Recipe targetRecipe) {
        assert targetRecipe != null : "Recipe to cook must not be null";
        this.targetRecipe = targetRecipe;
    }

    /**
     * Identifies any missing or insufficient ingredients needed to cook the recipe.
     *
     * If an ingredient is missing entirely from the inventory or its available quantity
     * is less than required, it is included in the returned list. For insufficient quantities,
     * only the shortage amount is included.
     *
     * @param inventory The inventory to check against.
     * @return A list of {@code Ingredient} objects representing the missing or insufficient ingredients.
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
     * Executes the command to cook the selected recipe by consuming ingredients from the inventory.
     *
     * This method verifies whether the command is invoked on a valid screen (either {@code RecipeBook}
     * or {@code Inventory}). It then checks for missing or insufficient ingredients and, if all are present,
     * deducts the used ingredients from the inventory.
     *
     * @param catalogue The current screen's catalogue context, expected to be {@code Inventory} or {@code RecipeBook}.
     * @return A {@code CommandResult} indicating success or failure, including any missing ingredients if applicable.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        if (!((catalogue instanceof RecipeBook) || (catalogue instanceof Inventory))) {
            return new CommandResult("Command only executable in RecipeBook or Inventory screen!");
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

        System.out.println("Cooking items...");
        // Deduct ingredients from inventory
        for (Ingredient requiredIngredient : recipeIngredients) {
            inventory.decreaseQuantity(inventory.getItemByName(requiredIngredient.getIngredientName()), requiredIngredient);
        }

        return new CommandResult("Recipe successfully cooked: " + targetRecipe.getRecipeName()
                + ". Ingredients have been deducted from inventory.");
    }
}
