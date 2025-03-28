package model.catalogue;

import commands.CommandResult;
import model.Recipe;
import ui.inputparser.InputParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A RecipeBook that manages a collection of recipes with CRUD operations.
 * This class allows adding, deleting, editing, and listing recipes.
 */
public class RecipeBook extends Catalogue<Recipe> {

    private final String NAME = "RecipeBook";

    /**
     * Constructs an empty RecipeBook.
     */
    public RecipeBook() {
        super();
    }

    /**
     * Retrieves the lowercase name of a recipe.
     *
     * @param recipe The recipe whose name is to be retrieved.
     * @return The lowercase name of the recipe.
     */
    private String getRecipeNameLowercase(Recipe recipe) {
        return recipe.getRecipeName().toLowerCase();
    }

    /**
     * Searches for recipes with similar names.
     *
     * @param recipe The recipe to search for.
     * @return A list of recipes with similar names.
     */
    public ArrayList<Recipe> searchSimilarRecipe(Recipe recipe) {
        String[] keywordList = getRecipeNameLowercase(recipe).split(" ");

        return items.stream()
                .filter(currRecipe -> {
                    String name = getRecipeNameLowercase(currRecipe);
                    return Arrays.stream(keywordList).allMatch(name::contains);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Checks if two recipes have the exact same name.
     *
     * @param existingRecipe The existing recipe.
     * @param newRecipe The new recipe.
     * @return True if both recipes have the same name, false otherwise.
     */
    private boolean isExactMatchFound(Recipe existingRecipe, Recipe newRecipe) {
        return getRecipeNameLowercase(existingRecipe).equals(getRecipeNameLowercase(newRecipe));
    }

    /**
     * Adds a new recipe to the RecipeBook.
     *
     * @param recipe The recipe to be added.
     * @return A CommandResult indicating success or failure.
     */
    @Override
    public CommandResult addItem(Recipe recipe) {
        ArrayList<Recipe> similarRecipe = searchSimilarRecipe(recipe);
        if (similarRecipe.isEmpty()) {
            addRecipe(recipe);
            return new CommandResult(recipe.getRecipeName() + " added to recipe book.");
        }

        int choice = InputParser.getUserChoiceForAddRecipe(similarRecipe, recipe);
        if (choice == 0) {
            addRecipe(recipe);
            return new CommandResult(recipe.getRecipeName() + " added to recipe book.");
        }
        return new CommandResult("Operation canceled.");
    }

    /**
     * Helper method to add a recipe to the RecipeBook.
     *
     * @param recipe The recipe to be added.
     */
    private void addRecipe(Recipe recipe) {
        items.add(recipe);
    }

    /**
     * Deletes a recipe from the RecipeBook.
     *
     * @param recipe The recipe to be deleted.
     * @return A CommandResult indicating success or failure.
     */
    @Override
    public CommandResult deleteItem(Recipe recipe) {
        ArrayList<Recipe> similarRecipe = searchSimilarRecipe(recipe);
        if (similarRecipe.isEmpty()) {
            return new CommandResult(recipe.getRecipeName() + " does not exist in the recipe book.");
        }

        if (similarRecipe.size() == SINGLE_MATCH && isExactMatchFound(similarRecipe.get(FIRST_ITEM_INDEX), recipe)) {
            removeRecipe(recipe);
            return new CommandResult(recipe.getRecipeName() + " removed from recipe book.");
        }

        int choice = InputParser.getUserChoiceForDeleteRecipe(similarRecipe, recipe);
        if (choice > 0 && choice <= similarRecipe.size()) {
            removeRecipe(similarRecipe.get(choice - 1));
            return new CommandResult(recipe.getRecipeName() + " removed from recipe book.");
        }
        return new CommandResult("Operation canceled.");
    }

    /**
     * Helper method to remove a recipe from the RecipeBook.
     *
     * @param recipe The recipe to be removed.
     */
    private void removeRecipe(Recipe recipe) {
        items.remove(recipe);
    }

    /**
     * Edits an existing recipe by replacing it with a new version.
     *
     * @param oldRecipe The recipe to be replaced.
     * @param newRecipe The updated recipe.
     * @return A CommandResult indicating success or failure.
     */
    public CommandResult editItem(Recipe oldRecipe, Recipe newRecipe) {
        if (items.contains(newRecipe)) {
            return new CommandResult("A recipe with the name " + newRecipe.getRecipeName() + " already exists.");
        }

        int index = items.indexOf(oldRecipe);
        if (index != -1) {
            items.set(index, newRecipe);
            return new CommandResult(oldRecipe.getRecipeName() + " updated to " + newRecipe.getRecipeName());
        }
        return new CommandResult("Recipe not found.");
    }

    /**
     * Lists all recipes in the RecipeBook.
     *
     * @return A CommandResult containing a formatted list of all recipes.
     */
    @Override
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("No recipes found.");
        }
        StringBuilder result = new StringBuilder("Recipe Book:\n");
        for (int i = 0; i < items.size(); i++) {
            result.append(i + 1).append(". ").append(items.get(i).getRecipeName()).append("\n");
        }
        return new CommandResult(result.toString().trim());
    }

    public String getName() {
        return NAME;
    }
}
