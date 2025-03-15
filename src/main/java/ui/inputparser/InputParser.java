package ui.inputparser;

import backend.storage.Ingredient;
import backend.storage.Recipe;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles user input parsing for selecting items when adding or deleting ingredients in the inventory.
 */
public class InputParser {
    static Scanner scanner;

    /**
     * Constructs an InputParser instance with a scanner for user input.
     */
    InputParser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Asks the user what to do when adding an ingredient that has similar items in the inventory.
     *
     * @param similarItems A list of ingredients similar to the new ingredient.
     * @param newItem The ingredient the user is trying to add.
     * @return The user's choice:
     *         - 0 to add the ingredient as a new item.
     *         - A number (1 to the number of similar items) to update an existing ingredient.
     *         - -1 to cancel the action.
     */
    public static int getUserChoiceForAdd(ArrayList<Ingredient> similarItems, Ingredient newItem) {
        System.out.println("Similar items found in inventory for: " + newItem.getIngredientName());

        // Print the list of similar items with corresponding numbers
        System.out.println("Type '0' to add this as a new item.");
        System.out.println("Select an existing item to increase its quantity:");
        for (int i = 0; i < similarItems.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: " + similarItems.get(i));
        }
        System.out.println("Type '-1' to cancel this action.");
        System.out.println();

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if (choice >= -1 && choice <= similarItems.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between -1 and " + similarItems.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Asks the user what to do when deleting an ingredient that has similar items in the inventory.
     *
     * @param similarItems A list of ingredients similar to the one being deleted.
     * @param newItem The ingredient the user wants to delete.
     * @return The user's choice:
     *         - A number (1 to the number of similar items) to decrease the quantity of an existing ingredient.
     *         - -1 to cancel the action.
     */
    public static int getUserChoiceForDelete(ArrayList<Ingredient> similarItems, Ingredient newItem) {
        System.out.println("Similar items found in inventory for: " + newItem.getIngredientName());

        // Print the list of similar items with corresponding numbers
        System.out.println("Select an existing item to decrease its quantity:");
        for (int i = 0; i < similarItems.size(); i++) {
            System.out.println("Type '" + (i + 1) + "' to update: " + similarItems.get(i));
        }
        System.out.println("Type '-1' to cancel this action.");
        System.out.println();

        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                // Ensure input is within the valid range
                if ((choice >= 1 && choice <= similarItems.size()) || choice == -1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter -1 or a number between 1 and "
                            + similarItems.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
