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
import controller.KitchenCTRL;
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
        KitchenCTRL.setCurrentScreen(ScreenState.WELCOME);
        Command command = parser.parseCommand("inventory");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.INVENTORY, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_recipe() {
        KitchenCTRL.setCurrentScreen(ScreenState.WELCOME);
        Command command = parser.parseCommand("recipe");
        assertInstanceOf(GoToCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.isScreenSwitch());
        assertEquals(ScreenState.RECIPEBOOK, result.getNewScreen());
    }

    @Test
    public void testWelcomeCommand_bye() {
        Command command = parser.parseCommand("bye");
        assertInstanceOf(ByeCommand.class, command);

        CommandResult result = command.execute();
        assertTrue(result.getFeedbackToUser().contains("Goodbye! Exiting program..."));
    }

    @Test
    public void testWelcomeCommand_invalid1() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("unknown"));
    }

    @Test
    public void testWelcomeCommand_invalid2() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("hey there"));
    }

    // --- INVENTORY SCREEN COMMANDS ---

    @Test
    public void testInventoryCommand_valid() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        Command command = parser.parseCommand("add 5 apple");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testInventoryCommand_delete_valid() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        Command command = parser.parseCommand("delete 2 sugar");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testInventoryCommand_list() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        Command command = parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testInventoryCommand_back() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        Command command = parser.parseCommand("back");
        assertInstanceOf(BackCommand.class, command);

        CommandResult result = command.execute();
        assertEquals(ScreenState.WELCOME, result.getNewScreen());
    }

    @Test
    public void testInventoryCommand_bye() {
        Command command = parser.parseCommand("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void testInventoryCommand_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("aaaaaaaa"));
    }

    @Test
    public void testInventoryCommand_add_invalidFormat() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("add milk"));
    }

    @Test
    public void testInventoryCommand_add_invalidQuantity() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVENTORY);
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("add milk two"));
    }

    @Test
    public void testInventoryCommand_delete_invalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("delete milk"));
    }

    @Test
    public void testInventoryCommand_delete_invalidQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("delete milk two"));
    }

    // --- RECIPE SCREEN COMMANDS ---

    @Test
    public void testRecipeCommand_add_valid() {
        KitchenCTRL.setCurrentScreen(ScreenState.RECIPE);
        Command command = parser.parseCommand("add 1 curry");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testRecipeCommand_delete_valid() {
        KitchenCTRL.setCurrentScreen(ScreenState.RECIPE);
        Command command = parser.parseCommand("delete 1 curry");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testRecipeCommand_list() {
        KitchenCTRL.setCurrentScreen(ScreenState.RECIPE);
        Command command = parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void testRecipeCommand_back() {
        KitchenCTRL.setCurrentScreen(ScreenState.RECIPE);
        Command command = parser.parseCommand("back");
        assertInstanceOf(BackCommand.class, command);

        CommandResult result = command.execute();
        assertEquals(ScreenState.RECIPEBOOK, result.getNewScreen());
    }

    @Test
    public void testRecipeCommand_bye() {
        Command command = parser.parseCommand("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void testRecipeCommand_invalid() {
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("notarealcommand"));
    }

    // --- ERROR CASES ---

    @Test
    public void testInvalidScreenState() {
        KitchenCTRL.setCurrentScreen(ScreenState.INVALID);
        assertThrows(IllegalArgumentException.class, () ->
                parser.parseCommand("add eggs 2"));
    }
}
