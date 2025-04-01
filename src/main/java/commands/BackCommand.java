package commands;

import controller.KitchenCTRL;
import controller.ScreenState;

/**
 * Represents a command to return to the main (WELCOME) screen from any other screen.
 */
public class BackCommand extends Command {
    /**
     * Constructs a {@code BackCommand}.
     * This command is typically used to navigate back to the WELCOME screen.
     */
    public BackCommand() {}

    /**
     * Executes the back navigation command.
     *
     * @return A {@code CommandResult} containing the target screen {@code ScreenState.WELCOME}.
     */
    @Override
    public CommandResult execute() {
        ScreenState current = KitchenCTRL.getCurrentScreen();

        if (current == ScreenState.RECIPE) {
            KitchenCTRL.setActiveRecipe(null); // clear context
            return new CommandResult(ScreenState.RECIPEBOOK);
        }
        return new CommandResult(ScreenState.WELCOME);
    }
}
