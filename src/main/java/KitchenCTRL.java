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


public class KitchenCTRL {
    private IngredientCatalogue inventoryCatalogue;
    private IngredientCatalogue shoppingCatalogue;
    private RecipeCatalogue recipeCatalogue;

    private CatalogueContentManager contentManager;

    private Ui ui;

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new KitchenCTRL().run();
    }

    /** Runs the program until termination.  */
    public void run() {
        start();
        runCommandLoopUntilExitCommand();
        exit();
    }

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
