package model.catalogue;

import commands.CommandResult;
import model.Ingredient;
import java.util.ArrayList;

public class Recipe extends Catalogue<Ingredient> {
    private String recipeName;

    /**
     * Constructs an empty recipe with no name or ingredients.
     */
    public Recipe() { }

    /**
     * Constructs a recipe with the specified name and no ingredients.
     *
     * @param recipeName the name of the recipe
     */
    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * Constructs a recipe with the specified name and a list of ingredients.
     *
     * @param recipeName the name of the recipe
     * @param ingredients the list of ingredients to include in the recipe
     */
    public Recipe(String recipeName, ArrayList<Ingredient> ingredients) {
        this.recipeName = recipeName;
        items.addAll(ingredients);
    }

    /**
     * Returns the name of the recipe.
     *
     * @return the recipe name
     */
    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public CommandResult addItem(Ingredient ingredient) {
        items.add(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " added to recipe for " + getRecipeName() + ".");
    }

    /**
     * Updates the quantity of an ingredient in the list if it exists;
     * otherwise, adds the ingredient as a new item.
     *
     * @param ingredient The ingredient to update or add.
     * @return The result of the update operation.
     */
    public CommandResult updateItem(Ingredient ingredient) {
        for (Ingredient item : items) {
            String itemNameInList = item.getIngredientName();
            String currItemName = ingredient.getIngredientName();
            if (itemNameInList.equalsIgnoreCase(currItemName)) {
                item.setQuantity(ingredient.getQuantity());
                return new CommandResult(ingredient.getIngredientName()
                        + " quantity updated in " + getRecipeName() + ".");
            }
        }

        // Ingredient not found - add as new
        items.add(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " added to " + getRecipeName() + ".");
    }

    /**
     * Deletes the specified ingredient from the recipe.
     *
     * @param ingredient the ingredient to delete
     * @return a {@code CommandResult} indicating the deletion outcome
     */
    @Override
    public CommandResult deleteItem(Ingredient ingredient) {
        items.remove(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " deleted from recipe for " + getRecipeName() + ".");
    }

    /**
     * Searches for ingredients in the recipe whose names contain the given query string.
     *
     * @param query the search query
     * @return a {@code CommandResult} with matching ingredient(s), if any
     */
    @Override
    public CommandResult findItem(String query) {
        return super.findItem(query, Ingredient::getIngredientName);
    }

    /**
     * Retrieves an ingredient from the recipe by its name, ignoring case.
     *
     * @param name the name of the ingredient to find
     * @return the matching {@code Ingredient}, or {@code null} if not found
     */
    @Override
    public Ingredient getItemByName(String name) {
        for (Ingredient ingredient : items) {
            if (ingredient.getIngredientName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null; // Not found
    }

    /**
     * Checks whether this recipe is equal to another object.
     * Two recipes are considered equal if their names are the same.
     *
     * @param o the object to compare with
     * @return {@code true} if the recipes are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Recipe other = (Recipe) o;
        return this.recipeName.equals(other.recipeName);
    }

    /**
     * Returns the type of this catalogue.
     *
     * @return a string representing the type, which is "Recipe"
     */
    @Override
    public String getType(){
        return "Recipe";
    }

    /**
     * Returns the string representation of the recipe, including its name and ingredients.
     *
     * @return a formatted string of the recipe
     */
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(recipeName).append("\n");
        for (Ingredient ingredient : items) {
            content.append(ingredient.toString()).append("\n");
        }
        return content.toString();
    }
}


