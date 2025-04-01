package commands;

import model.catalogue.Catalogue;

/**
 * Represents a command that searches for items within the current catalogue
 * using a user-provided keyword.
 * <p>
 * This command supports any catalogue that implements {@code findItem(String)},
 * including RecipeBook, Inventory, and Recipe. The actual filtering logic
 * is delegated to the catalogue, which defines how search results are matched.
 * </p>
 */
public class FindCommand extends Command {
    /** The keyword used to search for matching items. */
    private final String keyword;

    /**
     * Constructs a FindCommand with the given keyword.
     *
     * @param keyword The search term to look for within the catalogue.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the search on the given catalogue.
     *
     * @param catalogue The catalogue to search within (e.g., RecipeBook, Inventory, Recipe).
     * @return A {@link CommandResult} containing matched items or a not-found message.
     */
    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        if (catalogue == null) {
            return new CommandResult("No active catalogue to search in.");
        }

        return catalogue.findItem(keyword);
    }
}