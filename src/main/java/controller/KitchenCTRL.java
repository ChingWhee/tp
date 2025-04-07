package controller;

import commands.BackCommand;
import commands.ByeCommand;
import commands.CommandResult;
import commands.Command;
import commands.ListCommandsCommand;
import commands.GoToCommand;
import commands.EditRecipeCommand;

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

    /** The application's inventory catalogue. */
    private static Inventory inventory;

    /** The application's recipe book catalogue. */
    private static RecipeBook recipeBook;

    /** The current screen being displayed in the application. */
    private static ScreenState currentScreen = ScreenState.WELCOME;

    /** The currently selected active recipe. */
    private static Recipe activeRecipe;

    /** The user interface object for handling input/output. */
    private Ui ui;

    /** The parser for interpreting user commands. */
    private Parser parser;

    /**
     * Returns the current screen state of the application.
     *
     * @return The currently active {@code ScreenState}.
     * @throws IllegalStateException if the current screen is not set.
     */
    public static ScreenState getCurrentScreen() {
        if (currentScreen == null) {
            throw new IllegalStateException("Current screen is not set.");
        }
        return currentScreen;
    }

    /**
     * Sets the current screen state of the application.
     *
     * @param currentScreen The {@code ScreenState} to set as the current screen.
     * @throws IllegalArgumentException if {@code currentScreen} is null.
     */
    public static void setCurrentScreen(ScreenState currentScreen) {
        if (currentScreen == null) {
            throw new IllegalArgumentException("Cannot set screen to null");
        }
        KitchenCTRL.currentScreen = currentScreen;
    }

    /**
     * Sets the currently active recipe in the application context.
     *
     * @param recipe The {@code Recipe} to set as active.
     */
    public static void setActiveRecipe(Recipe recipe) {
        activeRecipe = recipe;
    }

    /**
     * Returns the currently active recipe.
     *
     * @return The active {@code Recipe}, or null if none is selected.
     */
    public static Recipe getActiveRecipe() {
        return activeRecipe;
    }

    /**
     * Ensures an active recipe is selected before proceeding.
     *
     * @return The currently active {@code Recipe}.
     * @throws IllegalStateException if no recipe is selected.
     */
    public static Recipe requireActiveRecipe() {
        Recipe r = activeRecipe;
        if (r == null) {
            throw new IllegalStateException("No recipe is currently selected.");
        }
        return r;
    }

    /**
     * Main entry point for the KitchenCTRL application.
     *
     * @param args Command-line arguments passed during application startup (not used).
     */
    public static void main(String[] args) {
        new KitchenCTRL().run();
    }

    /**
     * Starts the kitchen management program. Initializes components, enters the main loop,
     * and performs cleanup on exit.
     */
    public void run() {
        start();
        runCommandLoopUntilExitCommand();
        exit();
    }

    /**
     * Initializes the catalogues from persistent storage.
     * Useful for test cases.
     *
     * @throws RuntimeException if loading the catalogues fails.
     */
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
     * Initializes UI, parser, and catalogues. Loads data from persistent storage.
     *
     * @throws RuntimeException if any initialization fails.
     */
    private void start() {
        try {
            this.ui = new Ui();
            this.parser = new Parser();
            initializeCatalogues();
            ui.showInitMessage();
            Ui.showWelcomeMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Main command-processing loop that:
     * - Displays prompts
     * - Reads and parses user input
     * - Executes commands
     * - Updates screen and displays results
     * Loops until the ByeCommand is issued.
     */
    private void runCommandLoopUntilExitCommand() {
        Command command;

        do {
            String userCommandText = ui.getUserCommand();

            if (userCommandText.isEmpty()) {
                System.out.println("Please enter a command. Type `help` to see available commands.");
                ui.showDivider();
                continue;
            }

            try {
                command = parser.parseCommand(userCommandText);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                ui.showDivider();
                continue;
            }

            if (command instanceof ByeCommand) {
                break;
            }

            CommandResult result;
            if (command instanceof BackCommand || command instanceof GoToCommand ||
                    command instanceof EditRecipeCommand || command instanceof ListCommandsCommand) {
                result = command.execute();
                if (result.getNewScreen() != null) {
                    currentScreen = result.getNewScreen();
                }
                ui.showResultToUser(result);
                ui.showDivider();
                continue;
            }

            Catalogue<?> catalogue = getCatalogueByScreen(currentScreen);

            result = (catalogue == null)
                    ? command.execute()
                    : command.execute(catalogue);

            ui.showResultToUser(result);
            ui.showDivider();
        } while (true);
    }

    /**
     * Performs any necessary cleanup and displays exit message before terminating the application.
     */
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }

    /**
     * Retrieves the appropriate catalogue object based on the given screen.
     *
     * @param screen The current {@code ScreenState}.
     * @return The corresponding catalogue, or null if no catalogue applies to the screen.
     */
    public Catalogue<?> getCatalogueByScreen(ScreenState screen) {
        return switch (screen) {
            case INVENTORY -> inventory;
            case RECIPEBOOK -> recipeBook;
            case RECIPE -> activeRecipe;
            default -> null;
        };
    }

    /**
     * Returns the application-wide recipe book.
     *
     * @return The {@code RecipeBook}.
     */
    public static RecipeBook getRecipeBook() {
        return recipeBook;
    }

    /**
     * Returns the application-wide inventory.
     *
     * @return The {@code Inventory}.
     */
    public static Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns a list of all catalogues in the application.
     *
     * @return A list containing both inventory and recipe book.
     */
    public static ArrayList<Catalogue<?>> getAllCatalogues() {
        ArrayList<Catalogue<?>> catalogues = new ArrayList<>();
        catalogues.add(inventory);
        catalogues.add(recipeBook);
        return catalogues;
    }
}
