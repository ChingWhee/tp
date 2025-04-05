package commands;

import controller.ScreenState;
import ui.inputparser.Ui;

public class ListCommandsCommand extends Command {
    public ListCommandsCommand(ScreenState screen) {
        super(screen);
    }

    @Override
    public CommandResult execute() {
        if (screen == null) {
            return new CommandResult("Error: Empty input detected. Please input 'help' for available commands.", null);
        }
        switch (screen) {
        case WELCOME -> Ui.showWelcomeCommands();
        case INVENTORY -> Ui.showInventoryCommands();
        case RECIPE -> Ui.showRecipeCommands();
        case RECIPEBOOK -> Ui.showRecipeBookCommands();
        default -> {
            return new CommandResult("Invalid screen", null);
        }
        }
        return new CommandResult("");
    }
}
