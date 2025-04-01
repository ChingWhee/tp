package ui.inputparser;

import model.Ingredient;
import model.catalogue.Recipe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user input parsing for selecting items when adding or deleting ingredients in the inventory.
 */
public class InputParser {
    static Scanner scanner = new Scanner(System.in);
    InputParser() {}

    /**
     * Asks the user what to do when adding an ingredient that has similar items in the inventory.
     *
     * @param similarIngredient A list of ingredients similar to the new ingredient.
     * @param newIngredient The ingredient the user is trying to add.
     * @return The user's choice:
     *         - 0 to add the ingredient as a new item.
     *         - A number (1 to the number of similar items) to update an existing ingredient.
     *         - -1 to cancel the action.
     */
    public static int getUserChoiceForAddIngredient(ArrayList<Ingredient> similarIngredient, Ingredient newIngredient) {
        System.out.println("Similar items found in inventory for: " + newIngredient.getIngredientName());

        // Print the list of similar items with corresponding numbers
        System.out.println("Type '0' to add this as a new item.");
        System.out.println("Select an existing item to increase its quantity:");
        for (int i = 0; i < similarIngredient.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: " + similarIngredient.get(i));
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if (choice >= -1 && choice <= similarIngredient.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between -1 and "
                            + similarIngredient.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Asks the user what to do when deleting an ingredient that has similar items in the inventory.
     *
     * @param similarIngredient A list of ingredients similar to the one being deleted.
     * @param newItem The ingredient the user wants to delete.
     * @return The user's choice:
     *         - A number (1 to the number of similar items) to decrease the quantity of an existing ingredient.
     *         - -1 to cancel the action.
     */
    public static int getUserChoiceForDeleteIngredient(ArrayList<Ingredient> similarIngredient, Ingredient newItem) {
        System.out.println("Similar items found in inventory for: " + newItem.getIngredientName());

        // Print the list of similar items with corresponding numbers
        System.out.println("Select an existing item to decrease its quantity:");
        for (int i = 0; i < similarIngredient.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: " + similarIngredient.get(i));
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarIngredient.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarIngredient.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user when a recipe being added is similar to existing ones.
     *
     * @param similarRecipe List of recipes with similar names.
     * @param newRecipe     The recipe the user is trying to add.
     * @return Integer representing the user's choice:
     *         - 0: Add as new recipe.
     *         - -1: Cancel the operation.
     */
    public static int getUserChoiceForAddRecipe(ArrayList<Recipe> similarRecipe, Recipe newRecipe) {
        System.out.println("Similar items found in recipe list for: " + newRecipe.getRecipeName());

        // Print the list of similar recipes with corresponding numbers
        for (int i = 0; i < similarRecipe.size(); i++) {
            System.out.println((i + 1) + ": " + similarRecipe.get(i).getRecipeName());
        }

        System.out.println("Type '0' to add this as a new recipe.");
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if (choice == -1 || choice == 0) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter 0 or -1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user to select which similar recipe to delete.
     *
     * @param similarRecipe List of similar recipes found in the recipe catalogue.
     * @param newRecipe     The recipe name the user is attempting to delete.
     * @return Integer representing the user's choice:
     *         - 1...n: Delete the selected recipe.
     *         - -1: Cancel the operation.
     */
    public static int getUserChoiceForDeleteRecipe(ArrayList<Recipe> similarRecipe, Recipe newRecipe) {
        System.out.println("Similar items found in recipe list for: " + newRecipe.getRecipeName());

        // Print the list of similar recipes with corresponding numbers
        for (int i = 0; i < similarRecipe.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to delete: " + similarRecipe.get(i).getRecipeName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarRecipe.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarRecipe.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
