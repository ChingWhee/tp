# KitchenCTRL User Guide

KitchenCTRL is a command-line application designed to manage an inventory of ingredients and recipes. 
Users can add, delete, and list ingredients, as well as manage recipes efficiently.

## 📖 Table of Contents


- [Quick Start](#quick-start)
- [Interface Overview](#interface-overview)
- [Navigation Commands](#navigation-commands)
  - [Switch to Inventory: `inventory`](#switch-to-inventory-inventory)
  - [Switch to Recipe: `recipe`](#switch-to-recipe-recipe)
  - [Switch to Main Menu: `back`](#switch-to-main-menu-back)
  - [Exiting the Program: `bye`](#exiting-the-program-bye)
- [Inventory Commands](#inventory-commands)
  - [Listing Ingredients: `list`](#listing-ingredients-list)
  - [Finding an Ingredient: `find`](#finding-an-ingredient-find)
  - [Adding an Ingredient: `add`](#adding-an-ingredient-add)
  - [Deleting an Ingredient: `delete`](#deleting-an-ingredient-delete)
  - [Editing an Ingredient: `edit`](#editing-an-ingredient-edit)
  - [View Cookable Recipes: `cookable`](#view-cookable-recipes-cookable)
- [RecipeBook Commands](#recipebook-commands)
  - [Listing Recipes: `list`](#listing-recipes-list)
  - [Finding a Recipe: `find`](#finding-a-recipe-find)
  - [Adding a Recipe: `add`](#adding-a-recipe-add)
  - [Deleting a Recipe: `delete`](#deleting-a-recipe-delete)
  - [Editing a Recipe: `edit`](#editing-a-recipe-edit)
  - [Cooking a Recipe: `cook`](#cooking-a-recipe-cook)
- [Handling Similar Entries](#handling-similar-entries)
  - [Adding or Deleting Similar Ingredients](#adding-or-deleting-similar-ingredients)
  - [Adding or Deleting a Recipe](#adding-or-deleting-a-recipe)
- [Data Storage](#data-storage)
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
    - bye -> Exit the program
    - help -> View available comands
    ```
5. Type the command in the command box and press Enter to execute it. Start typing commands to manage your kitchen!

---
## 🖥️ Interface Overview

Upon startup, you’ll see a **main menu** with 4 command options:

- `inventory`: Manage your pantry items
- `recipe`: Save and manage recipes
- `bye`: Exit the program
- `help`: View available commands

Each screen has its own set of commands, described below.

---

## 🧭 Navigation Commands

### Switch to inventory: `inventory`
Switch to the inventory screen of the application from the main menu <br>
Format: `inventory`

### Switch to recipe `recipe`
Switch to the recipebook screen of the application from the main menu <br>
Format: `recipe`

### Switch to previous screen `back`
Switch to the main menu from the INVENTORY / RECIPEBOOK screen <br>
OR <br>
Switch from a Recipe back to RecipeBook <br>
Format: `back`

### Exiting the program `bye`
Exit the program <br>
Format: `bye`

---

## 📦 Inventory Commands

Once you're in the `inventory` screen:

### Listing Ingredients: `list`
Displays all ingredients currently stored in the inventory.

Format:
`list`

### 🔍 Finding an Ingredient: `find`
Searches for ingredients by name (case-insensitive, partial matches allowed).

**Format:**  
`find [keyword]`

**Example:**  
`find sugar`  
Lists all ingredients containing "sugar", such as:
```
Matching items:
- Sugar (2)
- Brown Sugar (5)
```

### Adding an Ingredient: `add`
Adds a new ingredient to the inventory.

Format:
`add [name] [quantity]`
- If a similar ingredient exists, the system will prompt for confirmation.

Example of usage:

`add Sugar 2`
Adds 2 units of sugar to the inventory.
<br><br>

### Deleting an Ingredient: `delete`
Removes an ingredient from the inventory.

Format:
`delete [name] [quantity]`

Example of usage:

`delete Sugar 1`
Removes 1 unit of sugar from the inventory.
<br><br>

### Editing an Ingredient: `edit`
Removes an ingredient from the inventory.

Format:
`edit [name] [quantity]`

Example of usage:

`edit Sugar 10`
Sets quantity of sugar to 10 in the inventory.
<br><br>

### View Cookable Recipes: `cookable`
Lists all recipes that can be cooked based off ingredients in inventory.

Format:
`cookable`

Example of usage:

_Assuming Inventory: Bread (1)_

| Recipe    | Ingredients           | Cookable |
|-----------|------------------------|----------|
| Toast     | Bread (1)              | ✅       |
| Sandwich  | Bread (2), Egg (1)     | ❌       |

`cookable` returns `Toast`

---

## 🍳 RecipeBook Commands

In the `recipebook` screen:

### Listing Ingredients: `list`
Displays all recipes in the RecipeBook.

Format:
`list`

### 🔍 Finding a Recipe: `find`
Searches for recipes with names that contain the keyword.

**Format:**  
`find [recipe_name]`

**Example:**  
`find cake`
```
Matching recipes:
- Chocolate Cake
- Carrot Cake
```

### Adding a Recipe: `add`
Creates a new recipe in the system.

### TO BE CHANGED
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

### Editing a Recipe: `edit`
Removes an ingredient from the inventory.

Format:
`edit [name]`

Example of usage:

`edit Sandwich`
Changes the current screen from `RecipeBook` to Sandwich's `Recipe` screen.
<br><br>

### Cooking a Recipe: `cook`
Cooks a Recipe by removing the active Recipe's needed ingredients from Inventory. <br>
If there are insufficient ingredients, returns a list of the balance to the user <br>

Format:
`cook [name]`

Example of usage:

| Ingredients for Toast | Ingredients for Sandwich    | Inventory              | 
|---------------------- |-----------------------------|------------------------|
| Bread (1)             | Bread (2)                   | Bread (1)              | 
|                       | Egg (1)                     | Milk (1)               | 

`cook Sandwich`
returns `[Bread (1), Egg (1)]`

`cook Toast`
returns a success message, and removes `Bread (1)` from Inventory

---

## 🍳 Recipe Commands
This screen is only accesible via the `edit` command in RecipeBook <br>
The following commands are applied to the selected Recipe <br>

### Listing Ingredients: `list`
Displays all ingredients needed for the Recipe.

Format:
`list`

### 🔍 Finding an Ingredient: `find`
Searches for ingredients by name (case-insensitive, partial matches allowed).

**Format:**  
`find [keyword]`

**Example:**  
`find sugar`  
Lists all ingredients containing "sugar", such as:
```
Matching items:
- Sugar (2)
- Brown Sugar (5)
```

### Adding an Ingredient: `add`
Adds a new ingredient to the Recipe.

Format:
`add [name] [quantity]`
- If a similar ingredient exists, the system will prompt for confirmation.

Example of usage:

`add Sugar 2`
Adds 2 units of sugar to the inventory.
<br><br>

### Deleting an Ingredient: `delete`
Removes an ingredient from the Recipe.

Format:
`delete [name] [quantity]`

Example of usage:

`delete Sugar 1`
Removes 1 unit of sugar from the Recipe.
<br><br>

### Editing an Ingredient: `edit`
Removes an ingredient from the Recipe.

Format:
`edit [name] [quantity]`

Example of usage:

`edit Sugar 10`
Sets quantity of sugar to 10 in the Recipe.
<br><br>

---

## 🔍 Handling Similar Entries

### Adding or deleting similar ingredients
When adding or deleting ingredients, KitchenCTRL will check for **similar ingredients** if an exact match is not found
(e.g., `sugar` and `white_sugar`).  
You will be prompted to choose whether to:

- Add a new entry
- Update an existing one
- Cancel the action

Example:
`add white_sugar 1` <br>
`add sugar 1` <br>
will prompt the user to choose

### Adding or Deleting a Recipe

When you try to add a recipe, KitchenCTRL will check if any similar recipes already exist if an exact match is not found
If found, you will be prompted to:

- Add the recipe as a new entry
- Cancel the operation

Similarly, for deletion, you will be prompted to:

- Delete the similar recipe
- Cancel the operation
  
---


## 💾 Data Storage

All data is stored locally in `.txt` files under the `/data/` folder:

- `inventory.txt`
- `recipe_book.txt`

[//]: # (The data is automatically saved after each command.)
The data will be saved only when the program is successfully closed, i.e, "Goodbye, see you soon!" suggests that the save has been done.

---


## Command Summary

| Command                 | Format                     | Description                                         |
|-------------------------|----------------------------|-----------------------------------------------------|
| **Switch to Inventory** | `inventory`                | Navigate to the inventory screen.                   |
| **Switch to Recipe**    | `recipe`                   | Navigate to the recipe management screen.           |
| **Switch to Main Menu** | `back`                     | Return to the main menu from any sub-screen.        |
| **Exit the Program**    | `bye`                      | Close the application.                              |
| **Add Ingredient**      | `add [name] [quantity]`    | Add an ingredient to the inventory.                 |
| **Delete Ingredient**   | `delete [name] [quantity]` | Remove an ingredient from the inventory.            |
| **List Ingredients**    | `list`                     | Show all ingredients in the inventory.              |
| **Add Recipe**          | `add [recipe_name]`        | Create a new recipe entry.                          |
| **Delete Recipe**       | `delete [recipe_name]`     | Remove a recipe from the system.                    |
| **Find**                | `find [keyword]`           | Search in inventory, recipe book, or recipe.        |
---

## Conclusion
KitchenCTRL provides a structured way to manage kitchen inventory and recipes. By following the commands outlined, users can efficiently maintain their ingredient stocks and meal planning.

For any issues, refer to error messages or consult the developer documentation.
