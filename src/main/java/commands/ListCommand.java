package commands;

import controller.ScreenState;
import model.catalogue.IngredientCatalogue;

/**
 * Represents a command to list all the ingredients in the inventory.
 * The command will retrieve and display the list of items from the
 * {@link IngredientCatalogue}.
 */
public class ListCommand extends Command {
    public ListCommand(ScreenState screen) {
        super(screen);
    }

    /**
     * Executes the list command by retrieving all ingredients from the
     * {@code IngredientCatalogue} and returning the result.
     *
     * @param inventory the {@link IngredientCatalogue} containing the ingredients
     * @return a {@link CommandResult} containing the list of ingredients
     * @throws IllegalArgumentException if the inventory is null
     */
    @Override
    public CommandResult execute(IngredientCatalogue inventory) {
        assert inventory != null : "IngredientCatalogue must not be null";
        return inventory.listItems();
    }
}
