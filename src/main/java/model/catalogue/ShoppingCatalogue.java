package model.catalogue;

/**
 * Represents the shopping list catalogue, containing ingredients to be purchased.
 */
public class ShoppingCatalogue extends IngredientCatalogue {

    private final String NAME = "ShoppingCatalogue";

    public ShoppingCatalogue() {

    }

    public String getName() {
        return NAME;
    }
}
