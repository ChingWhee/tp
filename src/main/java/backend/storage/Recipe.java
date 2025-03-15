package backend.storage;

import backend.storage.catalogue.Catalogue;
public class Recipe extends Catalogue<Ingredient> {
    private String recipeName;

    public Recipe() {
        super();
    }

    public String getRecipeName() { return recipeName; }

    @Override
    public void addItem(Ingredient ingredient) {}

    @Override
    public void deleteItem(Ingredient ingredient) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe other = (Recipe) o;
        return this.recipeName.equals(other.recipeName);
    }
}


