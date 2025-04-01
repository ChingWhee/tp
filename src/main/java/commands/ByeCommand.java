package commands;

import model.catalogue.Catalogue;
import storage.CatalogueContentManager;

import java.util.ArrayList;

import controller.KitchenCTRL;

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

        ArrayList<Catalogue<?>> catalogues = KitchenCTRL.getAllCatalogues();

        for (Catalogue<?> catalogue : catalogues) {
            contentManager.saveToFile(catalogue);
        }
    }

    /**
     * Executes the command to terminate the program.
     *
     * @return A CommandResult indicating that the program is exiting.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("Goodbye! Happy cooking! Exiting program...");
    }
}
