package logic.commands;

import model.Ingredient;
import model.Recipe;
import java.util.ArrayList;
import model.catalogue.IngredientCatalogue;

public class Commands {

    // Add ingredient to inventory
    public static void addIngredient(IngredientCatalogue inventory, String name, int quantity) {
        for (Ingredient ing : inventory.getItems()) {
            if (ing.getIngredientName().equalsIgnoreCase(name)) {
                ing.setQuantity(ing.getQuantity() + quantity);
                return;
            }
        }
        inventory.addItem(new Ingredient(name, quantity));
    }

    // Edit ingredient quantity
    public void editIngredient(IngredientCatalogue inventory,String name, int newQuantity) {
        for (Ingredient ing : inventory.getItems()) {
            if (ing.getIngredientName().equalsIgnoreCase(name)) {
                ing.setQuantity(newQuantity);
                return;
            }
        }
    }

    // List all ingredients in inventory
    public void listIngredients(IngredientCatalogue inventory) {
        for (Ingredient ing : inventory.getItems()) {
            System.out.println(ing.getIngredientName() + ": " + ing.getQuantity());
        }
    }

    // Delete an ingredient from inventory
    public void deleteIngredient(IngredientCatalogue inventory, Ingredient ingredientToDelete) {
        inventory.deleteItem(ingredientToDelete);
    }

    /*
    * Checks if there are enough ingredients in inventory to cook recipe
    *
    * @paramin recipe
    * @paramout arraylist of missing ingredients and quantity
     */
    public static ArrayList<Ingredient> getMissingIngredients(IngredientCatalogue inventory, Recipe targetRecipe) {
        ArrayList<Ingredient> missingIngredients = new ArrayList<>();
        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> targetRecipeIngredients = targetRecipe.getItems();

        for (Ingredient requiredIngredient : targetRecipeIngredients) {
            if (!inventoryItems.contains(requiredIngredient)) {
                missingIngredients.add(requiredIngredient);
            } else {
                int index = inventoryItems.indexOf(requiredIngredient);
                Ingredient availableIngredient = inventoryItems.get(index);
                if (availableIngredient.getQuantity() < requiredIngredient.getQuantity()) {
                    int shortage = requiredIngredient.getQuantity() - availableIngredient.getQuantity();
                    missingIngredients.add(new Ingredient(requiredIngredient.getIngredientName(), shortage));
                }
            }
        }
        return missingIngredients;
    }

    public static void cookRecipe(IngredientCatalogue inventory, Recipe recipeToCook) {
        ArrayList<Ingredient> missingIngredients = getMissingIngredients(inventory, recipeToCook);
        if (!missingIngredients.isEmpty()) {
            return;
        }

        ArrayList<Ingredient> inventoryItems = inventory.getItems();
        ArrayList<Ingredient> ingredientsToCook = recipeToCook.getItems();

        for (Ingredient requiredIngredient : ingredientsToCook) {
            int index = inventoryItems.indexOf(requiredIngredient);
            Ingredient ingredientInInventory = inventoryItems.get(index);
            ingredientInInventory.subtractQuantity(requiredIngredient.getQuantity());
        }

    }
}
