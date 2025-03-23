import logic.commands.Commands;

import commands.ByeCommand;
import commands.Command;
import commands.CommandResult;

import model.Ingredient;
import model.Recipe;
import model.catalogue.IngredientCatalogue;
import model.catalogue.RecipeCatalogue;

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
    private IngredientCatalogue inventoryCatalogue;

    // Catalogue storing recipes
    private RecipeCatalogue recipeCatalogue;

    // Catalogue storing shopping list ingredients
    private IngredientCatalogue shoppingCatalogue;
    private RecipeCatalogue recipeCatalogue;

    // Manages loading and saving of catalogue data
    private CatalogueContentManager contentManager;

    // User interface for interaction with the user
    private Ui ui;

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
            ui.showWelcomeMessage();

            contentManager = new CatalogueContentManager();

            this.inventoryCatalogue = contentManager.loadIngredientCatalogue();
            this.shoppingCatalogue = contentManager.loadShoppingCatalogue();
            this.recipeCatalogue = contentManager.loadRecipeCatalogue();
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
        Command command;
        boolean done = false;
        do {
            String userCommandText = Ui.getUserCommand();

            try {
                command = new Parser().parseCommand(userCommandText);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Only have inventory catalogue for now
            if (command instanceof ByeCommand) {
                CommandResult result = command.execute();
                done = true;
            } else {
                CommandResult result = command.execute(inventoryCatalogue);
                Ui.showResultToUser(result);
                contentManager.saveInventoryCatalogue(inventoryCatalogue.getCatalogueContent());
            }
        } while (!done);
    }

    /**
     * Cleans up and performs any final actions required before the program terminates.
     * (Currently a placeholder, can be expanded if needed.)
     */
    private void exit() {
        ui.showGoodbyeMessage();
        System.exit(0);
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
