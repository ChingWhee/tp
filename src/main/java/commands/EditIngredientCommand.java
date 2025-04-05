package commands;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Inventory;
import model.catalogue.Recipe;

import static controller.KitchenCTRL.requireActiveRecipe;

/**
 * Represents a command to edit an ingredient's quantity
 * in either the Inventory or the currently active Recipe.
 */
public class EditIngredientCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an {@code EditIngredientCommand} with the specified name and quantity.
     *
     * @param name     The name of the ingredient to edit.
     * @param quantity The new quantity to set for the ingredient.
     * @throws AssertionError if name is null/empty or quantity is negative.
     */
    public EditIngredientCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Name must not be null or empty";
        assert quantity >= 0 : "Quantity must be zero or more";
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Executes the edit operation based on the screen context.
     *
     * @param catalogue The catalogue to operate on (Inventory or Recipe).
     * @return A {@code CommandResult} indicating the outcome.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";

        try {
            return switch (KitchenCTRL.getCurrentScreen()) {
            case INVENTORY -> {
                if (catalogue instanceof Inventory inventory) {
                    Ingredient ingredient = new Ingredient(name, quantity);
                    yield inventory.editItem(ingredient);
                }
                yield new CommandResult("Invalid catalogue for inventory edit.", null);
            }
            case RECIPE -> {
                requireActiveRecipe();
                if (catalogue instanceof Recipe recipe) {
                    Ingredient ingredient = new Ingredient(name, quantity);
                    yield recipe.editItem(ingredient);
                }
                yield new CommandResult("Invalid catalogue for recipe edit.", null);
            }
            default -> new CommandResult("Editing is only allowed in Inventory or Recipe screens.", null);
            };
        } catch (ClassCastException e) {
            return new CommandResult("Catalogue type mismatch: " + e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return new CommandResult("Invalid argument: " + e.getMessage(), null);
        } catch (Exception e) {
            return new CommandResult("Unexpected error occurred: " + e.getMessage(), null);
        }
    }
}
