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
 * Represents a command to delete an item (ingredient or recipe) from the appropriate catalogue
 * based on the current {@code ScreenState}.
 *
 * <p>This command supports:
 * <ul>
 *     <li>Deleting {@code Ingredient} from {@code Inventory}</li>
 *     <li>Deleting a {@code Recipe} from {@code RecipeBook}</li>
 *     <li>Deleting {@code Ingredient} from a specific {@code Recipe}</li>
 * </ul>
 */
public class DeleteCommand extends Command {
    private final String name;
    private final int quantity;

    /**
     * Constructs a {@code DeleteCommand} for deleting an ingredient or recipe.
     *
     * @param name     The name of the item to delete.
     * @param quantity The quantity of the ingredient to delete (ignored for recipes).
     * @throws AssertionError if {@code name} is null/empty, or {@code quantity} is non-positive where required.
     */
    public DeleteCommand(String name, int quantity) {
        assert name != null && !name.trim().isEmpty() : "Name must not be null or empty";
        assert KitchenCTRL.getCurrentScreen() == RECIPEBOOK || quantity > 0 : "Quantity must be greater than zero";

        this.name = name;
        this.quantity = quantity;
    }

    public DeleteCommand(String name) {
        assert name != null && !name.trim().isEmpty() : "Name must not be null or empty";

        this.name = name;
        this.quantity = 0; // not used for recipe deletion
    }

    /**
     * Executes the delete operation based on the screen context.
     *
     * @param catalogue The catalogue from which to delete the item.
     * @return A {@code CommandResult} describing the outcome.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";

        return switch (KitchenCTRL.getCurrentScreen()) {
        case INVENTORY -> {
            if (catalogue instanceof Inventory inventory) {
                Ingredient ingredient = new Ingredient(name, quantity);
                if (!catalogue.getItems().contains(ingredient)) {
                    yield new CommandResult("Ingredient not found in Inventory.", null);
                }
                yield inventory.deleteItem(ingredient);
            }
            yield new CommandResult("Invalid catalogue for inventory operation.", null);
        }
        case RECIPEBOOK -> {
            if (catalogue instanceof RecipeBook recipeBook) {
                Recipe recipe = new Recipe(name);
                if (!catalogue.getItems().contains(recipe)) {
                    yield new CommandResult("Recipe not found in RecipeBook.", null);
                }
                yield recipeBook.deleteItem(recipe);
            }
            yield new CommandResult("Invalid catalogue for recipe book operation.", null);
        }
        case RECIPE -> {
            requireActiveRecipe();
            if (catalogue instanceof Recipe recipe) {
                Ingredient ingredient = new Ingredient(name, quantity);
                if (!catalogue.getItems().contains(ingredient)) {
                    yield new CommandResult("Ingredient not found in Recipe.", null);
                }
                yield recipe.deleteItem(ingredient);
            }
            yield new CommandResult("Invalid catalogue for recipe operation.", null);
        }
        default -> new CommandResult("Unsupported screen state for DeleteCommand.", null);
        };
    }
}
