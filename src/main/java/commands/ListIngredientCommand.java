package commands;

import model.Ingredient;
import model.catalogue.IngredientCatalogue;

public class ListIngredientCommand extends Command {
    @Override
    public CommandResult execute(IngredientCatalogue inventory) {
        assert inventory != null : "IngredientCatalogue must not be null";
        return inventory.listItems();
    }
}
