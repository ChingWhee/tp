package model.catalogue;

import model.Recipe;
import ui.inputparser.InputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Manages the ingredients in a recipe
 */
public class RecipeCatalogue extends Catalogue<Recipe> {
    public RecipeCatalogue() {
        super("Recipe_Catalogue");
    }

    public ArrayList<Recipe> searchSimilarRecipe(Recipe recipe) {
        String recipeName = recipe.getRecipeName();

        // Split ingredient name into individual words and check for matches
        String[] keywordList = recipeName.toLowerCase().split(" ");

        return items.stream()
                .filter(currRecipe -> {
                    String name = currRecipe.getRecipeName().toLowerCase();
                    return Arrays.stream(keywordList).allMatch(name::contains);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isExactMatchFound(Recipe existingRecipe, Recipe newRecipe) {
        String existingRecipeName = existingRecipe.getRecipeName();
        String newRecipeName = newRecipe.getRecipeName();

        return existingRecipeName.equalsIgnoreCase(newRecipeName);
    }

    @Override
    public void addItem(Recipe recipe) {
        // Search for similar recipe in the inventory
        ArrayList<Recipe> similarRecipe = searchSimilarRecipe(recipe);

        // Case 1: No similar recipe found, add the new recipe directly
        if (similarRecipe.isEmpty()) {
            addRecipe(recipe);
            return;
        }

        // Case 2: Multiple similar recipes found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForAddRecipe(similarRecipe, recipe);

        if (choice == 0) {
            // User chose to add the new ingredient as a separate entry
            addRecipe(recipe);
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    private void addRecipe(Recipe recipe) {
        items.add(recipe);
        System.out.println(recipe.getRecipeName() + " added to recipe list.");
    }

    @Override
    public void deleteItem(Recipe recipe) {
        // Search for similar recipe in the inventory
        ArrayList<Recipe> similarRecipe = searchSimilarRecipe(recipe);

        // Case 1: No similar ingredient found, inform the user
        if (similarRecipe.isEmpty()) {
            System.out.println(recipe.getRecipeName() + " does not exist in the recipe.");
            return;
        }

        // Case 2: Only one similar recipe exists, check if it's an exact match
        if (similarRecipe.size() == SINGLE_MATCH &&
                isExactMatchFound(similarRecipe.get(FIRST_ITEM_INDEX), recipe)) {
            removeRecipe(recipe);
            return;
        }

        // Case 3: Multiple similar recipes found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForDeleteRecipe(similarRecipe, recipe);

        if (choice > 0 && choice <= similarRecipe.size()) {
            // delete existing recipe
            removeRecipe(similarRecipe.get(choice - 1));
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    private void removeRecipe(Recipe recipe) {
        items.remove(recipe);
        System.out.println(recipe.getRecipeName() + " removed from recipe list.");
    }
}
