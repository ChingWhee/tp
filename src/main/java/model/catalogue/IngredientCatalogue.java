package model.catalogue;

import commands.CommandResult;
import model.Ingredient;
import ui.inputparser.InputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Abstract class for catalogues that manage ingredients (e.g., Inventory, Recipe).
 */
public abstract class IngredientCatalogue extends Catalogue<Ingredient> {

    /**
     * Returns a string label that represents this catalogue (e.g., "inventory", "recipe").
     * Used in user-facing messages for context.
     *
     * @return A label describing the type of ingredient catalogue.
     */
    protected abstract String getCatalogueLabel();

    /**
     * Searches for ingredients in the catalogue whose names match all keywords from the given ingredient.
     * Case-insensitive and supports multi-word matching.
     *
     * @param ingredient The ingredient whose name is used as the search query.
     * @return A list of similar matching ingredients.
     */
    public ArrayList<Ingredient> searchSimilarIngredient(Ingredient ingredient) {
        String ingredientName = ingredient.getIngredientName();
        String[] keywordList = ingredientName.toLowerCase().split(" ");
        return items.stream()
                .filter(currIngredient -> {
                    String name = currIngredient.getIngredientName().toLowerCase();
                    return Arrays.stream(keywordList).allMatch(name::contains);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Retrieves an ingredient by its name, case-insensitive.
     *
     * @param name The name of the ingredient to search for.
     * @return The matching ingredient or {@code null} if not found.
     */
    @Override
    public Ingredient getItemByName(String name) {
        for (Ingredient item : items) {
            if (item.getIngredientName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if two ingredients match by name (case-insensitive).
     *
     * @param existingIngredient An ingredient already in the catalogue.
     * @param newIngredient      A new ingredient being compared.
     * @return True if their names match; false otherwise.
     */
    protected boolean isExactMatchFound(Ingredient existingIngredient, Ingredient newIngredient) {
        return existingIngredient.getIngredientName().equalsIgnoreCase(newIngredient.getIngredientName());
    }

    /**
     * Adds an ingredient to the catalogue. If a similar ingredient is found, the user is prompted
     * to choose whether to update the quantity of an existing ingredient or add the new one.
     *
     * @param ingredient The ingredient to add.
     * @return A {@link CommandResult} describing the outcome.
     */
    @Override
    public CommandResult addItem(Ingredient ingredient) {
        try {
            if (ingredient == null) {
                return new CommandResult("Invalid ingredient: Ingredient is null.");
            }

            String name = ingredient.getIngredientName();
            if (name == null || name.trim().isEmpty()) {
                return new CommandResult("Invalid ingredient: name must be non-empty.");
            }

            int quantity = ingredient.getQuantity();
            if (quantity <= 0) {
                return new CommandResult("Invalid ingredient: quantity must be positive.");
            }

            ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

            if (similarIngredient.isEmpty()) {
                return addIngredient(ingredient);
            }

            if (similarIngredient.size() == SINGLE_MATCH) {
                if (isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
                    return increaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
                }
            }

            int choice = InputParser.getUserChoiceForAddIngredient(similarIngredient, ingredient);
            if (choice == 0) {
                return addIngredient(ingredient);
            } else if (choice > 0 && choice <= similarIngredient.size()) {
                return increaseQuantity(similarIngredient.get(choice - 1), ingredient);
            }

            return new CommandResult("Operation canceled.");
        } catch (Exception e) {
            return new CommandResult("Unexpected error adding ingredient: " + e.getMessage());
        }
    }

    /**
     * Adds a new ingredient to the catalogue without checking for duplicates.
     *
     * @param ingredient The ingredient to add.
     * @return A {@link CommandResult} confirming the addition.
     */
    private CommandResult addIngredient(Ingredient ingredient) {
        items.add(ingredient);
        return new CommandResult(ingredient.getQuantity() + "x " + ingredient.getIngredientName() +
            " added to " + getCatalogueLabel() + ".");
    }

    /**
     * Increases the quantity of an existing ingredient.
     *
     * @param existingIngredient The ingredient to update.
     * @param newIngredient      The ingredient containing the quantity to add.
     * @return A {@link CommandResult} showing the before/after quantity.
     */
    private CommandResult increaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int increaseQuantity = newIngredient.getQuantity();

        existingIngredient.addQuantity(increaseQuantity);

        return new CommandResult(existingIngredient.getIngredientName() + ": " +
            "Initial quantity = " + initialQuantity + ", " +
            "Added = " + increaseQuantity + ", " +
            "Total = " + existingIngredient.getQuantity());
    }

    /**
     * Removes or decreases an ingredient's quantity based on user interaction.
     *
     * @param ingredient The ingredient to delete or reduce.
     * @return A {@link CommandResult} with the outcome of the operation.
     */
    @Override
    public CommandResult deleteItem(Ingredient ingredient) {
        ArrayList<Ingredient> similarIngredient = searchSimilarIngredient(ingredient);

        if (similarIngredient.isEmpty()) {
            return new CommandResult(ingredient.getIngredientName()
                    + " does not exist in the " + getCatalogueLabel() + ".");
        }

        if (similarIngredient.size() == SINGLE_MATCH &&
                isExactMatchFound(similarIngredient.get(FIRST_ITEM_INDEX), ingredient)) {
            return decreaseQuantity(similarIngredient.get(FIRST_ITEM_INDEX), ingredient);
        }

        int choice = InputParser.getUserChoiceForDeleteIngredient(similarIngredient, ingredient);

        if (choice > 0 && choice <= similarIngredient.size()) {
            return decreaseQuantity(similarIngredient.get(choice - 1), ingredient);
        }

        return new CommandResult("Operation canceled.");
    }

    /**
     * Removes an ingredient from the catalogue completely.
     *
     * @param ingredient The ingredient to remove.
     * @return A {@link CommandResult} confirming the removal.
     */
    private CommandResult removeIngredient(Ingredient ingredient) {
        items.remove(ingredient);
        return new CommandResult(ingredient.getIngredientName() +
            " removed from " + getCatalogueLabel() + ".");
    }

    /**
     * Clears all ingredients from the catalogue.
     *
     * @return A {@link CommandResult} confirming all ingredients were removed.
     */
    public CommandResult removeAllIngredients() {
        items.clear();
        return new CommandResult("All ingredients removed from " + getCatalogueLabel() + ".");
    }

    /**
     * Decreases the quantity of an existing ingredient. If the remaining quantity is zero or less,
     * the ingredient is removed from the catalogue.
     *
     * @param existingIngredient The ingredient in the list.
     * @param newIngredient      The ingredient specifying how much to subtract.
     * @return A {@link CommandResult} showing the updated quantity or confirming removal.
     */
    public CommandResult decreaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int decreaseQuantity = newIngredient.getQuantity();

        existingIngredient.subtractQuantity(decreaseQuantity);

        String calculations = existingIngredient.getIngredientName() + ": " +
                "Initial quantity = " + initialQuantity + ", " +
                "Subtracted = " + decreaseQuantity + ", " +
                "Remaining = " + existingIngredient.getQuantity();

        if (existingIngredient.getQuantity() <= 0) {
            return removeIngredient(existingIngredient);
        }

        return new CommandResult(calculations);
    }

    /**
     * Searches for ingredients by a keyword in their name (case-insensitive).
     *
     * @param query The keyword to search for.
     * @return A {@link CommandResult} listing matching ingredients.
     */
    @Override
    public CommandResult findItem(String query) {
        return super.findItem(query, Ingredient::getIngredientName);
    }
}
