package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import ui.inputparser.InputParser;

/**
 * Manages an inventory of food ingredients, allowing users to add, delete,
 * and search for items while handling similar and duplicate entries.
 */
public class Inventory extends Catalogue<Ingredient> {

    /**
     * Constructs an empty inventory catalogue.
     */
    public Inventory() {

    }

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

    @Override
    public Ingredient getItemByName(String name) {
        for (Ingredient item : items) {
            if (item.getIngredientName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null; // Not found
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
    public CommandResult addItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

        // Case 1: No similar ingredient found, add the new ingredient directly
        if (similarIngredient.isEmpty()) {
            return addIngredient(ingredient);
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarIngredient.size() == SINGLE_MATCH &&
                isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
            return increaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForAddIngredient(similarIngredient, ingredient);

        if (choice == 0) {
            // User chose to add the new ingredient as a separate entry
            return addIngredient(ingredient);
        } else if (choice > 0 && choice <= similarIngredient.size()) {
            // User selected an existing ingredient, increase its quantity
            return increaseQuantity(similarIngredient.get(choice - 1), ingredient);
        }

        // User provided an invalid input, cancel the operation
        return new CommandResult("Operation canceled.");
    }

    /**
     * Adds a new ingredient to the inventory.
     *
     * @param ingredient The ingredient to add.
     */
    private CommandResult addIngredient(Ingredient ingredient) {
        items.add(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " added to inventory.");
    }

    /**
     * Increases the quantity of an existing ingredient in the inventory.
     *
     * @param existingIngredient The existing ingredient in the inventory.
     * @param newIngredient The new ingredient being added, increasing to the quantity.
     */
    private CommandResult increaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int increaseQuantity = newIngredient.getQuantity();

        existingIngredient.addQuantity(newIngredient.getQuantity());

        return new CommandResult(existingIngredient.getIngredientName() + ": " +
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
    public CommandResult deleteItem(Ingredient ingredient) {
        // Search for similar ingredients in the inventory
        ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

        // Case 1: No similar ingredient found, inform the user
        if (similarIngredient.isEmpty()) {
            return new CommandResult(ingredient.getIngredientName()
                    + " does not exist in the inventory.");
        }

        // Case 2: Only one similar ingredient exists, check if it's an exact match
        if (similarIngredient.size() == SINGLE_MATCH &&
                isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
            return decreaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
        }

        // Case 3: Multiple similar ingredients found, prompt user to choose an action
        int choice = InputParser.getUserChoiceForDeleteIngredient(similarIngredient, ingredient);

        if (choice > 0 && choice <= similarIngredient.size()) {
            // User selected an existing ingredient, decrease its quantity
            return decreaseQuantity(similarIngredient.get(choice - 1), ingredient);
        }

        // User provided an invalid input, cancel the operation
        return new CommandResult("Operation canceled.");
    }

    /**
     * Removes an ingredient from the inventory.
     *
     * @param ingredient The ingredient to remove.
     */
    private CommandResult removeIngredient(Ingredient ingredient) {
        items.remove(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " removed from inventory.");
    }

    /**
     * Removes all ingredients from the inventory.
     */
    public CommandResult removeAllIngredients() {
        items.clear();
        return new CommandResult("All ingredients removed from inventory.");
    }

    /**
     * Decreases the quantity of an ingredient in the inventory.
     * If the quantity reaches zero, the item is removed.
     *
     * @param existingIngredient The existing ingredient in the inventory.
     * @param newIngredient The ingredient being removed or decreased in quantity.
     */
    public CommandResult decreaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int decreaseQuantity = newIngredient.getQuantity();

        existingIngredient.subtractQuantity(newIngredient.getQuantity());

        // Create the calculation message
        String calculations = existingIngredient.getIngredientName() + ": " +
                "Initial quantity = " + initialQuantity + ", " +
                "Subtracted = " + decreaseQuantity + ", " +
                "Remaining = " + existingIngredient.getQuantity();

        if (existingIngredient.getQuantity() <= 0) {
            CommandResult removalResult = removeIngredient(existingIngredient);
            return new CommandResult(calculations + "\n" + removalResult.getFeedbackToUser());
        }

        return new CommandResult(calculations);
    }

    //find ingredient in inventory
    @Override
    public CommandResult findItem(String query) {
        return super.findItem(query, Ingredient::getIngredientName);
    }

    @Override
    public String getType(){
        return "Inventory";
    }

    @Override
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("Nothing found in inventory.");
        }
        StringBuilder result = new StringBuilder("These are the items in your inventory:\n");
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getIngredientName();
            result.append(i + 1).append(". ").append(name == null ? "[Unnamed Ingredient]" : name).append("\n");
        }
        return new CommandResult(result.toString().trim());
    }
}
