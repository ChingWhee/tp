package commands;

import controller.ScreenState;
import model.catalogue.Catalogue;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    private ScreenState screen = null;

    public Command() {

    }

    public Command(String args) {

    }

    public Command(ScreenState screen) {
        this.screen = screen;
    }

    public CommandResult execute() { // Ensure this method exists
        return null;
    }

    /**
     * Executes the command with a generic catalogue.
     * Subclasses should override and cast as needed.
     */
    public CommandResult execute(Catalogue<?> catalogue) {
        throw new UnsupportedOperationException("This command must override execute(Catalogue<?> catalogue).");
    }
}
