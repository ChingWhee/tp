package commands;

import static controller.KitchenCTRL.requireActiveRecipe;

import controller.KitchenCTRL;
import model.Ingredient;
import model.catalogue.Catalogue;
import model.catalogue.Recipe;

public class UpdateCommand extends Command {
    private final String name;
    private final int quantity;

    public UpdateCommand(String name, int newQuantity) {
        this.name = name;
        this.quantity = newQuantity;
    }

    @Override
    public CommandResult execute(Catalogue<?> catalogue) {
        assert catalogue != null : "Catalogue must not be null";

        return switch (KitchenCTRL.getCurrentScreen()) {
            case RECIPE -> {
                requireActiveRecipe();
                if (catalogue instanceof Recipe recipe) {
                    Ingredient ingredient = new Ingredient(name, quantity);
                    yield recipe.updateItem(ingredient);
                }
                yield new CommandResult("Invalid catalogue for recipe operation.", null);
            }
            default -> new CommandResult("Unsupported screen state for AddCommand.", null);
        };
    }
}
