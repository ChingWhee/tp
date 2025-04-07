package model;

public class Ingredient {
    private String ingredientName;
    private int quantity;

    public Ingredient(String ingredientName, int quantity) {
        if (ingredientName == null || ingredientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Ingredient that = (Ingredient) obj;
        return ingredientName.equalsIgnoreCase(that.ingredientName); // Case-insensitive comparison
    }

    @Override
    public int hashCode() {
        return ingredientName.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return ingredientName + " (" + quantity + ")";
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        if (ingredientName == null || ingredientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
        }
        this.ingredientName = ingredientName;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive.");
        }
        if (this.quantity + quantity > 99999){
            this.quantity = 99999;
            return;
        }
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to subtract must be positive.");
        }
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException("Insufficient quantity to subtract.");
        }
        this.quantity -= quantity;
    }
}
