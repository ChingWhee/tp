package commands;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;

public class AddIngredientCommand extends Command {
    private final String name;
    private final int quantity;

    public AddIngredientCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(IngredientCatalogue inventory) {
        assert inventory != null : "IngredientCatalogue must not be null";

        Ingredient toAdd = new Ingredient(name, quantity);
        inventory.addItem(toAdd);
        return new CommandResult("Ingredient added");
    }
}
