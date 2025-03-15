package commands;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;

public class AddIngredientCommand extends Command {
    private final String name;
    private final int quantity;

    public AddIngredientCommand(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(IngredientCatalogue inventory) {
        Ingredient toAdd = new Ingredient(name, quantity);
        inventory.addItem(toAdd);
        return new CommandResult("Ingredient added");
    }
}
