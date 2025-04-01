package ui.inputparser;

import commands.CommandResult;
import controller.ScreenState;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code Ui} class handles all input/output interactions with the user.
 * It displays screen prompts, help menus, command results, and reads user input.
 */
public class Ui {
    // Scanner to read user input from console
    private static Scanner scanner;

    // Production constructor
    public Ui() {
        this(System.in);
    }

    // Testable constructor
    public Ui(InputStream inStream) {
        scanner = new Scanner(inStream);
    }

    /**
     * Displays the ASCII logo upon program launch.
     */
    public void showInitMessage() {
        final String logo = """
                   .-.    .-.    .-.    .-.  .-.  .-"-.  .-.      .--.      .-.  .--.
                  <   |  <   |  <   |   | |  | |  | | |  | |      |()|     /  |  |  |
                   )  |   )  |   )  |   | |  | |  | | |  | |      |  |     |  |  |  |
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                  <___|  <___|  <___|   |\\|  | |  | | |  | |      |  |     |  |  |__|
                   }  |   || |   =  |   | |  | |  `-|-'  | |      |  |     |  |  |   L
                   }  |   || |   =  |   | |  | |   /A\\   | |      |  |     |  |  |   J
                   }  |   || |   =  |   |/   | |   |H|   | |      |  |     |  |  |    L
                   }  |   || |   =  |        | |   |H|   | |     _|__|_    |  |  |    J
                   }  |   || |   =  |        | |   |H|   | |    | |   |    |  |  | A   L
                   }  |   || |   =  |        | |   \\V/   | |    | |   |     \\ |  | H   J
                   }  |   FF |   =  |        | |    "    | |    | \\   |      ,Y  | H A  L
                   }  |   LL |    = |       _F J_       _F J_   \\  `--|       |  | H H  J
                   }  |   LL |     \\|     /       \\   /       \\  `.___|       |  | H H A L
                   }  |   \\\\ |           J         L |  _   _  |              |  | H H U J
                   }  |    \\\\|           J         F | | | | | |             /   | U ".-'
                    } |     \\|            \\       /  | | | | | |    .-.-.-.-/    |_.-'
                     \\|                    `-._.-'   | | | | | |   ( (-(-(-( )
                                                     `-' `-' `-'    `-`-`-`-'
                """;

        System.out.println(logo);
        System.out.flush();
    }

    /**
     * Displays a divider line used to separate sections in the UI.
     */
    public void showDivider() {
        System.out.println("==========================================================");
    }

    /**
     * Displays the appropriate screen prompt based on the current screen state.
     *
     * @param screen The current screen state (WELCOME, INVENTORY, SHOPPING, RECIPE).
     */
    public void showScreenPrompt(ScreenState screen) {
        switch (screen) {
        case WELCOME:
            showWelcomeMessage();
            break;
        case INVENTORY:
            showInventoryMessage();
            break;
        case SHOPPING:
            showShoppingMessage();
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
     * Displays the welcome message and available commands on the main screen.
     */
    private void showWelcomeMessage() {
        System.out.println("Welcome to KitchenCTRL — your digital kitchen companion!");
        System.out.println("What would you like to do today? Available commands:");
        System.out.println("- inventory -> View and manage your inventory");
        System.out.println("- recipe -> View and manage your recipes");
        // System.out.println("- shopping -> View and manage your shopping list");
        System.out.println("- bye -> Exit the program");
    }

    /**
     * Displays help and commands for the INVENTORY screen.
     */
    private void showInventoryMessage() {
        System.out.println("You're now in the INVENTORY screen.");
        System.out.println("Manage what’s in your inventory! Available commands:");
        System.out.println("- list -> Show all ingredients in inventory");
        System.out.println("- add [item] [qty] -> Add ingredients to inventory");
        System.out.println("- delete [item] [qty] -> Remove ingredients from inventory");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
    }

    /**
     * Displays help and commands for the SHOPPING screen.
     */
    private void showShoppingMessage() {
        System.out.println("You’re now in the SHOPPING LIST screen.");
        System.out.println("Time to get those groceries! Available commands:");
        System.out.println("- list -> Show all ingredients in shopping list");
        System.out.println("- add [item] [qty] -> Add ingredients to shopping list");
        System.out.println("- delete [item] [qty] -> Remove ingredients from shopping list");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
    }

    /**
     * Displays help and commands for managing a specific recipe's ingredients.
     */
    private void showRecipeMessage() {
        System.out.println("You’re now viewing a specific RECIPE.");
        System.out.println("Manage the ingredients for this recipe. Available commands:");
        System.out.println("- list -> Show all ingredients in the recipe");
        System.out.println("- add [item] [qty] -> Add an ingredient to the recipe");
        System.out.println("- update [item] [qty] -> Update the quantity of an existing ingredient");
        System.out.println("- delete [item] -> Remove an ingredient from the recipe");
        System.out.println("- back -> Return to the recipe list");
        System.out.println("- bye -> Exit the program");
    }

    /**
     * Displays help and commands for the RECIPE screen.
     */
    private void showRecipeBookMessage() {
        System.out.println("You’re now in the RECIPEBOOK screen.");
        System.out.println("What dish would you like to make today? Available commands:");
        System.out.println("- list -> Show all recipes");
        System.out.println("- add [name] -> Add a new recipe");
        System.out.println("- delete [name] -> Delete a recipe");
        System.out.println("- edit [name] -> edit a recipe");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
    }

    /**
     * Displays a goodbye message when exiting the application.
     */
    public void showGoodbyeMessage() {
        System.out.println("Goodbye, see you soon!");
    }

    /**
     * Reads the user input command from the console.
     * @return the raw command string entered by the user.
     */
    public String getUserCommand() {
        System.out.print("Enter command: ");
        System.out.flush();
        if (!scanner.hasNextLine()) { // Prevents NoSuchElementException
            System.out.println("No input detected. Exiting...");
            return "";  // Return empty string instead of blocking
        }
        return scanner.nextLine().trim();
    }

    /**
     * Displays the result of executing a command.
     * @param result The CommandResult containing feedback for the user.
     */
    public void showResultToUser(CommandResult result) {
        if (result.getFeedbackToUser() != null) {
            System.out.println(result.getFeedbackToUser());
        }
    }
}
