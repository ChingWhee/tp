package commands;

/**
 * A command that signifies the termination of the program.
 */
public class ByeCommand extends Command {

    /**
     * Constructs a ByeCommand instance.
     */
    public ByeCommand() {
    }

    /**
     * Executes the command to terminate the program.
     *
     * @return A CommandResult indicating that the program is exiting.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("Goodbye! Exiting program...");
    }
}
