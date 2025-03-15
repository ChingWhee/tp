import model.Ingredient;
import model.catalogue.IngredientCatalogue;
import model.catalogue.RecipeCatalogue;

import java.util.Scanner;

public class KitchenCTRL {
    static IngredientCatalogue ingredientCatalogue = new IngredientCatalogue();
    static RecipeCatalogue recipeCatalogue = new RecipeCatalogue();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        final String LOGO = """
                   .-.    .-.    .-.    .-.  .-.  .-"-.  .-.      .--.      .-.  .--.
                  <   |  <   |  <   |   | |  | |  | | |  | |      |()|     /  |  |  |
                   )  |   )  |   )  |   | |  | |  | | |  | |      |  |     |  |  |  |
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
                  <___|  <___|  <___|   |\\|  | |  | | |  | |      |  |     |  |  |__|
                   }  |   || |   =  |   | |  | |  `-|-'  | |      |  |     |  |  |   L
                   }  |   || |   =  |   | |  | |   /A\\   | |      |  |     |  |  |   J
                   }  |   || |   =  |   |/   | |   |H|   | |      |  |     |  |  |    L
                   }  |   || |   =  |        | |   |H|   | |     _|__|_    |  |  |    J
                   }  |   || |   =  |        | |   |H|   | |    | |   |    |  |  | A   L
                   }  |   || |   =  |        | |   \\V/   | |    | |   |     \\ |  | H   J
                   }  |   FF |   =  |        | |    "    | |    | \\   |      ,Y  | H A  L
                   }  |   LL |    = |       _F J_       _F J_   \\  `--|       |  | H H  J
                   }  |   LL |     \\|     /       \\   /       \\  `.___|       |  | H H A L
                   }  |   \\\\ |           J         L |  _   _  |              |  | H H U J
                   }  |    \\\\|           J         F | | | | | |             /   | U ".-'
                    } |     \\|            \\       /  | | | | | |    .-.-.-.-/    |_.-'
                     \\|                    `-._.-'   | | | | | |   ( (-(-(-( )
                                                     `-' `-' `-'    `-`-`-`-'
                """;

        System.out.println(LOGO);
        System.out.println("Welcome to KitchenCTRL!");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
        cwTest();
    }

    public static void cwTest() {
        Ingredient egg = new Ingredient("Egg", 2);
        Ingredient whiteSugar = new Ingredient("White sugar", 2);
        Ingredient brownSugar = new Ingredient("Brown sugar", 2);
        Ingredient sugar = new Ingredient("Sugar", 2);
        Ingredient sugar2 = new Ingredient("Sugar", 2);

        ingredientCatalogue.addItem(egg);
        ingredientCatalogue.addItem(whiteSugar);
        ingredientCatalogue.addItem(brownSugar);
        ingredientCatalogue.addItem(sugar);
        ingredientCatalogue.deleteItem(sugar2);
        ingredientCatalogue.displayItems();
    }
}
