package kitchenctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import model.Ingredient;
import model.catalogue.Recipe;
import org.junit.jupiter.api.Test;
import ui.inputparser.InputParser;

public class InputParserTest {

    @Test
    public void testGetUserChoiceForAddIngredient_validInput() {
        ArrayList<Ingredient> similar = new ArrayList<>();
        similar.add(new Ingredient("Sugar", 2));
        similar.add(new Ingredient("Brown Sugar", 3));

        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int choice = InputParser.getUserChoiceForAddIngredient(similar, new Ingredient("Sugar", 1));
        assertEquals(1, choice);
    }

    @Test
    public void testGetUserChoiceForDeleteIngredient_validInput() {
        ArrayList<Ingredient> similar = new ArrayList<>();
        similar.add(new Ingredient("Egg", 1));
        similar.add(new Ingredient("Egg Whites", 1));

        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int choice = InputParser.getUserChoiceForDeleteIngredient(similar, new Ingredient("Egg", 1));
        assertEquals(2, choice);
    }

    @Test
    public void testGetUserChoiceForAddRecipe_cancel() {
        ArrayList<Recipe> similar = new ArrayList<>();
        similar.add(new Recipe("Fried Rice"));

        String simulatedInput = "-1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int choice = InputParser.getUserChoiceForAddRecipe(similar, new Recipe("Fried Rice"));
        assertEquals(-1, choice);
    }

    @Test
    public void testGetUserChoiceForDeleteRecipe_selectFirst() {
        ArrayList<Recipe> similar = new ArrayList<>();
        similar.add(new Recipe("Nasi Lemak"));
        similar.add(new Recipe("Nasi Goreng"));

        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int choice = InputParser.getUserChoiceForDeleteRecipe(similar, new Recipe("Nasi"));
        assertEquals(1, choice);
    }
}
