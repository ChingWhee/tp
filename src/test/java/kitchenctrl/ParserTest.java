package kitchenctrl;

import commands.Command;
import commands.AddCommand;
import commands.BackCommand;
import commands.DeleteCommand;
import commands.ListCommand;
import commands.ByeCommand;
import commands.GoToCommand;

import commands.CommandResult;

import controller.ScreenState;
import org.junit.jupiter.api.Test;
import ui.inputparser.Parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private final Parser parser = new Parser();

    // --- WELCOME SCREEN COMMANDS ---

    @Test
    public void testWelcomeCommand_inventory() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "inventory");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.INVENTORY, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_recipe() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "recipe");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.RECIPE, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_bye() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "bye");
        assertInstanceOf(ByeCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.getFeedbackToUser().contains("Goodbye! Exiting program..."));
    }

    @Test
    public void testWelcomeCommand_invalid1() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.WELCOME, "unknown"));
    }

    @Test
    public void testWelcomeCommand_invalid2() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.EXIT, "inventory"));
    }

    // --- INVENTORY SCREEN COMMANDS ---

    @Test
    public void testInventoryCommand_valid() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "add apple 5");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testInventoryCommand_delete_valid() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "delete sugar 2");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testInventoryCommand_list() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testInventoryCommand_back() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "back");
        assertInstanceOf(BackCommand.class, command);

        CommandResult result = command.execute();
        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    }

    @Test
    public void testInventoryCommand_bye() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void testInventoryCommand_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "aaaaaaaa"));
    }

    @Test
    public void testInventoryCommand_add_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "add milk"));
    }

    @Test
    public void testInventoryCommand_add_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "add milk two"));
    }

    @Test
    public void testInventoryCommand_delete_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "delete milk"));
    }

    @Test
    public void testInventoryCommand_delete_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "delete milk two"));
    }

    // --- SHOPPING SCREEN COMMANDS ---
    //    @Test
    //    public void testShoppingCommand_valid() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "add apple 5");
    //        assertInstanceOf(AddCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_valid() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "delete sugar 2");
    //        assertInstanceOf(DeleteCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_list() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "list");
    //        assertInstanceOf(ListCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_back() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "back");
    //        assertInstanceOf(BackCommand.class, command);
    //
    //        CommandResult result = command.execute();
    //        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_bye() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "bye");
    //        assertInstanceOf(ByeCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "aaaaaaaa"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_add_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "add milk"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_add_invalidQuantity() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "add milk two"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "delete milk"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_invalidQuantity() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "delete milk two"));
    //    }
    // --- RECIPE SCREEN COMMANDS ---

    @Test
    public void testRecipeCommand_add_valid() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "add curry 1");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testRecipeCommand_delete_valid() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "delete curry 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testRecipeCommand_list() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testRecipeCommand_back() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "back");
        assertInstanceOf(BackCommand.class, command);

        CommandResult result = command.execute();
        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    }

    @Test
    public void testRecipeCommand_bye() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void testRecipeCommand_invalid() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.RECIPE, "notarealcommand"));
    }

    // --- ERROR CASES ---

    @Test
    public void testInvalidScreenState() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.SHOPPING, "add eggs 2"));
    }
}
import commands.AddCommand;
import commands.BackCommand;
import commands.DeleteCommand;
import commands.ListCommand;
import commands.ByeCommand;
import commands.GoToCommand;

import commands.CommandResult;

import controller.ScreenState;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
    private final Parser parser = new Parser();

    // --- WELCOME SCREEN COMMANDS ---

    @Test
    public void testWelcomeCommand_inventory() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "inventory");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.INVENTORY, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_recipe() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "recipe");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.RECIPE, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_bye() {
        Command command = parser.parseCommand(ScreenState.WELCOME, "bye");
        assertInstanceOf(ByeCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.getFeedbackToUser().contains("Goodbye! Exiting program..."));
    }

    @Test
    public void testWelcomeCommand_invalid1() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.WELCOME, "unknown"));
    }

    @Test
    public void testWelcomeCommand_invalid2() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.EXIT, "inventory"));
    }

    // --- INVENTORY SCREEN COMMANDS ---
    @Test
    public void testInventoryCommand_valid() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "add apple 5");
        assertInstanceOf(AddCommand.class, command);
    @Test
    public void testInventoryCommand_delete_valid() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "delete sugar 2");
        assertInstanceOf(DeleteCommand.class, command);
    }

    public void testInventoryCommand_list() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "list");
        assertInstanceOf(ListCommand.class, command);
    public void testInventoryCommand_back() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "back");
        assertInstanceOf(BackCommand.class, command);
        CommandResult result = command.execute();
        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    public void testInventoryCommand_bye() {
        Command command = parser.parseCommand(ScreenState.INVENTORY, "bye");
    public void testInventoryCommand_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "aaaaaaaa"));
    }

    @Test
    public void testInventoryCommand_add_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "add milk"));
    }

    @Test
    public void testInventoryCommand_add_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "add milk two"));
    }

    @Test
    public void testInventoryCommand_delete_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "delete milk"));
    }

    @Test
    public void testInventoryCommand_delete_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.INVENTORY, "delete milk two"));
    }

    // --- SHOPPING SCREEN COMMANDS ---
    //    @Test
    //    public void testShoppingCommand_valid() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "add apple 5");
    //        assertInstanceOf(AddCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_valid() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "delete sugar 2");
    //        assertInstanceOf(DeleteCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_list() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "list");
    //        assertInstanceOf(ListCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_back() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "back");
    //        assertInstanceOf(BackCommand.class, command);
    //
    //        CommandResult result = command.execute();
    //        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_bye() {
    //        Command command = parser.parseCommand(ScreenState.SHOPPING, "bye");
    //        assertInstanceOf(ByeCommand.class, command);
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "aaaaaaaa"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_add_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "add milk"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_add_invalidQuantity() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "add milk two"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_invalidFormat() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "delete milk"));
    //    }
    //
    //    @Test
    //    public void testShoppingCommand_delete_invalidQuantity() {
    //        assertThrows(IllegalArgumentException.class, () ->
    //                parser.parseCommand(ScreenState.SHOPPING, "delete milk two"));
    //    }
    // --- RECIPE SCREEN COMMANDS ---

    @Test
    public void testRecipeCommand_add_valid() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "add curry 1");
        assertInstanceOf(AddCommand.class, command);
    }
    @Test
    public void testRecipeCommand_delete_valid() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "delete curry 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testRecipeCommand_list() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testRecipeCommand_back() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "back");
        assertInstanceOf(BackCommand.class, command);

        CommandResult result = command.execute();
        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    }

    @Test
    public void testRecipeCommand_bye() {
        Command command = parser.parseCommand(ScreenState.RECIPE, "bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void testRecipeCommand_invalid() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.RECIPE, "notarealcommand"));
    }

    // --- ERROR CASES ---

    @Test
    public void testInvalidScreenState() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand(ScreenState.SHOPPING, "add eggs 2"));