package commands;

import controller.ScreenState;

public class BackCommand extends Command {
    public BackCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(ScreenState.WELCOME);
    }
}
