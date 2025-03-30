package ui.inputparser;

import commands.ByeCommand;
import commands.AddCommand;
import commands.CookRecipeCommand;
import commands.DeleteCommand;
import commands.BackCommand;
import commands.Command;
import commands.ListCommand;
import commands.GoToCommand;

import controller.KitchenCTRL;
import controller.ScreenState;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;

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
     * @param userInput The full input entered by the user.
     * @return A Command object representing the user's action.
     * @throws IllegalArgumentException If the command is unrecognized or incorrectly formatted.
     */
    public Command parseCommand(String userInput) {
        String[] commands = userInput.split(" ", 2);

        String command = commands[0].toLowerCase();
        String args = (commands.length > 1) ? commands[1] : "";

        return switch (KitchenCTRL.getCurrentScreen()) {
        case WELCOME -> parseWelcomeCommand(command);
        case INVENTORY -> parseInventoryCommand(command, args);
        case RECIPEBOOK -> parseRecipeBookCommand(command, args);
        case RECIPE -> parseRecipeCommand(command, args);
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
        case "recipe" -> prepareGoto(ScreenState.RECIPEBOOK);
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
    private Command parseInventoryCommand(String command, String args) {
        return switch (command) {
        case "add" -> prepareAdd(args);
        case "delete" -> prepareDelete(args);
        case "list" -> prepareList();
        case "back" -> prepareBack();
        case "bye" -> prepareBye();
        default -> throw new IllegalArgumentException("Unknown command in inventory screen.");
        };
    }

    /**
     * Parses commands specific to the recipe  screen.
     *
     * @param command The command keyword (add, delete, list, back).
     * @param args Arguments passed with the command.
     * @return A Command object for the recipe action.
     * @throws IllegalArgumentException If the command is unknown or malformed.
     */
    private Command parseRecipeBookCommand(String command, String args) {
        return switch (command) {
        case "add" -> prepareAdd(args);
        case "delete" -> prepareDelete(args);
        case "list" -> prepareList();
        case "back" -> prepareBack();
        case "bye" -> new ByeCommand();
        case "cook" -> prepareCook(args);
        default -> throw new IllegalArgumentException("Unknown command in RecipeBook screen.");
        };
    }

    /**
     * Parses commands specific to a selected recipe (ingredient management).
     *
     * @param command The command keyword (add, update, delete, list, back, bye).
     * @param args Arguments passed with the command.
     * @return A Command object for the recipe ingredient action.
     * @throws IllegalArgumentException If the command is unknown or malformed.
     */
    private Command parseRecipeCommand(String command, String args) {
        return switch (command) {
            case "add" -> prepareAdd(args);       // Add an ingredient to the recipe
            case "update" -> prepareDelete(args); // Update quantity of an ingredient
            case "delete" -> prepareDelete(args); // Delete an ingredient from the recipe
            case "list" -> prepareList();           // List all ingredients in the recipe
            case "back" -> prepareBack();                 // Go back to recipe book
            case "bye" -> new ByeCommand();               // Exit program
            default -> throw new IllegalArgumentException("Unknown command in recipe screen.");
        };
    }

    /**
     * Prepares an AddCommand based on the current screen.
     *
     * @param args   The arguments for the add command.
     * @return An appropriate AddCommand based on context.
     * @throws IllegalArgumentException If the arguments are invalid.
     */
    private Command prepareAdd(String args) {
        switch (KitchenCTRL.getCurrentScreen()) {
            case INVENTORY, RECIPE -> {
                // Expecting: add <ingredientName> <quantity>
                String[] parts = args.split(" ", 2);
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

                return new AddCommand(name, quantity);
            }
            case RECIPEBOOK -> {
                // Expecting: add <recipeName>
                String name = args.trim();
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Recipe name cannot be empty!");
                }

                return new AddCommand(name); // Assume constructor for recipe creation uses only name
            }
            default -> throw new IllegalArgumentException("Unsupported screen for add command.");
        }
    }


    /**
     * Parses arguments to create a {@code DeleteCommand}.
     *
     * @param args Input arguments in the format: &lt;name&gt; &lt;quantity&gt;
     * @return A DeleteCommand with the given name and quantity.
     * @throws IllegalArgumentException If the input format is invalid or quantity is not a number.
     */
    private Command prepareDelete(String args) {
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

        return new DeleteCommand(name, quantity);
    }

    /**
     * Creates a {@code ListCommand} for listing contents of the current catalogue.
     *
     * @return A {@code ListCommand} for the appropriate catalogue.
     */
    private Command prepareList() {
        return new ListCommand();
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

    private Command prepareCook(String args) {
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

        return new CookRecipeCommand(targetRecipe);
    }
}
