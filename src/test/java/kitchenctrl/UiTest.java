package kitchenctrl;

import commands.CommandResult;
import controller.ScreenState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.inputparser.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    private Ui ui;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ui = new Ui(); // static scanner is fine, as we only override System.in before calling getUserCommand()
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testShowInitMessage() {
        ui.showInitMessage();
        String output = outContent.toString();
        assertTrue(output.contains("""
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
                """));
    }

    @Test
    public void testShowDivider() {
        ui.showDivider();
        String output = outContent.toString();
        assertTrue(output.contains("=========================================================="));
    }

    @Test
    public void testShowScreenPromptWelcome() {
        ui.showScreenPrompt(ScreenState.WELCOME);
        String output = outContent.toString();

        assertTrue(output.contains("Welcome to KitchenCTRL — your digital kitchen companion!"));
        assertTrue(output.contains("- inventory → View and manage your inventory"));
        assertTrue(output.contains("- recipe → View and manage your recipes"));
        assertTrue(output.contains("- bye → Exit the program"));
    }

    @Test
    public void testShowScreenPromptInventory() {
        ui.showScreenPrompt(ScreenState.INVENTORY);
        String output = outContent.toString();

        assertTrue(output.contains("You're now in the INVENTORY screen."));
        assertTrue(output.contains("- list → Show all ingredients in inventory"));
        assertTrue(output.contains("- add [item] [qty] → Add ingredients to inventory"));
        assertTrue(output.contains("- delete [item] [qty] → Remove ingredients from inventory"));
        assertTrue(output.contains("- back → Return to the main screen"));
        assertTrue(output.contains("- bye → Exit the program"));
    }

    @Test
    public void testShowScreenPromptShopping() {
        ui.showScreenPrompt(ScreenState.SHOPPING);
        String output = outContent.toString();

        assertTrue(output.contains("You're now in the SHOPPING LIST screen."));
        assertTrue(output.contains("- list → Show all ingredients in shopping list"));
        assertTrue(output.contains("- add [item] [qty] → Add ingredients to shopping list"));
        assertTrue(output.contains("- delete [item] [qty] → Remove ingredients from shopping list"));
        assertTrue(output.contains("- back → Return to the main screen"));
        assertTrue(output.contains("- bye → Exit the program"));
    }

    @Test
    public void testShowScreenPromptRecipe() {
        ui.showScreenPrompt(ScreenState.RECIPE);
        String output = outContent.toString();

        assertTrue(output.contains("You're now in the RECIPE screen."));
        assertTrue(output.contains("- list → Show all recipes"));
        assertTrue(output.contains("- add [name] → Add a new recipe"));
        assertTrue(output.contains("- delete [name] → Delete a recipe"));
        assertTrue(output.contains("- back → Return to the main screen"));
        assertTrue(output.contains("- bye → Exit the program"));
    }

    @Test
    public void testShowScreenPromptExit() {
        ui.showScreenPrompt(ScreenState.EXIT);
        String output = outContent.toString();

        assertTrue(output.contains("Unknown screen state"));
    }


    @Test
    public void testShowGoodbyeMessage() {
        ui.showGoodbyeMessage();
        String output = outContent.toString();

        assertTrue(output.contains("Goodbye, see you soon!"));
    }

    @Test
    public void testGetUserCommand_readsTrimmedInput() {
        String simulatedInput = "  inventory  \n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());

        Ui ui = new Ui(in); // Use testable constructor with simulated input
        String command = ui.getUserCommand();

        assertEquals("inventory", command);
        assertTrue(outContent.toString().contains("Enter command:"));
    }

    @Test
    public void testGetUserCommand_noInput_returnsEmptyString() {
        // No input provided
        System.setIn(new ByteArrayInputStream(new byte[0]));

        Ui ui = new Ui();
        String command = ui.getUserCommand();

        assertEquals("", command);
        String output = outContent.toString();
        assertTrue(output.contains("Enter command:"));
        assertTrue(output.contains("No input detected. Exiting..."));
    }

    @Test
    void testShowResultToUserValid() {
        CommandResult result = new CommandResult("Ingredient added");

        ui.showResultToUser(result);

        assertTrue(outContent.toString().contains("Ingredient added"));
    }

    @Test
    void testShowResultToUserNull() {
        CommandResult result = new CommandResult(ScreenState.WELCOME);

        ui.showResultToUser(result);

        assertEquals("", outContent.toString());
    }
}
