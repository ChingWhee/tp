package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

import java.util.ArrayList;

public class Recipe extends IngredientCatalogue {
    private String recipeName;

    public Recipe() {}

    public Recipe(String name) {
        this.recipeName = name;
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients) {
        this.recipeName = name;
        this.items.addAll(ingredients);
    }

    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public String getType() {
        return "Recipe";
    }

    @Override
    protected String getCatalogueLabel() {
        return "recipe";
    }

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