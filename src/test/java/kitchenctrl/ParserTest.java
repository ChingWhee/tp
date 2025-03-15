package kitchenctrl;

import commands.ByeCommand;
import commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.inputparser.Parser;


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
        // Expect an AssertionError when the quantity is invalid (negative or zero)
        AssertionError thrown = assertThrows(AssertionError.class, () -> {
            parser.parseCommand("addingredient sugar -5"); // Should trigger assertion
        });

        assertTrue(thrown.getMessage().contains("Ingredient quantity must be greater than zero"));
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
