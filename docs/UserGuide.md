# KitchenCTRL User Guide

KitchenCTRL is a command-line application designed to manage an inventory of ingredients and recipes. 
Users can add, delete, and list ingredients, as well as manage recipes efficiently.

## 📖 Table of Contents

- [Quick Start](#-quick-start)
- [Interface Overview](#-interface-overview)
- [Navigation Commands](#-navigation-commands)
  - [Switch to Inventory: `inventory`](#switch-to-inventory-inventory)
  - [Switch to Shopping: `shopping`](#switch-to-shopping-shopping)
  - [Switch to Recipe: `recipe`](#switch-to-recipe-recipe)
  - [Switch to Main Menu: `back`](#switch-to-main-menu-back)
  - [Exiting the Program: `bye`](#exiting-the-program-bye)
- [Inventory and Shopping Commands](#-inventory-and-shopping-commands)
  - [Adding an Ingredient: `add`](#adding-an-ingredient-add)
  - [Deleting an Ingredient: `delete`](#deleting-an-ingredient-delete)
  - [Listing Ingredients: `list`](#listing-ingredients-list)
- [Recipe Commands](#-recipe-commands)
  - [Adding a Recipe: `add`](#adding-a-recipe-add)
  - [Deleting a Recipe: `delete`](#deleting-a-recipe-delete)
- [Handling Similar Entries](#-handling-similar-entries)
  - [Adding or deleting similar ingredients](#adding-or-deleting-similar-ingredients)
- [Data Storage](#-data-storage)
- [Coming Soon](#coming-soon)
- [Command Summary](#command-summary)
- [Conclusion](#conclusion)

---

## 💡 Quick Start

1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `KitchenCTRL` from [here](https://github.com/AY2425S2-CS2113-T13-1/tp).
3. Copy the file to the folder you want to use as the _home folder_ for your application.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tp.jar` 
   command to run the application. This is how the application looks like on startup: <br>
```aiignore
   .-.    .-.    .-.    .-.  .-.  .-"-.  .-.      .--.      .-.  .--.
  <   |  <   |  <   |   | |  | |  | | |  | |      |()|     /  |  |  |
   )  |   )  |   )  |   | |  | |  | | |  | |      |  |     |  |  |  |
   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
   )()|   )()|   )()|   |o|  | |  | | |  | |      |  |     |  |  |()|
  <___|  <___|  <___|   |\|  | |  | | |  | |      |  |     |  |  |__|
   }  |   || |   =  |   | |  | |  `-|-'  | |      |  |     |  |  |   L
   }  |   || |   =  |   | |  | |   /A\   | |      |  |     |  |  |   J
   }  |   || |   =  |   |/   | |   |H|   | |      |  |     |  |  |    L
   }  |   || |   =  |        | |   |H|   | |     _|__|_    |  |  |    J
   }  |   || |   =  |        | |   |H|   | |    | |   |    |  |  | A   L
   }  |   || |   =  |        | |   \V/   | |    | |   |     \ |  | H   J
   }  |   FF |   =  |        | |    "    | |    | \   |      ,Y  | H A  L
   }  |   LL |    = |       _F J_       _F J_   \  `--|       |  | H H  J
   }  |   LL |     \|     /       \   /       \  `.___|       |  | H H A L
   }  |   \\ |           J         L |  _   _  |              |  | H H U J
   }  |    \\|           J         F | | | | | |             /   | U ".-'
    } |     \|            \       /  | | | | | |    .-.-.-.-/    |_.-'
     \|                    `-._.-'   | | | | | |   ( (-(-(-( )
                                     `-' `-' `-'    `-`-`-`-'

Welcome to KitchenCTRL - your digital kitchen companion!
What would you like to do today? Available commands:
- inventory -> View and manage your inventory
- recipe -> View and manage your recipes
- shopping -> View and manage your shopping list
- bye -> Exit the program
```
5. Type the command in the command box and press Enter to execute it. Start typing commands to manage your kitchen!

---
## 🖥️ Interface Overview

Upon startup, you’ll see a **main menu** with 4 command options:

- `inventory`: Manage your pantry items
- `shopping`: Track what you need to buy
- `recipe`: Save and manage recipes
- `bye`: Exit the program

Each screen has its own set of commands, described below.

---

## 🧭 Navigation Commands

### Switch to inventory: `inventory`
Switch to the inventory screen of the application from the main menu <br>
Format: `inventory`

### Switch to shopping: `shopping`
Switch to the shopping list screen of the application from the main menu <br>
Format: `shopping`

### Switch to recipe `recipe`
Switch to the recipe list screen of the application from the main menu <br>
Format: `recipe`

### Switch to main menu `back`
Switch to the main menu from the inventory / shopping / recipe screen <br>
Format: `back`

### Exiting the program `bye`
Exit the program <br>
Format: `bye`

---

## 📦 Inventory and Shopping Commands

Once you're in the `inventory` or `shopping` screen:

### Adding an Ingredient: `add`
Adds a new ingredient to the inventory or shopping list.

Format:
`add [name] [quantity]`
- If a similar ingredient exists, the system will prompt for confirmation.

Example of usage:

`add Sugar 2`
Adds 2 units of sugar to the inventory or shopping list.
<br><br>

### Deleting an Ingredient: `delete`
Removes an ingredient from the inventory or shopping list.

Format:
`delete [name] [quantity]`

Example of usage:

`delete Sugar 1`
Removes 1 unit of sugar from the inventory or shopping list.
<br><br>

### Listing Ingredients: `list`
Displays all ingredients currently stored in the inventory or shopping lisr.

Format:
`list`

---

## 🍳 Recipe Commands

In the `recipe` screen:

### Adding a Recipe: `add`
Creates a new recipe in the system.

Format:
`add [recipe_name]`
- The system will prompt for the required ingredients.

Example Usage:
`add ChocolateCake`
<br><br>

### Deleting a Recipe: `delete`
Removes a recipe from the catalogue.

Format:
`delete [recipe_name]`

Example Usage:
`delete ChocolateCake`
Deletes the recipe for "ChocolateCake" from the system.

---

## 🔍 Handling Similar Entries

### Adding or deleting similar ingredients
When adding or deleting ingredients, KitchenCTRL will check for **similar ingredients** 
(e.g., `brown sugar` and `white sugar`).  
You will be prompted to choose whether to:

- Add a new entry
- Update an existing one
- Cancel the action

Example:

---

## 💾 Data Storage

All data is stored locally in `.txt` files under the `/data/` folder:

- `inventory.txt`
- `recipe_book.txt`
- `shopping_catalogue.txt`

The data is automatically saved after each command.

---
## Coming soon

### Adding a Recipe

When you try to add a recipe, KitchenCTRL will check if any similar recipes already exist.
If found, you will be prompted to:

- Add the recipe as a new entry
- Cancel the operation

### Deleting a Recipe

When you try to delete an ingredient, KitchenCTRL will check for any similar items.
If found, you will be prompted to:

- Delete existing recipe
- Cancel the action

---

## Developer Notes
### Core Components
- `InputParser`: Handles user input and choice validation.
- `KitchenCTRL`: Main application logic, including initialization and command execution.
- `Command`: Base class for commands like `AddIngredientCommand`, `DeleteIngredientCommand`, and `ByeCommand`.
- `IngredientCatalogue`: Manages the inventory of ingredients.
- `RecipeCatalogue`: Stores and manages recipes.
- `CatalogueContentManager`: Handles loading and saving data.
### Adding New Commands
To add a new command:
- Create a new class extending `Command`.
- Implement the `execute()` method.
- Register the command in `Parser`.

--- 

## Command Summary

| Command                | Format                                      | Description |
|------------------------|---------------------------------------------|-------------|
| **Switch to Inventory** | `inventory`                                | Navigate to the inventory screen. |
| **Switch to Shopping**  | `shopping`                                 | Navigate to the shopping list screen. |
| **Switch to Recipe**    | `recipe`                                   | Navigate to the recipe management screen. |
| **Switch to Main Menu** | `back`                                     | Return to the main menu from any sub-screen. |
| **Exit the Program**    | `bye`                                      | Close the application. |
| **Add Ingredient**      | `add [name] [quantity]`                    | Add an ingredient to the inventory or shopping list. |
| **Delete Ingredient**   | `delete [name] [quantity]`                 | Remove an ingredient from the inventory or shopping list. |
| **List Ingredients**    | `list`                                     | Show all ingredients in the inventory or shopping list. |
| **Add Recipe**         | `add [recipe_name]`                        | Create a new recipe entry. |
| **Delete Recipe**      | `delete [recipe_name]`                     | Remove a recipe from the system. |

---

## Conclusion
KitchenCTRL provides a structured way to manage kitchen inventory and recipes. By following the commands outlined, users can efficiently maintain their ingredient stocks and meal planning.

For any issues, refer to error messages or consult the developer documentation.
