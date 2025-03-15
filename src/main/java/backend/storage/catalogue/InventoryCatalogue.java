package backend.storage.catalogue;

import backend.storage.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import ui.inputparser.InputParser;

/**
 * Manages an inventory of food items.
 */
public class InventoryCatalogue extends Catalogue<Ingredient> {
    private static final int FIRST_ITEM_INDEX = 0;
    private static final int SINGLE_MATCH = 1;

    public InventoryCatalogue() {
        super();
    }

    public ArrayList<Ingredient> searchSimilarItem(Ingredient ingredient) {
        String ingredientName = ingredient.getIngredientName();

        // Split ingredient name into individual words and
        // check for existing ingredient with all matching keywords
        String[] keywordList = ingredientName.toLowerCase().split(" ");

        return items.stream()
                .filter(currIngredient -> {
                    String name = currIngredient.getIngredientName().toLowerCase();
                    return Arrays.stream(keywordList).allMatch(name::contains);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void addItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarItems = searchSimilarItem(ingredient);

        // Case 1: No similar ingredient found, add the new ingredient directly
        if (similarItems.isEmpty()) {
            addNewItem(ingredient);
            return;
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarItems.size() == SINGLE_MATCH &&
                isExactMatchFound(similarItems.get(FIRST_ITEM_INDEX), ingredient)) {
            increaseQuantity(similarItems.get(FIRST_ITEM_INDEX), ingredient);
            return;
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForAdd(similarItems, ingredient);

        if (choice == 0) {
            // User chose to add the new ingredient as a separate entry
            addNewItem(ingredient);
        } else if (choice > 0 && choice <= similarItems.size()) {
            // User selected an existing ingredient, increase its quantity
            increaseQuantity(similarItems.get(choice - 1), ingredient);
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    private void addNewItem(Ingredient ingredient) {
        items.add(ingredient);
        System.out.println(ingredient.getIngredientName() + " added to inventory.");
    }

    private boolean isExactMatchFound(Ingredient existingIngredient, Ingredient newIngredient) {
        String existingIngredientName = existingIngredient.getIngredientName();
        String newIngredientName = newIngredient.getIngredientName();

        return existingIngredientName.equalsIgnoreCase(newIngredientName);
    }

    private void increaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        existingIngredient.addQuantity(newIngredient.getQuantity());
    }

    @Override
    public void deleteItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarItems = searchSimilarItem(ingredient);

        // Case 1: No similar ingredient found, inform the user
        if (similarItems.isEmpty()) {
            System.out.println(ingredient.getIngredientName() + " does not exist in the inventory.");
            return;
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarItems.size() == SINGLE_MATCH &&
                isExactMatchFound(similarItems.get(FIRST_ITEM_INDEX), ingredient)) {
            decreaseQuantity(similarItems.get(FIRST_ITEM_INDEX), ingredient);
            return;
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForDelete(similarItems, ingredient);

        if (choice > 0 && choice <= similarItems.size()) {
            // User selected an existing ingredient, decrease its quantity
            increaseQuantity(similarItems.get(choice - 1), ingredient);
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    public void decreaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        existingIngredient.subtractQuantity(newIngredient.getQuantity());
    }
}
