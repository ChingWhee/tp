package ui.inputparser;

import backend.storage.Ingredient;

import java.util.ArrayList;
import java.util.Scanner;

public class InputParser {
    static Scanner scanner;

    InputParser() {
        scanner = new Scanner(System.in);
    }

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
