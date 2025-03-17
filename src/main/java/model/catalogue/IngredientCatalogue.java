package model.catalogue;

import model.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ui.inputparser.InputParser;

/**
 * Manages an inventory of food ingredients, allowing users to add, delete,
 * and search for items while handling similar and duplicate entries.
 */
public class IngredientCatalogue extends Catalogue<Ingredient> {
    /**
     * Constructs an empty inventory catalogue.
     */

    public IngredientCatalogue() {}

    /**
     * Searches for similar items in the inventory based on keyword matching.
     *
     * @param ingredient The ingredient to search for.
     * @return A list of ingredients that contain all keywords in the input ingredient name.
     */
    public ArrayList<Ingredient> searchSimilarIngredient(Ingredient ingredient) {
        String ingredientName = ingredient.getIngredientName();

        // Split ingredient name into individual words and check for matches
        String[] keywordList = ingredientName.toLowerCase().split(" ");

        return items.stream()
                .filter(currIngredient -> {
                    String name = currIngredient.getIngredientName().toLowerCase();
                    return Arrays.stream(keywordList).allMatch(name::contains);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Checks if an existing ingredient matches exactly with the new ingredient.
     *
     * @param existingIngredient The existing ingredient in the inventory.
     * @param newIngredient The new ingredient being added.
     * @return {@code true} if the names match exactly, {@code false} otherwise.
     */
    private boolean isExactMatchFound(Ingredient existingIngredient, Ingredient newIngredient) {
        String existingIngredientName = existingIngredient.getIngredientName();
        String newIngredientName = newIngredient.getIngredientName();

        return existingIngredientName.equalsIgnoreCase(newIngredientName);
    }

    /**
     * Adds an ingredient to the inventory. If a similar ingredient exists,
     * prompts the user to either add as a new item or increase the quantity of an existing one.
     *
     * @param ingredient The ingredient to be added.
     */
    @Override
    public void addItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

        // Case 1: No similar ingredient found, add the new ingredient directly
        if (similarIngredient.isEmpty()) {
            addIngredient(ingredient);
            return;
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarIngredient.size() == SINGLE_MATCH &&
                isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
            increaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
            return;
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForAddIngredient(similarIngredient, ingredient);

        if (choice == 0) {
            // User chose to add the new ingredient as a separate entry
            addIngredient(ingredient);
        } else if (choice > 0 && choice <= similarIngredient.size()) {
            // User selected an existing ingredient, increase its quantity
            increaseQuantity(similarIngredient.get(choice - 1), ingredient);
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    /**
     * Adds a new ingredient to the inventory.
     *
     * @param ingredient The ingredient to add.
     */
    private void addIngredient(Ingredient ingredient) {
        items.add(ingredient);

        contentManager.saveCatalogue(getCatalogueContent());

        System.out.println(ingredient.getIngredientName() + " added to inventory.");
    }

    /**
     * Increases the quantity of an existing ingredient in the inventory.
     *
     * @param existingIngredient The existing ingredient in the inventory.
     * @param newIngredient The new ingredient being added, increasing to the quantity.
     */
    private void increaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int increaseQuantity = newIngredient.getQuantity();

        existingIngredient.addQuantity(newIngredient.getQuantity());

        contentManager.saveCatalogue(getCatalogueContent());

        System.out.println(existingIngredient.getIngredientName() + ": " +
                "Initial quantity = " + initialQuantity + ", " +
                "Added = " + increaseQuantity + ", " +
                "Total = " + existingIngredient.getQuantity());
    }

    /**
     * Deletes an ingredient from the inventory. If similar ingredients exist,
     * prompts the user to either decrease quantity or fully remove the item.
     *
     * @param ingredient The ingredient to delete.
     */
    @Override
    public void deleteItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

        // Case 1: No similar ingredient found, inform the user
        if (similarIngredient.isEmpty()) {
            System.out.println(ingredient.getIngredientName() + " does not exist in the inventory.");
            return;
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarIngredient.size() == SINGLE_MATCH &&
                isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
            decreaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
            return;
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForDeleteIngredient(similarIngredient, ingredient);

        if (choice > 0 && choice <= similarIngredient.size()) {
            // User selected an existing ingredient, decrease its quantity
            decreaseQuantity(similarIngredient.get(choice - 1), ingredient);
        } else {
            // User provided an invalid input, cancel the operation
            System.out.println("Operation canceled.");
        }
    }

    /**
     * Removes an ingredient from the inventory.
     *
     * @param ingredient The ingredient to remove.
     */
    private void removeIngredient(Ingredient ingredient) {
        items.remove(ingredient);

        contentManager.saveCatalogue(getCatalogueContent());

        System.out.println(ingredient.getIngredientName() + " removed from inventory.");
    }

    /**
     * Decreases the quantity of an ingredient in the inventory.
     * If the quantity reaches zero, the item is removed.
     *
     * @param existingIngredient The existing ingredient in the inventory.
     * @param newIngredient The ingredient being removed or decreased in quantity.
     */
    public void decreaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int decreaseQuantity = newIngredient.getQuantity();

        existingIngredient.subtractQuantity(newIngredient.getQuantity());

        System.out.println(existingIngredient.getIngredientName() + ": " +
                "Initial quantity = " + initialQuantity + ", " +
                "Subtracted = " + decreaseQuantity + ", " +
                "Remaining = " + existingIngredient.getQuantity());

        if (existingIngredient.getQuantity() <= 0) {
            removeIngredient(existingIngredient);
        }

        contentManager.saveCatalogue(getCatalogueContent());
    }
}
