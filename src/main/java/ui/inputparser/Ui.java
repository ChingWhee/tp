package ui.inputparser;

import commands.CommandResult;

import java.util.Scanner;

public class Ui {
    private static final Scanner scanner = new Scanner(System.in);

    public void showWelcomeMessage() {
        final String LOGO = """
                   .-.    .-.    .-.    .-.  .-.  .-"-.  .-.      .--.      .-.  .--.
                  <   |  <   |  <   |   | |  | |  | | |  | |      |()|     /  |  |  |
                   )  |   )  |   )  |   | |  | |  | | |  | |      |  |     |  |  |  |
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                  <___|  <___|  <___|   |\\|  | |  | | |  | |      |  |     |  |  |__|
                   }  |   || |   =  |   | |  | |  `-|-'  | |      |  |     |  |  |   L
                   }  |   || |   =  |   | |  | |   /A\\   | |      |  |     |  |  |   J
                   }  |   || |   =  |   |/   | |   |H|   | |      |  |     |  |  |    L
                   }  |   || |   =  |        | |   |H|   | |     _|__|_    |  |  |    J
                   }  |   || |   =  |        | |   |H|   | |    | |   |    |  |  | A   L
                   }  |   || |   =  |        | |   \\V/   | |    | |   |     \\ |  | H   J
                   }  |   FF |   =  |        | |    "    | |    | \\   |      ,Y  | H A  L
                   }  |   LL |    = |       _F J_       _F J_   \\  `--|       |  | H H  J
                   }  |   LL |     \\|     /       \\   /       \\  `.___|       |  | H H A L
                   }  |   \\\\ |           J         L |  _   _  |              |  | H H U J
                   }  |    \\\\|           J         F | | | | | |             /   | U ".-'
                    } |     \\|            \\       /  | | | | | |    .-.-.-.-/    |_.-'
                     \\|                    `-._.-'   | | | | | |   ( (-(-(-( )
                                                     `-' `-' `-'    `-`-`-`-'
                """;

        System.out.println(LOGO);
        System.out.println("Welcome to KitchenCTRL!");
        System.out.flush();
    }

    public void showGoodbyeMessage() {
        System.out.println("Goodbye, see you soon!");
    }

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
