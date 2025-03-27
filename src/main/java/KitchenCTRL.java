import commands.GoToCommand;
import commands.BackCommand;
import commands.ByeCommand;
import commands.Command;
import commands.CommandResult;
import controller.ScreenState;
import logic.commands.Commands;

import model.Ingredient;
import model.Recipe;
import model.catalogue.*;
import model.catalogue.RecipeBook;

import storage.CatalogueContentManager;

import ui.inputparser.Parser;
import ui.inputparser.Ui;

/**
 * The {@code KitchenCTRL} class serves as the main controller for the kitchen management application.
 * It initializes the catalogues, manages user interaction through the UI, and handles command execution
 * using a command loop.
 */
public class KitchenCTRL {
    // Catalogue storing ingredients in the inventory
    private InventoryCatalogue inventoryCatalogue;

    // Catalogue storing recipes
    private RecipeBook recipeBook;

    // Catalogue storing shopping list ingredients
    private ShoppingCatalogue shoppingCatalogue;

    private Ui ui;
    private Parser parser;

    /**
     * Main entry-point for the KitchenCTRL application.
     *
     * @param args Command-line arguments passed during application startup (not used).
     */
    public static void main(String[] args) {
        new KitchenCTRL().run();
    }

    /**
     * Runs the kitchen management program, initializing components and processing commands
     * until the user exits.
     */
    public void run() {
        start();
        runCommandLoopUntilExitCommand();
        exit();
    }

    /**
     * Initializes the application components such as UI, catalogues, and data manager.
     * Loads data from persistent storage into respective catalogues.
     *
     * @throws RuntimeException if there is an error during initialization or loading data.
     */
    private void start() {
        try {
            // Initialization
            this.ui = new Ui();

            parser = new Parser();
            // Manages loading and saving of catalogue data
            CatalogueContentManager contentManager = new CatalogueContentManager();

            this.inventoryCatalogue = contentManager.loadInventoryCatalogue();
            this.shoppingCatalogue = contentManager.loadShoppingCatalogue();
            this.recipeBook = contentManager.loadRecipeBook();

            ui.showInitMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The main loop that:
     * - Displays the appropriate prompt
     * - Reads and parses user commands
     * - Executes commands against the correct catalogue
     * - Displays results
     * - Handles screen transitions and exit condition
     */
    private void runCommandLoopUntilExitCommand() {
        ScreenState currentScreen = ScreenState.WELCOME;
        Command command;

        do {
            // Show prompt based on current screen
            ui.showScreenPrompt(currentScreen);

            // Read user input
            String userCommandText = ui.getUserCommand();

            // Parse input into a Command
            try {
                command = parser.parseCommand(currentScreen, userCommandText);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Exit if it's a ByeCommand
            if (command instanceof ByeCommand) {
                break;
            }

            CommandResult result;
            // Switch screen if required by result
            if (command instanceof BackCommand || command instanceof GoToCommand) {
                result = command.execute();
                currentScreen = result.getNewScreen();
                ui.showDivider();
                continue;
            }

            // Get the relevant catalogue for the current screen
            Catalogue<?> catalogue = getCatalogueByScreen(currentScreen);

            // Execute the command and get result
            result = (catalogue == null)
                    ? command.execute() // e.g., welcome screen or global commands
                    : command.execute(catalogue); // inventory/shopping/recipe screens

            // Display result to the user
            ui.showResultToUser(result);
            ui.showDivider();
        } while (true);
    }

    /**
     * Cleans up and performs any final actions required before the program terminates.
     */
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }

    /**
     * Returns the appropriate catalogue based on the current screen.
     *
     * @param screen The current screen state.
     * @return The corresponding catalogue, or null if screen has no catalogue (e.g., WELCOME).
     */
    private Catalogue<?> getCatalogueByScreen(ScreenState screen) {
        return switch (screen) {
        case INVENTORY -> inventoryCatalogue;
        case SHOPPING -> shoppingCatalogue;
        case RECIPE -> recipeBook;
        default -> null; // For WELCOME, or throw if needed
        };
    }

    public static void carltonTest() {
        IngredientCatalogue inventory = new IngredientCatalogue();
        inventory.addItem(new Ingredient("White sugar", 50));
        inventory.addItem(new Ingredient("Egg", 3));
        inventory.addItem(new Ingredient("Flour", 50));

        Recipe recipe = new Recipe();
        recipe.addItem(new Ingredient("White sugar", 20));
        recipe.addItem(new Ingredient("Egg", 1));
        recipe.addItem(new Ingredient("Flour", 10));


        System.out.println(recipe);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("PRINTING MISSING");
        Commands.cookRecipe(inventory, recipe);
        inventory.listItems();
    }
}
