package ui.inputparser;

import commands.CommandResult;

import java.util.Scanner;

public class Ui {
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * Reads the user input command from the console.
     * @return the raw command string entered by the user.
     */
    public static String getUserCommand() {
        System.out.print("Enter command: ");
        System.out.flush();
        if (!scanner.hasNextLine()) { // Prevents NoSuchElementException
            System.out.println("No input detected. Exiting...");
            return "";  // Return empty string instead of blocking
        }
        return scanner.nextLine().trim();
    }

    /**
     * Displays the result of executing a command.
     * @param result The CommandResult containing feedback for the user.
     */
    public static void showResultToUser(CommandResult result) {
        System.out.println(result.feedbackToUser);
    }

}
