package commands;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;

/**
 * Represents a command to delete an ingredient from the inventory.
 * The command will attempt to remove a specified quantity of the ingredient
 * from the {@link IngredientCatalogue}.
 */
public class DeleteIngredientCommand extends Command {

    private final String name;
    private final int quantity;

    /**
     * Constructs a {@code DeleteIngredientCommand} with the specified ingredient name and quantity.
     *
     * @param name the name of the ingredient to be deleted
     * @param quantity the quantity of the ingredient to be deleted
     * @throws IllegalArgumentException if the name is null, empty, or if the quantity is less than or equal to zero
     */
    public DeleteIngredientCommand(String name, int quantity) {
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
    public CommandResult execute(IngredientCatalogue inventory) {
        assert inventory != null : "IngredientCatalogue must not be null";

        Ingredient toDelete = new Ingredient(name, quantity);
        return inventory.deleteItem(toDelete);
    }
}
