package model.catalogue;

import commands.CommandResult;
import ui.inputparser.InputParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A RecipeBook that manages a collection of recipes with CRUD operations.
 * This class allows adding, deleting, editing, and listing recipes.
 */
public class RecipeBook extends Catalogue<Recipe> {

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
     * @throws IllegalArgumentException if the recipe name is null or empty.
     */
    private String getRecipeNameLowercase(Recipe recipe) {
        if (recipe == null || recipe.getRecipeName() == null || recipe.getRecipeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty.");
        }
        return recipe.getRecipeName().trim().toLowerCase();
    }

    @Override
    public String getCatalogueContent() {
        if (items.isEmpty()) {
            return "";
        }
        StringBuilder content = new StringBuilder();
        for (Recipe recipe : items) {
            content.append(recipe.toString()).append("\n");
        }
        return content.toString().trim();
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
     * Finds a recipe by its name.
     *
     * @param name The name of the recipe to search for.
     * @return The Recipe object if found, otherwise null.
     */
    @Override
    public Recipe getItemByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (Recipe item : items) {
            if (item.getRecipeName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        return null;
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
        if (recipe == null) {
            return new CommandResult("Cannot add a null recipe.");
        }

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
        if (recipe == null) {
            throw new IllegalArgumentException("Cannot add null recipe.");
        }
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
        if (recipe == null) {
            return new CommandResult("Cannot delete a null recipe.");
        }

        ArrayList<Recipe> similarRecipe = searchSimilarRecipe(recipe);
        String recipeName = (recipe.getRecipeName() == null) ? "[unknown]" : recipe.getRecipeName();

        if (similarRecipe.isEmpty()) {
            return new CommandResult(recipeName + " does not exist in the recipe book.");
        }

        if (similarRecipe.size() == SINGLE_MATCH && isExactMatchFound(similarRecipe.get(FIRST_ITEM_INDEX), recipe)) {
            removeRecipe(recipe);
            return new CommandResult(recipeName + " removed from recipe book.");
        }

        int choice = InputParser.getUserChoiceForDeleteRecipe(similarRecipe, recipe);
        if (choice > 0 && choice <= similarRecipe.size()) {
            removeRecipe(similarRecipe.get(choice - 1));
            return new CommandResult(recipeName + " removed from recipe book.");
        }
        return new CommandResult("Operation canceled.");
    }

    /**
     * Helper method to remove a recipe from the RecipeBook.
     *
     * @param recipe The recipe to be removed.
     */
    private void removeRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Cannot remove null recipe.");
        }
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
        if (oldRecipe == null || newRecipe == null) {
            return new CommandResult("Cannot edit a null recipe.");
        }

        for (Recipe existing : items) {
            if (existing.getRecipeName().equalsIgnoreCase(newRecipe.getRecipeName())
                    && !Objects.equals(existing, oldRecipe)) {
                return new CommandResult("A recipe with the name " + newRecipe.getRecipeName() + " already exists.");
            }
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
        StringBuilder result = new StringBuilder("Your recipe book contains the following recipes:\n");
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getRecipeName();
            result.append(i + 1).append(". ").append(name == null ? "[Unnamed Recipe]" : name).append("\n");
        }
        return new CommandResult(result.toString().trim());
    }

    /**
     * Finds recipes whose names contain the given query (case-insensitive).
     *
     * @param query The search keyword or partial name of the recipe.
     * @return A CommandResult listing the matching recipes.
     */
    public CommandResult findItem(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new CommandResult("Please provide a keyword to search.");
        }

        String lowerQuery = query.trim().toLowerCase();
        ArrayList<Recipe> matching = items.stream()
                .filter(r -> r.getRecipeName() != null && r.getRecipeName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toCollection(ArrayList::new));

        if (matching.isEmpty()) {
            return new CommandResult("No recipes found containing: " + query);
        }

        StringBuilder result = new StringBuilder("Found recipes:\n");
        for (int i = 0; i < matching.size(); i++) {
            result.append(i + 1).append(". ").append(matching.get(i).getRecipeName()).append("\n");
        }

        return new CommandResult(result.toString().trim());
    }

    @Override
    public String getType() {
        return "RecipeBook";
    }
}
