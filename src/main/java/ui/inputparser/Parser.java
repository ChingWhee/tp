package ui.inputparser;

import commands.AddIngredientCommand;
import commands.ByeCommand;
import commands.Command;
import commands.DeleteIngredientCommand;

public class Parser {
    public Command parseCommand(String userInput) {
        String[] commands = userInput.split(" ", 2);

        String command = commands[0].toLowerCase();
        String args = (commands.length > 1) ? commands[1] : "";

        switch (command) {
        case "addingredient":
            return prepareAddIngredient(args);
        case "deleteingredient":
            return prepareDeleteIngredient(args);
        case "listinventory":
            return listInventory();
        case "bye":
            return new ByeCommand();
        default:
            throw new IllegalArgumentException("Unknown command: " + command);
        }
    }


    private Command prepareAddIngredient(String args) {
        String[] parts = args.split(" ", 2); // Expecting format: "name quantity"

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid format! Usage: addingredient <name> <quantity>");
        }

        String name = parts[0].trim();
        int quantity = 0;

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid integer!");
        }

        return new AddIngredientCommand(name, quantity);
    }


    private Command prepareDeleteIngredient(String args) {
        String[] parts = args.split(" ", 2); // Expecting format: "name quantity"

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid format! Usage: deleteingredient <name> <quantity>");
        }

        String name = parts[0].trim();
        int quantity = 0;

        try {
            quantity = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be a valid integer!");
        }

        return new DeleteIngredientCommand(name, quantity);
    }

    private Command listInventory() {
        return new ListIngredientCommand();
    }
}



