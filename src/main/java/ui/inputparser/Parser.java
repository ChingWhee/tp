package ui.inputparser;

import commands.AddIngredientCommand;
import commands.ByeCommand;
import commands.Command;

public class Parser {
    public Command parseCommand(String userInput) {
        String[] commands = userInput.split(" ", 2);

        String command = commands[0].toLowerCase();
        String args = (commands.length > 1) ? commands[1] : "";

        switch (command) {
        case "addingredient":
            return prepareAddIngredient(args);
        case "bye":
            return new ByeCommand();
        default:
            throw new IllegalArgumentException("Unknown command: " + command);

        }
    }




    private Command prepareAddIngredient(String args) {
        String[] parts = args.split(" ", 2); // Expecting format: "name quantity"

        if (parts.length < 2) {
            System.out.println("Invalid format! Usage: addingredient <name> <quantity>");
        }

        String name = parts[0].trim();
        int quantity = 0;

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Quantity must be a valid integer!");
        }

        return new AddIngredientCommand(name, quantity);
    }
}
