package commands;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;

/**
 * A command to add an ingredient to the ingredient catalogue.
 */
public class AddIngredientCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an AddIngredientCommand with the specified ingredient name and quantity.
     *
     * @param name     The name of the ingredient to add. Must not be null or empty.
     * @param quantity The quantity of the ingredient to add. Must be greater than zero.
     * @throws AssertionError if the name is null or empty, or if the quantity is not greater than zero.
     */
    public AddIngredientCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the command to add the specified ingredient to the ingredient catalogue.
     *
     * @param inventory The ingredient catalogue to which the ingredient will be added. Must not be null.
     * @return The result of executing the command.
     * @throws AssertionError if the inventory is null.
     */
    @Override
    public CommandResult execute(IngredientCatalogue inventory) {
        assert inventory != null : "IngredientCatalogue must not be null";

        Ingredient toAdd = new Ingredient(name, quantity);
        return inventory.addItem(toAdd);
    }
}
