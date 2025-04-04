package commands;

import controller.ScreenState;
import ui.inputparser.Ui;

public class ListCommandsCommand extends Command {
    public ListCommandsCommand(ScreenState screen) {
        super(screen);
    }

    public CommandResult execute() {
        assert screen != null;
        switch (screen) {
        case WELCOME -> Ui.showWelcomeCommands();
        case INVENTORY -> Ui.showInventoryCommands();
        case RECIPE -> Ui.showRecipeCommands();
        case RECIPEBOOK -> Ui.showRecipeBookCommands();
        }
        return new CommandResult("");
    }
}
