package kitchenctrl;

import model.Ingredient;
import model.catalogue.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ui.inputparser.InputParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {

    @AfterEach
    public void resetScannerToSystemIn() {
        InputParser.setScanner(new Scanner(System.in));
    }

    @Test
    public void testGetUserChoiceForAddIngredient_validInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
        InputParser.setScanner(new Scanner(input));

        ArrayList<Ingredient> similar = new ArrayList<>();
        similar.add(new Ingredient("Sugar", 5));
        Ingredient newIngredient = new Ingredient("Sugar", 3);

        int result = InputParser.getUserChoiceForAddIngredient(similar, newIngredient);
        assertEquals(1, result);
    }

    @Test
    public void testGetUserChoiceForDeleteIngredient_validInput() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
        InputParser.setScanner(new Scanner(input));

        ArrayList<Ingredient> similar = new ArrayList<>();
        similar.add(new Ingredient("Flour", 2));
        Ingredient toDelete = new Ingredient("Flour", 1);

        int result = InputParser.getUserChoiceForDeleteIngredient(similar, toDelete);
        assertEquals(1, result);
    }

    @Test
    public void testGetUserChoiceForAddRecipe_cancel() {
        ByteArrayInputStream input = new ByteArrayInputStream("-1\n".getBytes());
        InputParser.setScanner(new Scanner(input));

        ArrayList<Recipe> similar = new ArrayList<>();
        similar.add(new Recipe("Pasta"));
        Recipe newRecipe = new Recipe("Pasta");

        int result = InputParser.getUserChoiceForAddRecipe(similar, newRecipe);
        assertEquals(-1, result);
    }

    @Test
    public void testGetUserChoiceForDeleteRecipe_cancel() {
        ByteArrayInputStream input = new ByteArrayInputStream("-1\n".getBytes());
        InputParser.setScanner(new Scanner(input));

        ArrayList<Recipe> similar = new ArrayList<>();
        similar.add(new Recipe("Soup"));
        Recipe toDelete = new Recipe("Soup");

        int result = InputParser.getUserChoiceForDeleteRecipe(similar, toDelete);
        assertEquals(-1, result);
    }
}
