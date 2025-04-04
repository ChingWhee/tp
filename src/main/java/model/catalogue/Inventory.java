package model.catalogue;

import commands.CommandResult;

public class Inventory extends IngredientCatalogue {
    @Override
    public String getType() {
        return "Inventory";
    }

    @Override
    protected String getCatalogueLabel() {
        return "inventory";
    }

    @Override
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("Nothing found in inventory.");
        }
        StringBuilder result = new StringBuilder("These are the items in your inventory:\n");
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).toString();
            result.append(i + 1).append(". ").append(name == null ? "[Unnamed Ingredient]" : name).append("\n");
        }
        return new CommandResult(result.toString().trim());
    }
}
