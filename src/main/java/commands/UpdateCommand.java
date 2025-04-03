package commands;

import static controller.KitchenCTRL.requireActiveRecipe;

import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;

/**
 * Represents a command to update the quantity of an ingredient in a recipe.
 * <p>
 * This command is only valid when the current screen is in the RECIPE state.
 * It updates an ingredient in the currently active recipe catalogue by name.
 * If the ingredient does not exist, the behavior depends on the {@code Recipe}'s
 * implementation of {@code updateItem}.
 */
public class UpdateCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an {@code UpdateCommand} with the specified ingredient name and new quantity.
     *
     * @param name        the name of the ingredient to update
     * @param newQuantity the new quantity to assign to the ingredient
     */
    public UpdateCommand(String name, int newQuantity) {
        this.name = name;
        this.quantity = newQuantity;
    }

    /**
     * Executes the update operation on the given catalogue.
     * <p>
     * This method supports only the RECIPE screen state. If the catalogue is a {@code Recipe},
     * it attempts to update the quantity of the specified ingredient. Otherwise, it returns
     * an error message.
     *
     * @param catalogue the catalogue to operate on, expected to be a {@code Recipe}
     * @return a {@code CommandResult} indicating the outcome of the operation
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue instanceof Recipe : "Catalogue must be a Recipe";
        // Create the new ingredient with the specified name and quantity
        Ingredient newIngredient = new Ingredient(name, quantity);
        Recipe recipe = (Recipe) catalogue;
        // Ensure there is an active recipe
        requireActiveRecipe();

        if (recipe.getItems().contains(newIngredient)) {
            // Update the item in the recipe
            recipe.updateItem(newIngredient);

            return new CommandResult(newIngredient.getIngredientName()
                    + " quantity updated in " + recipe.getRecipeName() + ".");
        }

        return new CommandResult("No such ingredient found.");
    }
}
