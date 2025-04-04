package commands;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Recipe;
import model.catalogue.Catalogue;
import model.catalogue.Inventory;
import model.catalogue.RecipeBook;

import static controller.KitchenCTRL.requireActiveRecipe;
import static controller.ScreenState.RECIPEBOOK;

/**
 * Represents a command to add an item (ingredient or recipe) to the appropriate catalogue
 * based on the current {@code ScreenState}.
 *
 * <p>This command supports:
 * <ul>
 *     <li>Adding {@code Ingredient} to {@code Inventory}</li>
 *     <li>Adding a new {@code Recipe} to {@code RecipeBook}</li>
 *     <li>Adding {@code Ingredient} to a selected {@code Recipe}</li>
 * </ul>
 */
public class AddCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs an {@code AddCommand} with the specified screen context, item name, and quantity.
     *
     * @param name     The name of the ingredient or recipe to add.
     * @param quantity The quantity of the ingredient to add. Ignored for recipes.
     * @throws AssertionError if {@code name} is null/empty, or {@code quantity} is not positive (when required).
     */

    public AddCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Name must not be null or empty";

        assert screen == RECIPEBOOK || quantity > 0 : "Quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Constructs an {@code AddCommand} with the specified item name.
     * <p>
     * This constructor is used when adding a recipe, where the quantity is irrelevant.
     * The quantity is set to 0 by default.
     *
     * @param name The name of the recipe to add.
     * @throws AssertionError if {@code name} is null or empty.
     */
    public AddCommand(String name) {
        assert name != null && !name.trim().isEmpty() : "Name must not be null or empty";

        this.name = name;
        this.quantity = 0; // irrelevant for recipebook screen
    }

    /**
     * Executes the add operation based on the screen context.
     *
     * @param catalogue The catalogue to add the item to.
     * @return A {@code CommandResult} indicating success or failure with user feedback.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";

        try {
            return switch (KitchenCTRL.getCurrentScreen()) {
            case INVENTORY -> {
                if (catalogue instanceof Inventory inventory) {
                    Ingredient ingredient = new Ingredient(name, quantity);
                    yield inventory.addItem(ingredient);
                }
                yield new CommandResult("Invalid catalogue for inventory operation.", null);
            }
            case RECIPEBOOK -> {
                if (catalogue instanceof RecipeBook recipeBook) {
                    Recipe recipe = new Recipe(name);
                    yield recipeBook.addItem(recipe);
                }
                yield new CommandResult("Invalid catalogue for recipe book operation.", null);
            }
            case RECIPE -> {
                requireActiveRecipe();
                if (catalogue instanceof Recipe recipe) {
                    Ingredient ingredient = new Ingredient(name, quantity);
                    yield recipe.addItem(ingredient);
                }
                yield new CommandResult("Invalid catalogue for recipe operation.", null);
            }
            default -> new CommandResult("Unsupported screen state for AddCommand.", null);
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
