package commands;

import controller.ScreenState;
import model.catalogue.Catalogue;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    /** The screen context this command is associated with (e.g., INVENTORY, RECIPE). */
    ScreenState screen = null;

    /**
     * Default constructor used for global commands (e.g., {@code ByeCommand}, {@code BackCommand}).
     */
    public Command() {

    }

    /**
     * Constructs a command associated with a particular screen.
     *
     * @param screen The screen context where this command is valid.
     */
    public Command(ScreenState screen) {
        this.screen = screen;
    }

    /**
     * Executes the command without requiring a catalogue.
     * Used for global or navigation-related commands.
     *
     * @return The result of the command execution as a {@code CommandResult}.
     */
    public CommandResult execute() { // Ensure this method exists
        return null;
    }

    /**
     * Executes the command using the provided {@code Catalogue}.
     * <p>
     * Subclasses that manipulate data (e.g., inventory items) should override this method.
     *
     * @param catalogue The catalogue relevant to the current screen.
     * @return The result of the command execution as a {@code CommandResult}.
     * @throws UnsupportedOperationException If the method is not overridden in the subclass.
     */
    public CommandResult execute(Catalogue<?> catalogue) {
        throw new UnsupportedOperationException("override me");
    }
}
