package commands;

import controller.ScreenState;

/**
 * Represents the result of a command execution, which contains feedback
 * that can be shown to the user.
 */
public class CommandResult {
    /* The feedback message to be shown to the user. */
    private final String feedbackToUser;
    private final ScreenState newScreen;

    /**
     * Constructs a {@code CommandResult} with the given feedback message.
     *
     * @param feedbackToUser the feedback message to be shown to the user
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null);
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
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns the screen to switch to, or null if no transition.
     */
    public ScreenState getNewScreen() {
        return newScreen;
    }

    /**
     * Returns true if the command result involves a screen switch.
     */
    public boolean isScreenSwitch() {
        return newScreen != null;
    }
}
