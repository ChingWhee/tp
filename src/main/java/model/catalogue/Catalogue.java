package model.catalogue;

import commands.CommandResult;
import model.Ingredient;

import java.util.stream.Collectors;

import java.util.ArrayList;

/**
 * A generic catalogue of items, providing common methods for managing them.
 * This class serves as a base for various catalogues such as inventory and recipes.
 * Subclasses should implement methods to handle adding and deleting items with custom behavior.
 *
 * @param <T> The type of item stored in the catalogue.
 */
public abstract class Catalogue<T> {
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
     * @param item       The item to be added.
     * @param isSilenced skips user input if silenced
     */
    public abstract CommandResult addItem(T item, boolean isSilenced);

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
                "This catalogue contains the following list of items:\n");
        for (int i = 0; i < items.size(); i++) {
            result.append(i + 1).append(". ").append(items.get(i)).append("\n");
        }

        return new CommandResult(result.toString().trim());
    }

    /**
     * Searches for items in the catalogue that match the given query using a custom string extractor.
     * <p>
     * This method allows flexible searching by letting the caller specify how to extract the searchable
     * text from each item (e.g., getName(), getTitle(), toString()).
     * The search is case-insensitive and matches any item whose extracted string contains the query substring.
     * </p>
     *
     * @param query      The search keyword to look for.
     * @param extractor  A function that extracts the text to match from each item in the catalogue.
     * @return A {@link CommandResult} containing either a formatted list of matches or a not-found message.
     */
    public CommandResult findItem(String query, java.util.function.Function<T, String> extractor) {
        if (query == null || query.trim().isEmpty()) {
            return new CommandResult("Please provide a keyword to search.");
        }

        String lowerQuery = query.trim().toLowerCase();
        ArrayList<T> matching = items.stream()
                .filter(item -> {
                    String str = extractor.apply(item);
                    return str != null && str.toLowerCase().contains(lowerQuery);
                })
                .collect(Collectors.toCollection(ArrayList::new));

        if (matching.isEmpty()) {
            return new CommandResult("No items found containing: " + query);
        }

        StringBuilder result = new StringBuilder("Found items:\n");
        for (int i = 0; i < matching.size(); i++) {
            T item = matching.get(i);
            result.append(i + 1).append(". ");

            if (item instanceof Ingredient ingredient) {
                result.append(ingredient.getQuantity()).append("x ").append(ingredient.getIngredientName());
            } else {
                // fallback to extractor or toString
                result.append(extractor.apply(item));
            }

            result.append("\n");
        }


        return new CommandResult(result.toString().trim());
    }

    /**
     * Default findItem method to be optionally overridden by subclasses.
     *
     * @param query The search keyword.
     * @return A CommandResult (default implementation just returns unsupported).
     */
    public CommandResult findItem(String query) {
        return new CommandResult("Search is not supported for this catalogue.");
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
