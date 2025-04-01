package model.catalogue;

import commands.CommandResult;

import java.util.ArrayList;

/**
 * A generic catalogue of items, providing common methods for managing them.
 * This class serves as a base for various catalogues such as inventory, shopping lists, and recipes.
 * Subclasses should implement methods to handle adding and deleting items with custom behavior.
 *
 * @param <T> The type of item stored in the catalogue.
 */
public abstract class Catalogue<T> {
    protected static final int FIRST_ITEM_INDEX = 0;
    protected static final int SINGLE_MATCH = 1;
    protected ArrayList<T> items;

    /**
     * Constructs an empty catalogue.
     */
    public Catalogue() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the catalogue.
     *
     * @param item The item to be added.
     */
    public abstract CommandResult addItem(T item);

    /**
     * Deletes an item from the catalogue.
     *
     * @param item The item to be removed.
     */
    public abstract CommandResult deleteItem(T item);

    /**
     * Updates an existing item in the catalogue by replacing it with a new version.
     * If the item does not exist, no action is taken.
     *
     * @param oldItem The item to be replaced.
     * @param newItem The updated item.
     */
    public void updateItem(T oldItem, T newItem) {
        int index = items.indexOf(oldItem);
        if (index != -1) {
            items.set(index, newItem);
        }
    }

    /**
     * Retrieves all items stored in the catalogue.
     *
     * @return An {@link ArrayList} containing all items in the catalogue.
     */
    public ArrayList<T> getItems() {
        return items;
    }

    /**
     * Displays all items in the catalogue in a numbered list format.
     * If the catalogue is empty, a message is displayed instead.
     */
    public CommandResult listItems() {
        if (items.isEmpty()) {
            return new CommandResult("No items found.");
        }

        StringBuilder result = new StringBuilder(
                "(override me) This catalogue contains the following list of items:\n");
        for (int i = 0; i < items.size(); i++) {
            result.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }

        return new CommandResult(result.toString().trim());
    }

    /**
     * Retrieves all items in the catalogue as a formatted string.
     *
     * @return A string representation of all items in the catalogue.
     */
    public String getCatalogueContent() {
        if (items.isEmpty()) {
            return "";
        }
        StringBuilder content = new StringBuilder();
        for (T item : items) {
            content.append(item.toString()).append("\n");
        }
        return content.toString();
    }

    /**
     * Retrieves the name of the catalogue.
     *
     * @return The name of the catalogue.
     */
    public abstract String getType();

    /**
     * Abstract method to be implemented by subclasses to get an item by name.
     *
     * @param name The name of the item to find.
     * @return The matching item if found, otherwise null.
     */
    public abstract T getItemByName(String name);
}
