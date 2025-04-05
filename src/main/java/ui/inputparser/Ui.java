package ui.inputparser;

import commands.CommandResult;
import controller.KitchenCTRL;
import controller.ScreenState;
import model.catalogue.Recipe;

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
     * @param screen The current screen state (WELCOME, INVENTORY, RECIPE).
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
     * Displays the welcome message and available commands on the main screen.
     */
    public static void showWelcomeMessage() {
        System.out.println("Welcome to KitchenCTRL - your digital kitchen companion!");
        System.out.println("What would you like to do today? Available commands:");
        showWelcomeCommands();
    }

    /**
     * Displays help and commands for the INVENTORY screen.
     */
    public static void showInventoryMessage() {
        System.out.println("You're now in the INVENTORY screen.");
        System.out.println("Manage what's in your inventory! Available commands:");
        showInventoryCommands();
    }

    /**
     * Displays help and commands for managing a specific recipe's ingredients.
     */
    public static void showRecipeMessage() {
        Recipe activeRecipe = KitchenCTRL.getActiveRecipe();
        System.out.println("You're now viewing a specific RECIPE: " + activeRecipe.getRecipeName());
        System.out.println("Manage the ingredients for this recipe. Available commands:");
        showRecipeCommands();
    }

    /**
     * Displays help and commands for the RECIPE screen.
     */
    public static void showRecipeBookMessage() {
        System.out.println("You're now in the RECIPEBOOK screen.");
        System.out.println("What dish would you like to make today? Available commands:");
        showRecipeBookCommands();
    }

    public static void showWelcomeCommands() {
        System.out.println("- inventory -> View and manage your inventory");
        System.out.println("- recipe -> View and manage your recipes");
        System.out.println("- bye -> Exit the program");
        System.out.println("- help -> View available commands");
    }

    public static void showInventoryCommands() {
        System.out.println("- list -> Show all ingredients in inventory");
        System.out.println("- find [name] -> Find ingredient(s) in inventory");
        System.out.println("- add [item] [qty] -> Add ingredient(s) to inventory");
        System.out.println("- delete [item] [qty] -> Remove ingredient(s) from inventory based on qty specified");
        //for removing used ingredients manually or wrongly named ingredients
        System.out.println("- edit [item] [qty] -> Set qty of specified ingredient in inventory");
        //directly set qty of specified ingredient
        System.out.println("- cookable -> Find all cookable recipes");
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
        System.out.println("- help -> View available commands");
    }

    public static void showRecipeCommands() {
        System.out.println("- list -> Show all ingredients in the recipe");
        System.out.println("- find [name] -> Find ingredient(s) in the recipe");
        System.out.println("- add [item] [qty] -> Add ingredient(s) to the recipe");
        System.out.println("- delete [item] [qty] -> Remove ingredient(s) from recipe based on qty specified");
        //for removing used ingredients manually or wrongly named ingredients
        System.out.println("- edit [item] [qty] -> Set qty of specified ingredient in recipe");
        //directly set qty of specified ingredient
        System.out.println("- back -> Return to the recipe list");
        System.out.println("- bye -> Exit the program");
        System.out.println("- help -> View available commands");
    }

    public static void showRecipeBookCommands() {
        System.out.println("- list -> Show all recipes");
        System.out.println("- find [name] -> Find recipe(s) matching search string");
        System.out.println("- add [name] -> Add a new recipe");
        System.out.println("- delete [name] -> Delete an existing recipe specified by [name]");
        System.out.println("- edit [name] -> edit an existing recipe specified by [name]");
        System.out.println("- cook [name] -> Cook a recipe, or display missing ingredients required to cook it");
        //user cooks the recipe, prints out list of items required, and system automatically removes
        //to view again, view from recipe tab
        System.out.println("- back -> Return to the main screen");
        System.out.println("- bye -> Exit the program");
        System.out.println("- help -> View available commands");
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
