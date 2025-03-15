package commands;

import backend.storage.Ingredient;
import backend.storage.Recipe;
import java.util.ArrayList;
import java.util.List;

public class Commands {
    private List<Ingredient> inventory;
    private Recipe recipe;

    public Commands() {

        this.inventory = new ArrayList<>();
        this.recipe = new Recipe();
    }

    // Add ingredient to inventory
    public void addIngredient(String name, int quantity) {
        for (Ingredient ing : inventory) {
            if (ing.getIngredientName().equalsIgnoreCase(name)) {
                ing.setQuantity(ing.getQuantity() + quantity);
                return;
            }
        }
        inventory.add(new Ingredient(name, quantity));
    }

    // Edit ingredient quantity
    public void editIngredient(String name, int newQuantity) {
        for (Ingredient ing : inventory) {
            if (ing.getIngredientName().equalsIgnoreCase(name)) {
                ing.setQuantity(newQuantity);
                return;
            }
        }
    }

    // List all ingredients in inventory
    public void listIngredients() {
        for (Ingredient ing : inventory) {
            System.out.println(ing.getIngredientName() + ": " + ing.getQuantity());
        }
    }

    // Delete an ingredient from inventory
    public void deleteIngredient(String name) {
        inventory.removeIf(ing -> ing.getIngredientName().equalsIgnoreCase(name));
    }

    public boolean canCookRecipe(List<Ingredient> recipeIngredients) {
        for (Ingredient required : recipeIngredients) {
            boolean found = false;
            for (Ingredient inv : inventory) {
                if (inv.getIngredientName().equalsIgnoreCase(required.getIngredientName())) {
                    found = true;
                    if (inv.getQuantity() < required.getQuantity()) {
                        return false; // Insufficient quantity
                    }
                    break;
                }
            }
            if (!found) {
                return false; // Ingredient not found in inventory
            }
        }
        return true; // All ingredients are sufficient
    }
}
