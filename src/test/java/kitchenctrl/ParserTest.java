package kitchenctrl;

import commands.ByeCommand;
import commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.inputparser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }


    @Test
    void testParseCommand_addIngredientInvalidFormat() {
        ArrayIndexOutOfBoundsException thrown = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            parser.parseCommand("addingredient sugar"); // Missing quantity
        });
    }

    @Test
    void testParseCommand_addIngredientInvalidQuantity() {
        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Execute command that should print an error
        parser.parseCommand("addingredient sugar five");

        // Restore System.out
        System.setOut(System.out);

        // Convert output stream to string and verify it contains expected error message
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Quantity must be a valid integer!")); // Expect printed output
    }

    @Test
    void testParseCommand_bye() {
        Command command = parser.parseCommand("bye");

        assertNotNull(command);
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    void testParseCommand_unknownCommand() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseCommand("unknowncommand");
        });

        assertEquals("Unknown command: unknowncommand", thrown.getMessage());
    }
}
