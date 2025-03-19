package commands;

/**
 * Represents the result of a command execution, which contains feedback
 * that can be shown to the user.
 */
public class CommandResult {

    /**
     * The feedback message to be shown to the user.
     */
    public final String feedbackToUser;

    /**
     * Constructs a {@code CommandResult} with the given feedback message.
     *
     * @param feedbackToUser the feedback message to be shown to the user
     */
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }
}
