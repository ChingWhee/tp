package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

/**
 * Represents an inventory of ingredients.
 * <p>
 * This class extends {@link IngredientCatalogue} and provides implementation
 * for inventory-specific labeling and item listing.
 * </p>
 */
public class Inventory extends IngredientCatalogue {

    /**
     * Returns the type identifier of this catalogue.
     *
     * @return A string representing this catalogue's type, which is "Inventory".
     */
    @Override
    public String getType() {
        return "Inventory";
    }

    /**
     * Returns the catalogue label used in user-facing messages.
     * Used by the parent class to dynamically inject "inventory" in printed output.
     *
     * @return The label "inventory".
     */
    @Override
    protected String getCatalogueLabel() {
        return "inventory";
    }

    /**
     * Lists all ingredients in the inventory in a numbered format.
     * If the inventory is empty, returns an appropriate message.
     *
     * @return A {@link CommandResult} containing the list of ingredients
     *     or a message indicating the inventory is empty.
     */
    @Override
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("Nothing found in inventory.");
        }

        StringBuilder result = new StringBuilder("These are the items in your inventory:\n");
        for (int i = 0; i < items.size(); i++) {
            Ingredient ingredient = items.get(i);
            int qty = ingredient.getQuantity();
            String name = ingredient.getIngredientName();
            result.append(i + 1).append(". ").append(qty).append("x ").append(name).append("\n");
        }

        return new CommandResult(result.toString().trim());
    }
}
