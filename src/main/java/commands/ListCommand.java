package commands;

import model.catalogue.Catalogue;
import model.catalogue.Inventory;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;

/**
 * Represents a command to list all items in the current catalogue.
 * <p>
 * Depending on the current screen, this command will list:
 * <ul>
 *     <li>Ingredients in the {@link Inventory}</li>
 *     <li>Recipes in the {@link RecipeBook}</li>
 * </ul>
 */
public class ListCommand extends Command {
    /**
     * Constructs a {@code ListCommand} with the specified screen context.
     */
    public ListCommand() {}

    /**
     * Executes the list command by retrieving the list of items from the provided catalogue.
     * <p>
     * The method determines the catalogue type via runtime type checking and calls the appropriate
     * method to list its items.
     *
     * @param catalogue The catalogue from which items will be listed.
     * @return A {@code CommandResult} containing the formatted list of items.
     * @throws AssertionError if the catalogue is {@code null}.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof Inventory inventory) {
            return inventory.listItems();
        } else if (catalogue instanceof RecipeBook recipeBook) {
            return recipeBook.listItems();
        } else if (catalogue instanceof Recipe recipe){
            return recipe.listItems();
        } else {
            return new CommandResult("Unsupported catalogue for AddCommand.", null);
        }
    }
}
