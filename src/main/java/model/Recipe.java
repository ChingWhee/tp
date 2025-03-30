package model;

import commands.CommandResult;
import model.catalogue.Catalogue;
public class Recipe extends Catalogue<Ingredient> {
    private String recipeName;

    public Recipe() { }


    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public CommandResult addItem(Ingredient ingredient) {
        items.add(ingredient);
        return null;
    }

    @Override
    public CommandResult deleteItem(Ingredient ingredient) {
        items.remove(ingredient);
        return null;
    }

    //this is to get the name of an ingredient in the recipe by the name of the ingredient
    @Override
    public Ingredient getItemByName(String name) {
        for (Ingredient ingredient : items) {
            if (ingredient.getIngredientName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null; // Not found
    }

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
}


