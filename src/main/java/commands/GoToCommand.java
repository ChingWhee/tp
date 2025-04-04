package commands;

import controller.ScreenState;
import ui.inputparser.Ui;

/**
 * Represents a command to switch from the current screen to another target {@link ScreenState}.
 * <p>
 * This is typically used from the welcome screen to navigate to:
 * <ul>
 *     <li>INVENTORY screen</li>
 *     <li>RECIPE screen</li>
 * </ul>
 */
public class GoToCommand extends Command {
    /**
     * Constructs a {@code GoToCommand} that switches the user to the specified screen.
     *
     * @param screen The screen to navigate to (e.g. INVENTORY, RECIPE).
     */
    public GoToCommand(ScreenState screen) {
        super(screen);
    }

    /**
     * Executes the command and returns a {@link CommandResult} indicating the screen to transition to.
     *
     * @return A {@code CommandResult} with the new screen to switch to.
     */
    @Override
    public CommandResult execute() {
        Ui.showScreenPrompt(screen);
        return new CommandResult(this.screen);
    }
}
