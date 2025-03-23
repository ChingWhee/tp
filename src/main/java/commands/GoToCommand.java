package commands;

import controller.ScreenState;

public class GoToCommand extends Command {
    public GoToCommand(ScreenState screen) {
        super(screen);
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(this.screen);
    }
}
