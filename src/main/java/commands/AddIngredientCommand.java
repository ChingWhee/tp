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
        Ingredient ToAdd = new Ingredient(name, quantity);
        inventory.addItem(ToAdd);
        return new CommandResult("Ingredient added");
    }
}