package commands;

import controller.ScreenState;
import model.Ingredient;
import model.Recipe;
import model.catalogue.*;

/**
 * Represents a command to delete an ingredient from the inventory.
 * The command will attempt to remove a specified quantity of the ingredient
 * from the {@link IngredientCatalogue}.
 */
public class DeleteCommand extends Command {

    private final String name;
    private final int quantity;

    /**
     * Constructs a {@code DeleteIngredientCommand} with the specified ingredient name and quantity.
     *
     * @param name the name of the ingredient to be deleted
     * @param quantity the quantity of the ingredient to be deleted
     * @throws IllegalArgumentException if the name is null, empty, or if the quantity is less than or equal to zero
     */
    public DeleteCommand(ScreenState screen, String name, int quantity) {
        super(screen);
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the delete command by attempting to remove the ingredient with the specified quantity
     * from the {@code IngredientCatalogue}.
     *
     * @param inventory the {@link IngredientCatalogue} from which the ingredient should be deleted
     * @return a {@link CommandResult} indicating the outcome of the deletion
     * @throws IllegalArgumentException if the inventory is null
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof InventoryCatalogue inventory) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return inventory.deleteItem(newIngredient);
        } else if (catalogue instanceof ShoppingCatalogue shopping) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return shopping.deleteItem(newIngredient);
        } else if (catalogue instanceof RecipeCatalogue recipe) {
            Recipe newRecipe = new Recipe();
            return recipe.deleteItem(newRecipe);
        } else {
            return new CommandResult("Unsupported catalogue for DeleteCommand.");
        }
    }
}
