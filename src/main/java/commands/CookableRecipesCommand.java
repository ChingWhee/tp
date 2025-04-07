package commands;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import model.catalogue.Inventory;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Represents a command to find all recipes that can be cooked with the current inventory.
 *
 * This command checks the {@link RecipeBook} and determines which recipes
 * have all the required ingredients available in sufficient quantity in the {@link Inventory}.
 * It returns a formatted list of cookable recipe names.
 */
public class CookableRecipesCommand extends Command {

    private final RecipeBook recipeBook;

    /**
     * Constructs a {@code CookableRecipesCommand} using the {@code RecipeBook}
     * retrieved from {@link KitchenCTRL}.
     *
     * Assumes the {@code RecipeBook} has already been initialized in {@code KitchenCTRL}.
     *
     * @throws AssertionError if {@code KitchenCTRL.getRecipeBook()} returns {@code null}.
     */
    public CookableRecipesCommand() {
        this.recipeBook = KitchenCTRL.getRecipeBook();
    }

    /**
     * Determines which recipes from the given {@code RecipeBook} can be cooked
     * with the available ingredients in the provided {@code Inventory}.
     *
     * A recipe is considered cookable if all of its required ingredients are
     * present in the inventory in sufficient quantities.
     *
     * @param recipeBook The recipe book containing all available recipes.
     * @param inventory The inventory containing available ingredients.
     * @return A list of {@code Recipe} objects that can be fully cooked.
     */
    public ArrayList<Recipe> getCookableRecipes(RecipeBook recipeBook, Inventory inventory) {
        ArrayList<Recipe> cookableRecipes = new ArrayList<>();
        ArrayList<Recipe> allRecipes = recipeBook.getItems();
        ArrayList<Ingredient> inventoryItems = inventory.getItems();

        // Check each recipe for ingredient sufficiency
        for (Recipe recipe : allRecipes) {
            boolean canCook = true;

            for (Ingredient requiredIngredient : recipe.getItems()) {
                String requiredName = requiredIngredient.getIngredientName();
                int requiredQty = requiredIngredient.getQuantity();

                Ingredient available = findIngredientByName(inventoryItems, requiredName);

                if (available == null || available.getQuantity() < requiredQty) {
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
     * Searches a list of ingredients for one with the specified name.
     *
     * Comparison is case-insensitive.
     *
     * @param ingredients The list of ingredients to search.
     * @param name The name of the ingredient to find.
     * @return The matching {@code Ingredient} if found, or {@code null} if not found.
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
     * Executes the command to find and list all recipes that can be cooked
     * using the current inventory.
     *
     * If no recipes are cookable or the catalogue is not an {@code Inventory},
     * appropriate error messages are returned. Otherwise, the result lists all
     * cookable recipes, each prefixed with a dash on a new line.
     *
     * @param catalogue The current item catalogue, expected to be an {@code Inventory}.
     * @return A {@code CommandResult} listing cookable recipes or a message indicating none can be cooked.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        if (!((catalogue instanceof Inventory) || (catalogue instanceof RecipeBook))) {
            return new CommandResult("Catalogue is not Inventory!");
        }

        Inventory inventory = KitchenCTRL.getInventory();

        if (recipeBook == null) {
            return new CommandResult("RecipeBook is empty, please add some recipes!");
        }

        ArrayList<Recipe> cookableRecipes = getCookableRecipes(recipeBook, inventory);

        if (cookableRecipes.isEmpty()) {
            String message = "No recipes can be cooked with the current inventory. Please get more ingredients!";
            return new CommandResult(message);
        }

        // Format recipe names as a bulleted list
        String recipeNames = cookableRecipes.stream()
                .map(recipe -> "- " + recipe.getRecipeName())
                .collect(Collectors.joining("\n"));

        return new CommandResult("Cookable recipes:\n" + recipeNames);
    }
}
