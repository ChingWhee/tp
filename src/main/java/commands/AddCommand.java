package commands;

import controller.ScreenState;
import model.Ingredient;
import model.Recipe;
import model.catalogue.*;

/**
 * A command to add an ingredient to the ingredient catalogue.
 */
public class AddCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an AddIngredientCommand with the specified ingredient name and quantity.
     *
     * @param name     The name of the ingredient to add. Must not be null or empty.
     * @param quantity The quantity of the ingredient to add. Must be greater than zero.
     * @throws AssertionError if the name is null or empty, or if the quantity is not greater than zero.
     */
    public AddCommand(ScreenState screen, String name, int quantity) {
        super(screen);
        assert name != null && !name.trim().isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";
        if (catalogue instanceof InventoryCatalogue inventory) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return inventory.addItem(newIngredient);
        } else if (catalogue instanceof ShoppingCatalogue shopping) {
            Ingredient newIngredient = new Ingredient(name, quantity);
            return shopping.addItem(newIngredient);
        } else if (catalogue instanceof RecipeCatalogue recipe) {
            Recipe newRecipe = new Recipe();
            return recipe.addItem(newRecipe);
        } else {
            return new CommandResult("Unsupported catalogue for AddCommand.", null);
        }
    }
}
