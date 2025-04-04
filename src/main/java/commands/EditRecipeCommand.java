package commands;


import controller.KitchenCTRL;
import controller.ScreenState;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import ui.inputparser.Ui;

/**
 * Represents a command to enter a specific recipe for editing.
 * It sets the active recipe in the controller and transitions to the RECIPE screen.
 */
public class EditRecipeCommand extends Command {
    private final String recipeName;

    /**
     * Constructs an {@code EditRecipeCommand} with the given recipe name.
     *
     * @param args The name of the recipe to edit.
     */
    public EditRecipeCommand(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be empty.");
        }
        this.recipeName = args.trim();
        assert !recipeName.trim().isEmpty() : "Recipe name must not be null or empty";
    }

    /**
     * Executes the command: sets the active recipe and transitions to RECIPE screen.
     *
     * @return A {@code CommandResult} with feedback and screen transition.
     */
    @Override
    public CommandResult execute() {
        RecipeBook recipeBook = KitchenCTRL.getRecipeBook();
        Recipe recipe = recipeBook.getItemByName(recipeName);

        if (recipe == null) {
            return new CommandResult("Recipe not found: " + recipeName, ScreenState.RECIPEBOOK);
        }

        KitchenCTRL.setActiveRecipe(recipe);
        Ui.showRecipeMessage();
        return new CommandResult("Now editing recipe: " + recipeName, ScreenState.RECIPE);
    }
}
