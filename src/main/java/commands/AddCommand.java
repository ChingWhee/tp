package commands;

import controller.ScreenState;
import model.Ingredient;
import model.Recipe;
import model.catalogue.Catalogue;
import model.catalogue.InventoryCatalogue;
import model.catalogue.RecipeBook;
import model.catalogue.ShoppingCatalogue;

/**
 * Represents a command to add an item (ingredient or recipe) to the appropriate catalogue
 * based on the current {@code ScreenState}.
 *
 * <p>This command supports:
 * <ul>
 *     <li>Adding {@code Ingredient} to {@code InventoryCatalogue}</li>
 *     <li>Adding {@code Ingredient} to {@code ShoppingCatalogue}</li>
 *     <li>Adding a new {@code Recipe} to {@code RecipeCatalogue}</li>
 * </ul>
 */
public class AddCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an {@code AddCommand} with the specified screen context, item name, and quantity.
     *
     * @param screen   The screen state representing the active catalogue (INVENTORY, SHOPPING, or RECIPE).
     * @param name     The name of the ingredient or recipe to add.
     * @param quantity The quantity of the ingredient to add. Ignored for recipes.
     * @throws AssertionError if {@code name} is null/empty, or {@code quantity} is not positive.
     */
    public AddCommand(ScreenState screen, String name, int quantity) {
        super(screen);
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the add operation by determining the correct catalogue type based on runtime type checking.
     *
     * @param catalogue The catalogue to add the item to.
     * @return A {@code CommandResult} indicating success or failure with feedback for the user.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof InventoryCatalogue inventory) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return inventory.addItem(newIngredient);
        } else if (catalogue instanceof ShoppingCatalogue shopping) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return shopping.addItem(newIngredient);
        } else if (catalogue instanceof RecipeBook recipe) {
            Recipe newRecipe = new Recipe();
            return recipe.addItem(newRecipe);
        } else {
            return new CommandResult("Unsupported catalogue for AddCommand.", null);
        }
    }
}
