package commands;

import storage.CatalogueContentManager;

/**
 * A command that signifies the termination of the program.
 */
public class ByeCommand extends Command {
    /**
     * Constructs a ByeCommand instance.
     */
    public ByeCommand() {
        // If user terminates the program gracefully, the program will save everything.
        CatalogueContentManager contentManager = new CatalogueContentManager();
        contentManager.saveToFile("InventoryCatalogue");
        contentManager.saveToFile("ShoppingCatalogue");
        contentManager.saveToFile("RecipeBook");
    }

    /**
     * Executes the command to terminate the program.
     *
     * @return A CommandResult indicating that the program is exiting.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("Goodbye! Exiting program...");
    }
}
