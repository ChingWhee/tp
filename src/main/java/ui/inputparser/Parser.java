package ui.inputparser;

import commands.*;

import controller.KitchenCTRL;
import controller.ScreenState;
import model.Recipe;
import model.catalogue.Catalogue;
import model.catalogue.RecipeBook;

import java.util.ArrayList;

/**
 * The {@code Parser} class is responsible for interpreting user input and
 * returning the appropriate {@code Command} object based on the current screen context.
 * It delegates command creation logic depending on whether the user is in the WELCOME,
 * INVENTORY, SHOPPING, or RECIPE screen.
 */
public class Parser {
    /**
     * Parses a user input string and returns the corresponding Command.
     *
     * @param screen The current screen state (WELCOME, INVENTORY, SHOPPING, RECIPE).
     * @param userInput The full input entered by the user.
     * @return A Command object representing the user's action.
     * @throws IllegalArgumentException If the command is unrecognized or incorrectly formatted.
     */
    public Command parseCommand(ScreenState screen, String userInput) {
        String[] commands = userInput.split(" ", 2);

        String command = commands[0].toLowerCase();
        String args = (commands.length > 1) ? commands[1] : "";

        return switch (screen) {
        case WELCOME -> parseWelcomeCommand(command);
        case INVENTORY -> parseInventoryCommand(screen, command, args);
        // case SHOPPING -> parseShoppingCommand(screen, command, args);
        case RECIPE -> parseRecipeCommand(screen, command, args);
        default -> throw new IllegalArgumentException("Unrecognized screen state.");
        };
    }

    /**
     * Parses commands specific to the welcome screen.
     *
     * @param command The parsed command keyword.
     * @return A Command object corresponding to the user input.
     * @throws IllegalArgumentException If the command is unknown.
     */
    private Command parseWelcomeCommand(String command) {
        return switch (command) {
        case "inventory" -> prepareGoto(ScreenState.INVENTORY);
        // case "shopping" -> prepareGoto(ScreenState.SHOPPING);
        case "recipe" -> prepareGoto(ScreenState.RECIPE);
        case "bye" -> prepareBye();
        default -> throw new IllegalArgumentException("Unknown command in welcome screen.");
        };
    }

    /**
     * Parses commands specific to the inventory screen.
     *
     * @param screen  The current screen state (INVENTORY).
     * @param command The command keyword (add, delete, list, back).
     * @param args Arguments passed with the command.
     * @return A Command object for the inventory action.
     * @throws IllegalArgumentException If the command is unknown or malformed.
     */
    private Command parseInventoryCommand(ScreenState screen, String command, String args) {
        return switch (command) {
        case "add" -> prepareAdd(screen, args);
        case "delete" -> prepareDelete(screen, args);
        case "list" -> prepareList(screen);
        case "back" -> prepareBack();
        case "bye" -> prepareBye();
        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
        };
    }

    //    /**
    //     * Parses commands specific to the shopping screen.
    //     *
    //     * @param screen  The current screen state (SHOPPING).
    //     * @param command The command keyword (add, delete, list, back).
    //     * @param args Arguments passed with the command.
    //     * @return A Command object for the shopping action.
    //     * @throws IllegalArgumentException If the command is unknown or malformed.
    //     */
    //    private Command parseShoppingCommand(ScreenState screen, String command, String args) {
    //        return switch (command) {
    //        case "add" -> prepareAdd(screen, args);
    //        case "delete" -> prepareDelete(screen, args);
    //        case "list" -> prepareList(screen);
    //        case "back" -> prepareBack();
    //        case "bye" -> prepareBye();
    //        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
    //        };
    //    }

    /**
     * Parses commands specific to the recipe screen.
     *
     * @param screen  The current screen state (RECIPE).
     * @param command The command keyword (add, delete, list, back).
     * @param args Arguments passed with the command.
     * @return A Command object for the recipe action.
     * @throws IllegalArgumentException If the command is unknown or malformed.
     */
    private Command parseRecipeCommand(ScreenState screen, String command, String args) {
        return switch (command) {
        case "add" -> prepareAdd(screen, args);
        case "delete" -> prepareDelete(screen, args);
        case "list" -> prepareList(screen);
        case "back" -> prepareBack();
        case "bye" -> new ByeCommand();
        case "cook" -> prepareCook(screen, args);
        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
        };
    }

    /**
     * Parses arguments to create an {@code AddCommand}.
     *
     * @param screen The screen context to determine target catalogue.
     * @param args Input arguments in the format: &lt;name&gt; &lt;quantity&gt;
     * @return An AddCommand with the given name and quantity.
     * @throws IllegalArgumentException If the input format is invalid or quantity is not a number.
     */
    private Command prepareAdd(ScreenState screen, String args) {
        String[] parts = args.split(" ", 2); // Expecting format: "name quantity"

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid format! Usage: add <name> <quantity>");
        }

        String name = parts[0].trim();
        int quantity;

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid integer!");
        }

        return new AddCommand(screen, name, quantity);
    }

    /**
     * Parses arguments to create a {@code DeleteCommand}.
     *
     * @param screen The screen context to determine target catalogue.
     * @param args Input arguments in the format: &lt;name&gt; &lt;quantity&gt;
     * @return A DeleteCommand with the given name and quantity.
     * @throws IllegalArgumentException If the input format is invalid or quantity is not a number.
     */
    private Command prepareDelete(ScreenState screen, String args) {
        String[] parts = args.split(" ", 2); // Expecting format: "name quantity"

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid format! Usage: delete <name> <quantity>");
        }

        String name = parts[0].trim();
        int quantity;

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid integer!");
        }

        return new DeleteCommand(screen, name, quantity);
    }

    /**
     * Creates a {@code ListCommand} for listing contents of the current catalogue.
     *
     * @param screen The screen context.
     * @return A {@code ListCommand} for the appropriate catalogue.
     */
    private Command prepareList(ScreenState screen) {
        return new ListCommand(screen);
    }

    /**
     * Creates a {@code BackCommand} to return to the WELCOME screen.
     *
     * @return A new {@code BackCommand}.
     */
    private Command prepareBack() {
        return new BackCommand();
    }

    /**
     * Creates a {@code GoToCommand} to switch to the given screen.
     *
     * @param screen The target screen to switch to.
     * @return A {@code GoToCommand} that navigates to the selected screen.
     */
    private Command prepareGoto(ScreenState screen) {
        return new GoToCommand(screen);
    }

    /**
     * Creates a {@code ByeCommand} to terminate the program.
     *
     * @return A new {@code ByeCommand}.
     */
    private Command prepareBye() {
        return new ByeCommand();
    }

    private Command prepareCook(ScreenState screen, String args) {
        //expected args format is name of recipe
        RecipeBook recipeBook = KitchenCTRL.getRecipeBook();

        Recipe targetRecipe;

        try {
            String targetRecipeName = args.trim();
            targetRecipe = recipeBook.getItemByName(targetRecipeName);
            if (targetRecipe == null) {
                throw new IllegalArgumentException("Recipe not found!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Log the error (optional)
            return null; // Handle error gracefully
        }

        return new cookRecipeCommand(screen, targetRecipe);
    }
}
