package controller;

import commands.BackCommand;
import commands.ByeCommand;
import commands.Command;
import commands.CommandResult;
import commands.EditRecipeCommand;
import commands.GoToCommand;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;
import model.catalogue.RecipeBook;
import model.catalogue.Inventory;

import ui.inputparser.Parser;
import ui.inputparser.Ui;

import storage.CatalogueContentManager;

import java.util.ArrayList;

/**
 * The {@code KitchenCTRL} class serves as the main controller for the kitchen management application.
 * It initializes the catalogues, manages user interaction through the UI, and handles command execution
 * using a command loop.
 */
public class KitchenCTRL {
    // Catalogue storing ingredients in the inventory
    private static Inventory inventory;

    // Catalogue storing recipes
    private static RecipeBook recipeBook;

    private static ScreenState currentScreen = ScreenState.WELCOME;

    private static Recipe activeRecipe;

    private Ui ui;
    private Parser parser;

    public static ScreenState getCurrentScreen() {
        return currentScreen;
    }

    public static void setCurrentScreen(ScreenState currentScreen) {
        KitchenCTRL.currentScreen = currentScreen;
    }

    public static void setActiveRecipe(Recipe recipe) {
        activeRecipe = recipe;
    }

    public Recipe getActiveRecipe() {
        return activeRecipe;
    }

    public static Recipe requireActiveRecipe() {
        Recipe r = activeRecipe;
        if (r == null) {
            throw new IllegalStateException("No recipe is currently selected.");
        }
        return r;
    }

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

    //for test cases
    public void initializeCatalogues() {
        try {
            CatalogueContentManager contentManager = new CatalogueContentManager();
            inventory = contentManager.loadInventory();
            recipeBook = contentManager.loadRecipeBook();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing catalogues", e);
        }
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
            this.parser = new Parser();
            initializeCatalogues();
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
        Command command;

        do {
            // Show prompt based on current screen
            ui.showScreenPrompt(currentScreen);

            // Read user input
            String userCommandText = ui.getUserCommand();

            // Parse input into a Command
            try {
                command = parser.parseCommand(userCommandText);
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
            if (command instanceof BackCommand || command instanceof GoToCommand ||
                    command instanceof EditRecipeCommand) {
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
                    : command.execute(catalogue); // inventory/active recipe

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
        case INVENTORY -> inventory;
        case RECIPEBOOK -> recipeBook;
        case RECIPE -> activeRecipe;
        default -> null; // For WELCOME, or throw if needed
        };
    }

    public static RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static ArrayList<Catalogue<?>> getAllCatalogues() {
        ArrayList<Catalogue<?>> catalogues = new ArrayList<>();
        catalogues.add(inventory);
        catalogues.add(recipeBook);
        return catalogues;
    }
}
