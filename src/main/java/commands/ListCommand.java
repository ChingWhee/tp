package commands;

import controller.ScreenState;
import model.catalogue.Catalogue;
import model.catalogue.InventoryCatalogue;
import model.catalogue.RecipeCatalogue;
import model.catalogue.ShoppingCatalogue;

/**
 * Represents a command to list all items in the current catalogue.
 * <p>
 * Depending on the current screen, this command will list:
 * <ul>
 *     <li>Ingredients in the {@link InventoryCatalogue}</li>
 *     <li>Ingredients in the {@link ShoppingCatalogue}</li>
 *     <li>Recipes in the {@link RecipeCatalogue}</li>
 * </ul>
 */
public class ListCommand extends Command {
    /**
     * Constructs a {@code ListCommand} with the specified screen context.
     *
     * @param screen The current screen state where this command is invoked.
     */
    public ListCommand(ScreenState screen) {
        super(screen);
    }

    /**
     * Executes the list command by retrieving the list of items from the provided catalogue.
     * <p>
     * The method determines the catalogue type via runtime type checking and calls the appropriate
     * method to list its items.
     *
     * @param catalogue The catalogue from which items will be listed.
     * @return A {@code CommandResult} containing the formatted list of items,
     * or an error message if the catalogue type is unsupported.
     * @throws AssertionError if the catalogue is {@code null}.
     */
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
