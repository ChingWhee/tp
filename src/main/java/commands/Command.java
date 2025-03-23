package commands;

import model.catalogue.IngredientCatalogue;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    public Command(String args) {

    }

    public Command() {

    }

    public CommandResult execute() { // Ensure this method exists
        return null;
    }

    public CommandResult execute(IngredientCatalogue inventory) {
        throw new UnsupportedOperationException("override me");
    }
}
