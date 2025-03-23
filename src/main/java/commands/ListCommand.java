package commands;

import controller.ScreenState;
import model.catalogue.*;

/**
 * Represents a command to list all the ingredients in the inventory.
 * The command will retrieve and display the list of items from the
 * {@link IngredientCatalogue}.
 */
public class ListCommand extends Command {
    public ListCommand(ScreenState screen) {
        super(screen);
    }

    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof InventoryCatalogue inventory) {
            return inventory.listItems();
        } else if (catalogue instanceof ShoppingCatalogue shopping) {
            return shopping.listItems();
        } else if (catalogue instanceof RecipeCatalogue recipe) {
            return recipe.listItems();
        } else {
            return new CommandResult("Unsupported catalogue for AddCommand.", null);
        }
    }
}
