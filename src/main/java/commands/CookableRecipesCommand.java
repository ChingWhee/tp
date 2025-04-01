package commands;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import model.catalogue.Inventory;

import java.util.ArrayList;

/**
 * Represents a command to find all recipes that can be cooked with the current inventory.
 *
 * <p>This command checks the {@link RecipeBook} and determines which recipes
 * have all the required ingredients available in sufficient quantity in the {@link Inventory}.
 * It then returns a list of these cookable recipes.</p>
 */
public class CookableRecipesCommand extends Command {

    private final RecipeBook recipeBook;

    /**
     * Constructs a {@code CookableRecipesCommand} with the given recipe book.
     *
     * @throws AssertionError if {@code recipeBook} is null.
     */
    public CookableRecipesCommand() {
        this.recipeBook = KitchenCTRL.getRecipeBook();
    }

    /**
     * Determines which recipes can be cooked with the available ingredients in the inventory.
     *
     * <p>Each recipe in the {@code RecipeBook} is checked against the inventory.
     * A recipe is considered cookable if all of its required ingredients are present
     * in the inventory in at least the required quantities.</p>
     *
     * @param inventory The inventory containing available ingredients.
     * @return A list of {@code Recipe} objects that can be fully cooked.
     */
    public ArrayList<Recipe> getCookableRecipes(RecipeBook recipeBook, Inventory inventory) {
        ArrayList<Recipe> cookableRecipes = new ArrayList<>();

        ArrayList<Recipe> allRecipes = recipeBook.getItems(); // Retrieves all recipes from RecipeBook
        ArrayList<Ingredient> inventoryItems = inventory.getItems(); // Retrieves all ingredients in inventory

        //iterate through every recipe in RecipeBook
        for (Recipe recipe : allRecipes) {
            boolean canCook = true;
            //check if all the ingredients of the recipe is there
            for (Ingredient requiredIngredient : recipe.getItems()) {
                Ingredient availableIngredient = findIngredientByName(inventoryItems, requiredIngredient.getIngredientName());
                if (availableIngredient == null || availableIngredient.getQuantity() < requiredIngredient.getQuantity()) {
                    canCook = false;
                    break;
                }
            }
            if (canCook) {
                cookableRecipes.add(recipe);
            }
        }

        return cookableRecipes;
    }

    /**
     * Finds an ingredient by name within a list of ingredients.
     *
     * @param ingredients The list of ingredients to search.
     * @param name The name of the ingredient to find.
     * @return The {@code Ingredient} if found, or {@code null} if not found.
     */
    private Ingredient findIngredientByName(ArrayList<Ingredient> ingredients, String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getIngredientName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    /**
     * Executes the command to find all cookable recipes based on the current inventory.
     *
     * <p>This method retrieves the inventory and checks for all recipes that can be fully cooked.</p>
     *
     * @param catalogue The catalogue of available items (not used in this method).
     * @return A {@code CommandResult} listing all cookable recipes or indicating none are available.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        Inventory inventory = KitchenCTRL.getInventory();
        RecipeBook recipeBook = KitchenCTRL.getRecipeBook();

        if (recipeBook == null) {
            // Return empty list or handle the situation gracefully
            return new CommandResult("RecipeBook is empty, please add some recipes!"); // Or log a message/return a specific result
        }

        ArrayList<Recipe> cookableRecipes = getCookableRecipes(recipeBook, inventory);

        if (cookableRecipes.isEmpty()) {
            return new CommandResult("No recipes can be cooked with the current inventory. Please get more ingredients!");
        }

        return new CommandResult("Cookable recipes: " + cookableRecipes);
    }
}
