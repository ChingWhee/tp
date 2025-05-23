package ui.inputparser;

import controller.ScreenState;
import model.Ingredient;
import model.catalogue.Recipe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user input parsing for selecting items when adding or deleting ingredients in the inventory.
 */
public class ConflictHelper {
    private static Scanner scanner = new Scanner(System.in);
    ConflictHelper() {}

    private static String getContextLabel() {
        ScreenState screen = controller.KitchenCTRL.getCurrentScreen();

        return switch (screen) {
        case INVENTORY -> "inventory";
        case RECIPEBOOK -> "recipe book";
        case RECIPE -> "recipe";
        case WELCOME, EXIT, INVALID -> "application"; // fallback case
        };
    }


    public static void setScanner(Scanner testScanner) {
        scanner = testScanner;
    }

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
        String contextLabel = getContextLabel();
        System.out.println("Similar items found in " + contextLabel + ":");

        // Print the list of similar items with corresponding numbers
        System.out.println("Type '0' to add this as a new item.");
        System.out.println("Select an existing item to increase its quantity:");
        for (int i = 0; i < similarIngredient.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: "
                + similarIngredient.get(i).getQuantity() + "x "
                + similarIngredient.get(i).getIngredientName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if (choice >= -1 && choice <= similarIngredient.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between -1 and "
                            + similarIngredient.size() + ".");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
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
        String contextLabel = getContextLabel();
        System.out.println("Similar items found in " + contextLabel + ":");

        // Print the list of similar items with corresponding numbers
        System.out.println("Select an existing item to decrease its quantity:");
        for (int i = 0; i < similarIngredient.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: "
                + similarIngredient.get(i).getQuantity() + "x "
                + similarIngredient.get(i).getIngredientName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarIngredient.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarIngredient.size() + ".");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
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
        System.out.println("Similar items found in recipe book:");

        // Print the list of similar recipes with corresponding numbers
        for (int i = 0; i < similarRecipe.size(); i++) {
            System.out.println((i + 1) + ": " + similarRecipe.get(i).getRecipeName());
        }

        System.out.println("Type '0' to add this as a new recipe.");
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if (choice == -1 || choice == 0) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter 0 or -1.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
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
        System.out.println("Similar items found in recipe book:");

        // Print the list of similar recipes with corresponding numbers
        for (int i = 0; i < similarRecipe.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to delete: " + similarRecipe.get(i).getRecipeName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarRecipe.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarRecipe.size() + ".");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }


    public static int getUserChoiceForEditIngredient(ArrayList<Ingredient> similarIngredient, Ingredient newItem) {
        String contextLabel = getContextLabel();
        System.out.println("Similar items found in " + contextLabel + ":");

        // Print the list of similar items with corresponding numbers
        System.out.println("Select an existing item to update its quantity:");
        for (int i = 0; i < similarIngredient.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: "
                + similarIngredient.get(i).getQuantity() + "x "
                + similarIngredient.get(i).getIngredientName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarIngredient.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarIngredient.size() + ".");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }

        }
    }
    private static int parseQuantity(String quantityStr) {
        // Regex only accepts optional negative sign, followed by digits (no plus sign, letters, or whitespace)
        if (!quantityStr.matches("^-?\\d+$")) {
            throw new IllegalArgumentException("Quantity must be a valid integer within specified range " +
                "(e.g., 0, -5, 123). " + "No '+', letters, or spaces allowed.");
        }
        return Integer.parseInt(quantityStr);
    }

}
