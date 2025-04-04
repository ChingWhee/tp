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

    protected abstract String getCatalogueLabel();

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

    @Override
    public Ingredient getItemByName(String name) {
        for (Ingredient item : items) {
            if (item.getIngredientName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    protected boolean isExactMatchFound(Ingredient existingIngredient, Ingredient newIngredient) {
        return existingIngredient.getIngredientName().equalsIgnoreCase(newIngredient.getIngredientName());
    }

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

    private CommandResult addIngredient(Ingredient ingredient) {
        items.add(ingredient);
        return new CommandResult(ingredient.getQuantity() + "x " + ingredient.getIngredientName() +
            " added to " + getCatalogueLabel() + ".");
    }

    private CommandResult increaseQuantity(Ingredient existingIngredient, Ingredient newIngredient) {
        int initialQuantity = existingIngredient.getQuantity();
        int increaseQuantity = newIngredient.getQuantity();

        existingIngredient.addQuantity(increaseQuantity);

        return new CommandResult(existingIngredient.getIngredientName() + ": " +
            "Initial quantity = " + initialQuantity + ", " +
            "Added = " + increaseQuantity + ", " +
            "Total = " + existingIngredient.getQuantity());
    }

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

    private CommandResult removeIngredient(Ingredient ingredient) {
        items.remove(ingredient);
        return new CommandResult(ingredient.getIngredientName() +
            " removed from " + getCatalogueLabel() + ".");
    }


    public CommandResult removeAllIngredients() {
        items.clear();
        return new CommandResult("All ingredients removed from " + getCatalogueLabel() + ".");
    }

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

    @Override
    public CommandResult findItem(String query) {
        return super.findItem(query, Ingredient::getIngredientName);
    }
}