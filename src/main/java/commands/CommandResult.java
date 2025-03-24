package commands;

import controller.ScreenState;

/**
 * Represents the result of a command execution, which contains feedback
 * that can be shown to the user.
 */
public class CommandResult {
    /** The feedback message to be shown to the user. */
    private final String feedbackToUser;
    /** The new screen to switch to after executing the command. */
    private final ScreenState newScreen;

    /**
     * Constructs a {@code CommandResult} with the given feedback message.
     * No screen transition is triggered.
     *
     * @param feedbackToUser the feedback message to be shown to the user
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null);
    }

    /**
     * Constructs a {@code CommandResult} that only specifies a screen to transition to.
     * No message will be shown to the user.
     *
     * @param newScreen The screen to switch to.
     */
    public CommandResult(ScreenState newScreen) {
        this(null, newScreen);
    }

    /**
     * Constructs a CommandResult with full control.
     *
     * @param feedbackToUser the feedback message to be shown to the user
     * @param newScreen the screen to switch to, or null if no screen change
     */
    public CommandResult(String feedbackToUser, ScreenState newScreen) {
        this.feedbackToUser = feedbackToUser;
        this.newScreen = newScreen;
    }

    /**
     * Returns the user-facing feedback message.
     *
     * @return The feedback string
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns the screen to switch to
     *
     * @return The {@code ScreenState} to switch to
     */
    public ScreenState getNewScreen() {
        return newScreen;
    }

    /**
     * Returns {@code true} if this result includes a screen transition.
     *
     * @return {@code true} if a new screen is specified; {@code false} otherwise.
     */
    public boolean isScreenSwitch() {
        return newScreen != null;
    }
}
