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
     * Prompts the user to resolve a conflict when adding a new recipe by selecting from a list of similar recipes.
     * <p>
     * Options provided to the user:
     * <ul>
     *     <li>Type <code>0</code> to add the new recipe as a separate entry.</li>
     *     <li>Type a number between <code>1</code> and <code>n</code> (where <code>n</code> is the number of similar recipes)
     *         to select an existing recipe for potential reference or editing.</li>
     *     <li>Type <code>-1</code> to cancel the operation.</li>
     * </ul>
     *
     * @param similarRecipe an {@link ArrayList} of similar {@link Recipe} objects that may already exist in the recipe book
     * @param newRecipe the new {@link Recipe} object the user is attempting to add
     * @return an integer representing the user's choice:
     *         <ul>
     *             <li><code>-1</code>: Cancel the operation</li>
     *             <li><code>0</code>: Add the recipe as a new entry</li>
     *             <li><code>1</code> to <code>n</code>: Index (1-based) of a selected existing similar recipe</li>
     *         </ul>
     */
    public static int getUserChoiceForAddRecipe(ArrayList<Recipe> similarRecipe, Recipe newRecipe) {
        System.out.println("Similar items found in recipe book:");

        // Print the list of similar recipes with corresponding numbers
        System.out.println("Type '0' to add this as a new recipe.");
        System.out.println("Select an existing recipe to reference or edit:");
        for (int i = 0; i < similarRecipe.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to choose: " + similarRecipe.get(i).getRecipeName());
        }
        System.out.println("Type '-1' to cancel this action.");

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = parseQuantity(scanner.nextLine().trim());

                // Accept -1 (cancel), 0 (add new), or a valid index
                if (choice >= -1 && choice <= similarRecipe.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between -1 and " + similarRecipe.size() + ".");
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
