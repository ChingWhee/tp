package kitchenctrl;

import commands.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.inputparser.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UiTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream)); // Redirect System.out for testing
    }

    // Utility method to set a new Scanner with mocked input
    private void setScannerInput(String input) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Use reflection to access and modify the private scanner field in Ui
        Field scannerField = Ui.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in)); // Replace scanner with new input
    }

    @Test
    void testShowResultToUser() {
        CommandResult result = new CommandResult("Ingredient added");

        Ui.showResultToUser(result);

        assertTrue(outputStream.toString().contains("Ingredient added"));
    }
}
