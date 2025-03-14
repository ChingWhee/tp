package backend.storage.catalogue;

import java.util.ArrayList;

/**
 * A generic catalogue of items, providing common methods for managing them.
 *
 * @param <T> The type of item stored in the catalogue.
 */
public abstract class Catalogue<T> {
    protected ArrayList<T> items;

    public Catalogue() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the catalogue.
     *
     * @param item The item to be added.
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Deletes an item from the catalogue.
     *
     * @param item The item to be removed.
     */
    public void deleteItem(T item) {
        items.remove(item);
    }

    /**
     * Updates an existing item by replacing it with a new version.
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
     * Retrieves all items in the catalogue.
     *
     * @return A list of all stored items.
     */
    public ArrayList<T> getItems() {
        return items;
    }

    /**
     * Displays all items in the catalogue.
     */
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        for (T item : items) {
            System.out.println(item);
        }
    }
}
