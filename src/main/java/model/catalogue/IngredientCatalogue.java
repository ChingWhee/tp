//@@author ChingWhee
package model.catalogue;

import commands.CommandResult;
import model.Ingredient;
import ui.inputparser.ConflictHelper;

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
        String[] inputKeywords = ingredient.getIngredientName().toLowerCase().split(" ");

        return items.stream()
                .filter(currIngredient -> {
                    String[] itemKeywords = currIngredient.getIngredientName().toLowerCase().split(" ");

                    // Check if any input word is in the item, or vice versa
                    return Arrays.stream(inputKeywords).anyMatch(inputWord ->
                            Arrays.stream(itemKeywords).anyMatch(itemWord ->
                                    inputWord.contains(itemWord) || itemWord.contains(inputWord)
                            )
                    );
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
    //@@author

    /**
     * Adds an ingredient to the catalogue. If a similar ingredient is found, the user is prompted
     * to choose whether to update the quantity of an existing ingredient or add the new one.
     *
     * @param ingredient The ingredient to add.
     * @param isSilenced skips user input if silenced
     * @return A {@link CommandResult} describing the outcome.
     */
    @Override
    public CommandResult addItem(Ingredient ingredient, boolean isSilenced) {
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

            // Check for an exact match first
            for (Ingredient existingIngredient : similarIngredient) {
                if (isExactMatchFound(existingIngredient, ingredient)) {
                    return increaseQuantity(existingIngredient, ingredient);
                }
            }

            //Silent mode: skip user interaction, default to adding as new
            if (isSilenced) {
                return addIngredient(ingredient);
            }

            //loud mode
            int choice = ConflictHelper.getUserChoiceForAddIngredient(similarIngredient, ingredient);
            if (choice == 0) {
                return addIngredient(ingredient);
            } else if (choice > 0 && choice <= similarIngredient.size()) {
                return increaseQuantity(similarIngredient.get(choice - 1), ingredient);
            }

            return new CommandResult("Operation canceled.");
        } catch (Exception e) {
            return new CommandResult("Error adding ingredient: " + e.getMessage());
        }
    }

    //@@author ChingWhee
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
        int addedQuantity = newIngredient.getQuantity();
        existingIngredient.addQuantity(addedQuantity);

        if (existingIngredient.getQuantity() == 99999) {
            return new CommandResult("Max Quantity of Ingredient reached, limited to 99999");
        }

        return new CommandResult(
                addedQuantity + "x " + existingIngredient.getIngredientName() +
                        " added to " + getCatalogueLabel() + "."
        );
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
            return new CommandResult("Ingredient does not exist in the "
                    + getCatalogueLabel() + ".");
        }

        // Check for an exact match first
        for (Ingredient existingIngredient : similarIngredient) {
            if (isExactMatchFound(existingIngredient, ingredient)) {
                return decreaseQuantity(existingIngredient, ingredient);
            }
        }

        int choice = ConflictHelper.getUserChoiceForDeleteIngredient(similarIngredient, ingredient);

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
        int quantity = ingredient.getQuantity(); // store quantity before mutation
        String name = ingredient.getIngredientName(); // store name just in case too

        items.remove(ingredient);

        return new CommandResult(
                quantity + "x " + name + " removed from " + getCatalogueLabel() + "."
        );
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
    //@@author

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
        int decreaseAmount = newIngredient.getQuantity();
        int actualRemoved = Math.min(initialQuantity, decreaseAmount);

        existingIngredient.subtractQuantity(actualRemoved);
        String name = existingIngredient.getIngredientName();
        String label = getCatalogueLabel();

        boolean wasOverDeleted = decreaseAmount > initialQuantity;
        boolean wasFullyRemoved = existingIngredient.getQuantity() <= 0;

        if (wasFullyRemoved) {
            items.remove(existingIngredient);
        }

        StringBuilder message = new StringBuilder();
        message.append(actualRemoved).append("x ").append(name).append(" removed from ").append(label).append(".");

        if (wasOverDeleted) {
            message.append(" (Warning: You tried to remove more than available; only ").append(initialQuantity)
                    .append("x ").append(name).append(" was removed.)");
        }

        return new CommandResult(message.toString());
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

    private CommandResult adjustQuantity(Ingredient existing, int newQuantity) {
        int currentQuantity = existing.getQuantity();

        if (newQuantity == currentQuantity) {
            return new CommandResult("No changes made: Quantity is already " + newQuantity + ".");
        } else if (newQuantity > currentQuantity) {
            int increaseAmount = newQuantity - currentQuantity;
            Ingredient deltaIngredient = new Ingredient(existing.getIngredientName(), increaseAmount);
            return increaseQuantity(existing, deltaIngredient);
        } else {
            int decreaseAmount = currentQuantity - newQuantity;
            Ingredient deltaIngredient = new Ingredient(existing.getIngredientName(), decreaseAmount);
            return decreaseQuantity(existing, deltaIngredient);
        }
    }

    /**
     * Edits an ingredient in the current catalogue by updating its quantity.
     * <p>
     * This method performs validation on the provided ingredient, searches for similar
     * ingredients in the catalogue, and attempts to update the matching one. If multiple
     * similar ingredients are found but none match exactly, the user will be prompted
     * to choose which one to edit. The ingredient's quantity is then updated accordingly.
     * </p>
     *
     * @param ingredient The {@link Ingredient} to edit, containing the target name and new quantity.
     * @return A {@link CommandResult} containing a success or error message depending on the outcome.
     *         Possible outcomes include:
     *         <ul>
     *             <li>Success message after updating the quantity.</li>
     *             <li>Message if no similar ingredient was found.</li>
     *             <li>Message if input validation fails (e.g., null ingredient, empty name, negative quantity).</li>
     *             <li>Message if the operation was canceled or an error occurred.</li>
     *         </ul>
     */
    public CommandResult editItem(Ingredient ingredient) {
        try {
            if (ingredient == null) {
                return new CommandResult("Invalid ingredient: Ingredient is null.");
            }

            String name = ingredient.getIngredientName();
            if (name == null || name.trim().isEmpty()) {
                return new CommandResult("Invalid ingredient: name must be non-empty.");
            }

            int newQuantity = ingredient.getQuantity();
            if (newQuantity < 0) {
                return new CommandResult("Invalid ingredient: quantity must be zero or more.");
            }

            ArrayList<Ingredient> similarIngredients = searchSimilarIngredient(ingredient);

            if (similarIngredients.isEmpty()) {
                return new CommandResult("The ingredient does not exist in the " + getCatalogueLabel() + ".");
            }

            // Exact match first
            for (Ingredient existing : similarIngredients) {
                if (isExactMatchFound(existing, ingredient)) {
                    return adjustQuantity(existing, newQuantity);
                }
            }

            // Let user choose which one to edit
            int choice = ConflictHelper.getUserChoiceForEditIngredient(similarIngredients, ingredient);

            if (choice > 0 && choice <= similarIngredients.size()) {
                Ingredient selected = similarIngredients.get(choice - 1);
                return adjustQuantity(selected, newQuantity);
            }

            return new CommandResult("Operation canceled.");
        } catch (Exception e) {
            return new CommandResult("Error editing ingredient: " + e.getMessage());
        }
    }
}
