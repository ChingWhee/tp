package commands;

import controller.ScreenState;
import model.Ingredient;
import model.catalogue.Recipe;
import model.catalogue.Catalogue;
import model.catalogue.Inventory;
import model.catalogue.RecipeBook;

/**
 * Represents a command to delete an item (ingredient or recipe) from the appropriate catalogue.
 * <p>
 * Depending on the active {@link ScreenState}, the command removes:
 * <ul>
 *     <li>A specified quantity of an ingredient from the {@link Inventory}</li>
 *     <li>A recipe (by name) from the {@link RecipeBook}</li>
 * </ul>
 */
public class DeleteCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs a {@code DeleteCommand} with the specified name and quantity.
     *
     * @param name     The name of the item to be deleted (ingredient or recipe).
     * @param quantity The quantity to be removed (used only for ingredients).
     * @throws AssertionError if the name is null/empty or quantity is non-positive.
     */
    public DeleteCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the delete operation based on the type of catalogue.
     *
     * @param catalogue The target catalogue from which the item should be deleted.
     * @return A {@code CommandResult} indicating the outcome of the deletion.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof Inventory inventory) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return inventory.deleteItem(newIngredient);
        } else if (catalogue instanceof RecipeBook recipe) {
            Recipe newRecipe = new Recipe();
            return recipe.deleteItem(newRecipe);
        } else {
            return new CommandResult("Unsupported catalogue for DeleteCommand.");
        }
    }
}
