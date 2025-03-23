import commands.ByeCommand;
import commands.CommandResult;
import controller.ScreenState;
import logic.commands.Commands;

import commands.Command;

import model.Ingredient;
import model.Recipe;
import model.catalogue.*;

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
    private RecipeCatalogue recipeCatalogue;

    // Catalogue storing shopping list ingredients
    private ShoppingCatalogue shoppingCatalogue;

    // Manages loading and saving of catalogue data
    private CatalogueContentManager contentManager;

    private Ui ui;
    private Parser parser;
    private ScreenState currentScreen = ScreenState.WELCOME;

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
            contentManager = new CatalogueContentManager();

            this.inventoryCatalogue = contentManager.loadInventoryCatalogue();
            this.shoppingCatalogue = contentManager.loadShoppingCatalogue();
            this.recipeCatalogue = contentManager.loadRecipeCatalogue();

            ui.showInitMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Continuously reads and processes user commands until an exit command is issued.
     * Executes commands using the current state of the inventory catalogue and displays
     * results to the user.
     */
    private void runCommandLoopUntilExitCommand() {
        ScreenState currentScreen = ScreenState.WELCOME;
        Command command;

        do {
            ui.showScreenPrompt(currentScreen);

            String userCommandText = ui.getUserCommand();
            try {
                command = new Parser().parseCommand(currentScreen, userCommandText);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            };

            Catalogue<?> catalogue = getCatalogueByScreen(currentScreen);
            CommandResult result;

            if (catalogue == null) {
                result = command.execute();
            } else {
                result = command.execute(catalogue);
            }

            if (command instanceof ByeCommand) {
                break;
            }

            ui.showResultToUser(result);

            if (result.isScreenSwitch()) {
                currentScreen = result.getNewScreen();
            }
        } while (true);
    }

    /**
     * Cleans up and performs any final actions required before the program terminates.
     * (Currently a placeholder, can be expanded if needed.)
     */
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
    }

    private Catalogue<?> getCatalogueByScreen(ScreenState screen) {
        return switch (screen) {
            case INVENTORY -> inventoryCatalogue;
            case SHOPPING -> shoppingCatalogue;
            case RECIPE -> recipeCatalogue;
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
