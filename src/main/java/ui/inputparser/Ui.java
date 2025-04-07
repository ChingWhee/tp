package ui.inputparser;

import commands.CommandResult;
import controller.KitchenCTRL;
import controller.ScreenState;
import model.catalogue.Recipe;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code Ui} class handles all user input and output operations.
 * It is responsible for displaying prompts, command menus, and feedback to the user,
 * as well as reading commands entered by the user from the console.
 */
public class Ui {
    // Scanner to read user input from console
    private static Scanner scanner;

    /**
     * Constructs a Ui object using the standard system input stream.
     */
    public Ui() {
        this(System.in);
    }

    /**
     * Constructs a Ui object using a specified input stream (used for testing).
     *
     * @param inStream The input stream to read user input from.
     */
    public Ui(InputStream inStream) {
        scanner = new Scanner(inStream);
    }

    /**
     * Displays the ASCII logo upon launching the program.
     */
    public void showInitMessage() {
        final String logo = """ 
            [ASCII Art Logo Content Here]
            """;
        System.out.println(logo);
        System.out.flush();
    }

    /**
     * Displays a divider line to separate UI sections.
     */
    public void showDivider() {
        System.out.println("==========================================================");
    }

    /**
     * Displays the appropriate screen prompt and commands based on the current screen state.
     *
     * @param screen The current screen state.
     */
    public static void showScreenPrompt(ScreenState screen) {
        switch (screen) {
        case WELCOME:
            showWelcomeMessage();
            break;
        case INVENTORY:
            showInventoryMessage();
            break;
        case RECIPEBOOK:
            showRecipeBookMessage();
            break;
        case RECIPE:
            showRecipeMessage();
            break;
        default:
            System.out.println("Unknown screen state");
        }
    }

    /**
     * Displays the welcome message and the list of commands on the main screen.
     */
    public static void showWelcomeMessage() {
        System.out.println("Welcome to KitchenCTRL - your digital kitchen companion!");
        System.out.println("What would you like to do today? Available commands:");
        showWelcomeCommands();
        System.out.println();
    }

    /**
     * Displays help and available commands for the INVENTORY screen.
     */
    public static void showInventoryMessage() {
        System.out.println("You're now in the INVENTORY screen.");
        System.out.println("Manage what's in your inventory! Available commands:");
        showInventoryCommands();
        System.out.println();
    }

    /**
     * Displays help and available commands for a specific RECIPE screen.
     */
    public static void showRecipeMessage() {
        Recipe activeRecipe = KitchenCTRL.getActiveRecipe();
        System.out.println("You're now viewing a specific RECIPE: " + activeRecipe.getRecipeName());
        System.out.println("Manage the ingredients for this recipe. Available commands:");
        showRecipeCommands();
        System.out.println();
    }

    /**
     * Displays help and available commands for the RECIPEBOOK screen.
     */
    public static void showRecipeBookMessage() {
        System.out.println("You're now in the RECIPEBOOK screen.");
        System.out.println("What dish would you like to make today? Available commands:");
        showRecipeBookCommands();
        System.out.println();
    }

    /**
     * Displays the list of available commands on the WELCOME screen.
     */
    public static void showWelcomeCommands() {
        System.out.println("- inventory -> View and manage your inventory");
        System.out.println("- recipe -> View and manage your recipes");
        System.out.println("- bye -> Exit the program");
        System.out.print("- help -> View available commands");
    }

    /**
     * Displays the list of available commands on the INVENTORY screen.
     */
    public static void showInventoryCommands() {
        System.out.println("- list -> Show all ingredients in inventory");
        System.out.println("- find [name] -> Find ingredient(s) in inventory");
        System.out.println("- add [qty] [item] -> Add ingredient(s) to inventory");
        System.out.println("- delete [qty] [item] -> Remove ingredient(s) from inventory");
        System.out.println("- edit [qty] [item] -> Set quantity of an ingredient");
        System.out.println("- cookable -> Find all cookable recipes");
        System.out.println("- cook [name] -> Cook a recipe or show missing ingredients");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
        System.out.print("- help -> View available commands");
    }

    /**
     * Displays the list of available commands for a specific recipe screen.
     */
    public static void showRecipeCommands() {
        System.out.println("- list -> Show all ingredients in the recipe");
        System.out.println("- find [name] -> Find ingredient(s) in the recipe");
        System.out.println("- add [qty] [item] -> Add ingredient(s) to the recipe");
        System.out.println("- delete [qty] [item] -> Remove ingredient(s) from the recipe");
        System.out.println("- edit [qty] [item] -> Set quantity of an ingredient");
        System.out.println("- cook -> Cook this recipe or show missing ingredients");
        System.out.println("- back -> Return to the recipe list");
        System.out.println("- bye -> Exit the program");
        System.out.print("- help -> View available commands");
    }

    /**
     * Displays the list of available commands on the RECIPEBOOK screen.
     */
    public static void showRecipeBookCommands() {
        System.out.println("- list -> Show all recipes");
        System.out.println("- find [name] -> Find recipe(s) by name");
        System.out.println("- add [name] -> Add a new recipe");
        System.out.println("- delete [name] -> Delete a recipe");
        System.out.println("- edit [name] -> Edit an existing recipe");
        System.out.println("- cook [name] -> Cook a recipe or view required ingredients");
        System.out.println("- cookable -> Find all cookable recipes");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
        System.out.print("- help -> View available commands");
    }

    /**
     * Displays a goodbye message before exiting the application.
     */
    public void showGoodbyeMessage() {
        System.out.println("Goodbye, see you soon!");
    }

    /**
     * Reads and returns the user's command from the console input.
     *
     * @return The raw command entered by the user, trimmed of whitespace.
     *         Returns an empty string if no input is detected.
     */
    public String getUserCommand() {
        System.out.print("Enter command: ");
        System.out.flush();
        if (!scanner.hasNextLine()) {
            System.out.println("No input detected. Exiting...");
            return "";
        }
        return scanner.nextLine().trim();
    }

    /**
     * Displays the result of a command execution to the user.
     *
     * @param result The CommandResult containing feedback to be displayed.
     */
    public void showResultToUser(CommandResult result) {
        if (result.getFeedbackToUser() != null) {
            System.out.println(result.getFeedbackToUser());
        }
    }
}
