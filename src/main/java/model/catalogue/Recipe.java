package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

import java.util.ArrayList;

/**
 * Represents a recipe, which is a fixed list of ingredients.
 * <p>
 * This class extends {@link IngredientCatalogue} to reuse common ingredient-related
 * logic, while also adding recipe-specific behavior such as naming and formatted output.
 * </p>
 */
public class Recipe extends IngredientCatalogue {
    private String recipeName;

    /**
     * Constructs an empty recipe with no name or ingredients.
     */
    public Recipe() {}

    /**
     * Constructs a recipe with a specified name and no ingredients.
     *
     * @param name The name of the recipe.
     */
    public Recipe(String name) {
        this.recipeName = name;
    }

    /**
     * Constructs a recipe with a specified name and a list of ingredients.
     *
     * @param name       The name of the recipe.
     * @param ingredients The ingredients to include in the recipe.
     */
    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        this.recipeName = name;
        this.items.addAll(ingredients);
    }

    /**
     * Gets the name of the recipe.
     *
     * @return The recipe name.
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     * Returns the type identifier of this catalogue.
     *
     * @return A string representing this catalogue's type, which is "Recipe".
     */
    @Override
    public String getType() {
        return "Recipe";
    }

    /**
     * Returns the label "recipe" for use in shared logic that prints user-facing messages.
     *
     * @return The string "recipe".
     */
    @Override
    protected String getCatalogueLabel() {
        return "recipe";
    }

    /**
     * Lists the ingredients in the recipe in a numbered and formatted style.
     * Shows quantity and name for each ingredient.
     *
     * @return A {@link CommandResult} with the formatted list of ingredients or a message if empty.
     */
    @Override
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("No ingredients found.");
        }
        StringBuilder result = new StringBuilder("This recipe requires the following ingredients:\n");
        for (int i = 0; i < items.size(); i++) {
            Ingredient ingredient = items.get(i);
            result.append(i + 1).append(". ")
                    .append(ingredient.getQuantity()).append("x ")
                    .append(ingredient.getIngredientName() == null ? "[Unnamed Ingredient]" : ingredient.getIngredientName())
                    .append("\n");
        }
        return new CommandResult(result.toString().trim());
    }

    /**
     * Returns a string representation of the recipe, including its name and ingredients.
     *
     * @return A formatted string showing the recipe's name and ingredients.
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
