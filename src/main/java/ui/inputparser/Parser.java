package ui.inputparser;

import commands.Command;
import commands.AddCommand;
import commands.BackCommand;
import commands.DeleteCommand;
import commands.ListCommand;
import commands.ByeCommand;
import commands.GoToCommand;

import controller.ScreenState;

/**
 * Parses user input and returns the appropriate Command based on the current screen state.
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
        case SHOPPING -> parseShoppingCommand(screen, command, args);
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
        case "shopping" -> prepareGoto(ScreenState.SHOPPING);
        case "recipe" -> prepareGoto(ScreenState.RECIPE);
        case "bye" -> prepareBye();
        default -> throw new IllegalArgumentException("Unknown command in welcome screen.");
        };
    }

    /**
     * Parses commands specific to the inventory screen.
     *
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

    /**
     * Parses commands specific to the shopping screen.
     *
     * @param command The command keyword (add, delete, list, back).
     * @param args Arguments passed with the command.
     * @return A Command object for the shopping action.
     * @throws IllegalArgumentException If the command is unknown or malformed.
     */
    private Command parseShoppingCommand(ScreenState screen, String command, String args) {
        return switch (command) {
        case "add" -> prepareAdd(screen, args);
        case "delete" -> prepareDelete(screen, args);
        case "list" -> prepareList(screen);
        case "back" -> prepareBack();
        case "bye" -> prepareBye();
        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
        };
    }

    /**
     * Parses commands specific to the recipe screen.
     *
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
        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
        };
    }

    /**
     * Prepares an AddCommand by parsing name and quantity from input.
     *
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
     * Prepares a DeleteCommand by parsing name and quantity from input.
     *
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
     * Prepares a ListCommand to list catalogue contents.
     *
     * @return A new ListCommand.
     */
    private Command prepareList(ScreenState screen) {
        return new ListCommand(screen);
    }

    /**
     * Prepares a BackCommand to return to the welcome screen.
     *
     * @return A new BackCommand.
     */
    private Command prepareBack() {
        return new BackCommand();
    }

    private Command prepareGoto(ScreenState screen) {
        return new GoToCommand(screen);
    }

    /**
     * Prepares a ByeCommand to exit the program.
     *
     * @return A new ByeCommand.
     */
    private Command prepareBye() {
        return new ByeCommand();
    }
}



