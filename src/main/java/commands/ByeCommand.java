package commands;

public class ByeCommand extends Command {

    public ByeCommand() {
    }

    @Override
    public CommandResult execute() {
        return new CommandResult("Goodbye! Exiting program...");
    }
}
