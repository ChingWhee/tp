package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

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

        // Ingredient not found — add as new
        items.add(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " added to " + getRecipeName() + ".");
    }

    @Override
    public CommandResult deleteItem(Ingredient ingredient) {
        items.remove(ingredient);
        return new CommandResult(ingredient.getIngredientName() + " deleted from recipe for " + getRecipeName() + ".");
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

    @Override
    public String getType(){
        return "Recipe";
    }
}


