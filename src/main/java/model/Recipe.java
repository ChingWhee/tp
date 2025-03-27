package model;

import commands.CommandResult;
import model.catalogue.Catalogue;
public class Recipe extends Catalogue<Ingredient> {
    private String recipeName;

    public Recipe() { }

    public Recipe(String name) {
        recipeName = name;
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


