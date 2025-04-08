# KitchenCTRL User Guide

KitchenCTRL is a command-line application designed to manage an inventory of ingredients and recipes.
Users can add, delete, and list ingredients, as well as manage recipes efficiently.

## 📖 Table of Contents

- [Quick Start](#-quick-start)
- [Interface Overview](#interface-overview)
- [Navigation Commands](#-navigation-commands)
  - [Switch to Inventory: `inventory`](#switch-to-inventory-inventory)
  - [Switch to RecipeBook: `recipe`](#switch-to-recipebook-recipe)
  - [Switch to Previous Screen: `back`](#switch-to-previous-screen-back)
  - [Exiting the Program: `bye`](#exiting-the-program-bye)
  - [Display Commands: `help`](#display-commands-help)
- [📦 Inventory & Recipe CRUD Commands](#-inventory--recipe-crud-commands)
  - [Listing Ingredients: `list`](#-listing-ingredients-list)
  - [Finding an Ingredient: `find`](#-finding-an-ingredient-find)
  - [Adding an Ingredient: `add`](#-adding-an-ingredient-add)
  - [Deleting an Ingredient: `delete`](#-deleting-an-ingredient-delete)
  - [Editing an Ingredient: `edit`](#%EF%B8%8F-editing-an-ingredient-edit) 
- [🍳 RecipeBook Commands](#-recipebook-commands)
  - [Listing Recipes: `list`](#listing-ingredients-list)
  - [Finding a Recipe: `find`](#-finding-a-recipe-find)
  - [Adding a Recipe: `add`](#adding-a-recipe-add)
  - [Deleting a Recipe: `delete`](#deleting-a-recipe-delete)
  - [Editing a Recipe: `edit`](#editing-a-recipe-edit)
- [👨‍🍳 View Cookable Recipes: `cookable`](#view-cookable-recipes-cookable)
- [🍳 Cooking a Recipe: `cook`](#-cooking-a-recipe-cook)
- [🔍 Handling Similar Entries](#-handling-similar-entries)
  - [Similar Ingredients](#adding-deleting-or-editing-similar-ingredients-based-on-substring-match)
  - [Similar Recipes](#adding-or-deleting-similar-recipes-based-on-substring-match)
- [💾 Data Storage](#-data-storage)
  - [File Format: `inventory.txt`](#-file-format-inventorytxt)
  - [File Format: `recipe_book.txt`](#-file-format-recipe_booktxt)
- [⚠️ Handling Large Files or Quantities](#%EF%B8%8F-handling-large-files-or-quantities)
- [📋 Command Summary](#-command-summary)
- [Conclusion](#conclusion)

---

## 💡 Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `KitchenCTRL` from [here](https://github.com/AY2425S2-CS2113-T13-1/tp).
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
## Interface Overview

Upon startup, you’ll see a **main menu** with 4 command options:

- `inventory`: Manage your pantry items
- `recipe`: Manage recipes
- `bye`: Save to disk and Exit the program 
- `help`: View available commands 

Each screen has its own set of commands, described below.

---

## 🧭 Navigation Commands

### Switch to inventory: `inventory`
- Switch to the inventory screen of the application from the main menu.  
- **Format:** `inventory`

---

### Switch to recipebook: `recipe`
- Switch to the recipebook screen of the application from the main menu.
- **Format:** `recipe`

---

### Switch to previous screen: `back`
- Return to the main menu from the INVENTORY or RECIPEBOOK screen.
- Or return to RECIPEBOOK from within a specific recipe editing screen.

- **Format:** `back`

---

### Exiting the program: `bye`
[Available in all Screens]
- Exit the program.  
**Format:** `bye`

---

### End of Input (Ctrl+Z / Ctrl+C)
[Available in all Screens]
- Pressing **Ctrl+Z** or **Ctrl+C** will signal the end of input.
- The program will exit immediately with the message: `No input detected. Exiting...`

**Format:** _(Ctrl+Z / Ctrl+C during command input)_

---

### Display Commands: `help`
[Available in all Screens]
- Displays a list of supported commands in current screen.
- Displays action associated with commands
- **Format:** `help`

---

## 📦 Inventory & Recipe CRUD Commands

Once you're in the `inventory` or `edit recipe` screen:

---

### 📃 Listing Ingredients: `list`

Displays all ingredients currently stored in the inventory.

**Format:**  
`list`

---

### 🔍 Finding an Ingredient: `find`

Searches for ingredients by name (case-insensitive, partial matches allowed).

**Format:**  
`find [keyword]`

**Example:**  
`find apple`  
Lists all ingredients containing "apple", such as:
```
Found items:
1. 5x red apple
```

---

### ➕ Adding an Ingredient: `add`

Adds a new ingredient to the inventory.

**Format:**  
`add [qty] [ingredient description]`

**Example:**  
`add 1 cup of milk`  
Displays: `1x cup of milk added to recipe.`

---

### ➖ Deleting an Ingredient: `delete`

Removes a specified quantity of an ingredient from the inventory.

**Format:**  
`delete [qty] [ingredient description]`
- If the item doesn't exist, you’ll be notified.
- If you attempt to delete more than available, only the existing quantity is removed.
- Only the specified quantity is removed, unless it's the full amount—then the entire item is deleted.

**Examples:**  
`delete 2 hotdogs`  
Displays: `Ingredient does not exist in the inventory.`

`delete 999 green apple`  
Displays: `1x green apple removed from inventory. (Warning: You tried to remove more than available...)`

`delete 1 red apple`  
Displays: `1x red apple removed from inventory.`

---

### ✏️ Editing an Ingredient: `edit`

Sets the quantity of an existing ingredient in the inventory to the specified amount.

**Format:**  
`edit [qty] [ingredient description]`
- If similar items exist, you’ll be prompted to choose which one to update.
- Enter `-1` to cancel the operation.
- Only accepts integers from 1 to 99999.

**Examples:**
- `edit 999 red apple`  
  Increases red apple quantity to 999.

- `edit -1 apple` Displays an error: `Quantity must be a positive integer from 1-99999.`

- `edit 5 red apple`Sets red apple quantity to 5.

### Adding, deleting or editing ingredients with similar descriptions (substring)
When adding or deleting ingredients, KitchenCTRL will check for **similar ingredients** if an exact match is not found
(e.g., `sugar` and `white sugar`).  
You will be prompted to choose whether to:

- Add a new entry
- Update an existing one
- Cancel the action

Example:

`add 1 bag of white sugar` <br>
`add 1 sugar cube` <br>

will prompt the user to choose preferred course of action.

---

## 🍳 RecipeBook Commands

In the `recipebook` screen:

### Listing Ingredients: `list`
Displays all recipes in the RecipeBook.

Format:
`list`

### 🔍 Finding a Recipe: `find`
Searches for recipes with names that contain the keyword.

Format:  
`find [recipe_name]`

Example:  
`find cake`
```
Found recipes:
1. Chocolate Cake
2. Carrot Cake
```

### Adding a Recipe: `add`
Creates a new recipe in the system.

Format:
`add [recipe_name]`
KitchenCTRL will go into the "edit" recipe screen for the newly added recipe

Example Usage:
`add ChocolateCake`
<br><br>

### Deleting a Recipe: `delete`
Removes a recipe from the recipe book.

Format:
`delete [recipe_name]`

Example Usage:
`delete ChocolateCake`
Deletes the recipe for "ChocolateCake" from the system.

### Editing a Recipe: `edit`
Changes the current screen to edit the selected Recipe.

Format:
`edit [name]`

Example of usage:

`edit Sandwich`
Changes the current screen from `RecipeBook` to Sandwich's `Recipe` screen.
<br><br>

---
### View Cookable Recipes: cookable
Available in **RECIPEBOOK** and **INVENTORY** screens.

Lists all recipes that can be cooked based on the current ingredients in your inventory.

**Format:**  
`cookable`

**Example:**  
_Assuming Inventory contains: Bread (1)_

| Recipe    | Ingredients           | Cookable |
|-----------|------------------------|----------|
| Toast     | Bread (1)              | ✅       |
| Sandwich  | Bread (2), Egg (1)     | ❌       |

Command `cookable` returns:
```
Toast
```

---

### 🍳 Cooking a Recipe: `cook`
Available in **RECIPEBOOK**, **INVENTORY**, and **RECIPE** screens.

Attempts to cook a recipe by deducting its ingredients from your inventory.

---

#### 🧾 In RECIPEBOOK or INVENTORY screens

**Format:**  
`cook [recipe name]`

- Specify the name of the recipe to cook.
- System will attempt to cook that recipe.

**Example:**  
`cook apple pie`

---

#### 📄 In RECIPE screen (while editing/viewing a recipe)

**Format:**  
`cook` *(no arguments needed)*

- Cooks the **currently active recipe** being edited.
- More convenient when already working inside a specific recipe.

**Behavior:**
- If all required ingredients are available in inventory:
  - The recipe is successfully cooked.
  - Required ingredients are deducted from inventory.
  - A success message is displayed.
- If ingredients are missing:
  - The recipe is not cooked.
  - A list of the **missing ingredients** and their required quantities is shown.

**Examples:**

**Inventory:**
- Bread (1)
- Milk (1)

**Recipes:**
- **Toast** requires Bread (1)
- **Sandwich** requires Bread (2), Egg (1)

`cook Sandwich`  
Returns:
```
Missing ingredients:
1. 1x Bread
2. 1x Egg
```

`cook toast`  
Returns:
```
Recipe successfully cooked: toast. Ingredients have been deducted from inventory.
```


---

## 🔍 Handling Similar Entries

### Adding, Deleting or Editing similar Ingredients (based on substring match)

When adding, editing, or deleting ingredients, KitchenCTRL will check for **similar ingredients** if an exact match is not found  
(e.g., `sugar` and `white sugar`).

If similar ingredients are found, you will be prompted to:

#### ➕ Add Ingredient
- **Type `0`** → Add this as a new ingredient.
- **Select an existing match** → Increase the quantity of the selected ingredient.
- **Type `-1`** → Cancel the operation.

#### ✏️ Edit Ingredient
- **Select an existing match** → Update the quantity of the selected ingredient.
- **Type `-1`** → Cancel the operation.

#### ❌ Delete Ingredient
- **Select an existing match** → Decrease the quantity of the selected ingredient.
- **Type `-1`** → Cancel the operation.

### Adding or Deleting similar Recipes (based on substring match)

When you try to add or edit a recipe, KitchenCTRL will check if any similar recipes already exist if an exact match is not found.

#### ➕ Add Recipe
If similar recipe names are found, you will be prompted to:

- **Add as new** → Creates a new recipe and jumps to editing the new recipe.
- **Select an existing match** → Jumps to editing the selected existing recipe.
- **Cancel** → Does nothing and returns to the previous state.

#### ❌ Delete Recipe
If similar recipe names are found, you will be prompted to:

- **Delete the similar recipe** → Returns back to RECIPEBOOK screen.
- **Cancel**→ Does nothing and returns to the previous state.

---


# 💾 Data Storage

All data is stored locally in `.txt` files under the `/data/` folder:

- `inventory.txt`
- `recipe_book.txt`

All ingredient descriptions and recipe names must be unique (case-insensitive).

## 🟢 Load Behavior
- Files are **only read on startup**.
- Their contents are imported into the program.
- **Any modification to the files while the program is running will be ignored.**

## 🔴 Save Behavior
- Files are **only saved when the program exits properly**, indicated by the message:  
  **"Goodbye, see you soon!"**
- The save operation **overwrites** the existing file contents.

## ⚙️ Manual Editing
- Users may update the files **only while the program is not running**.
- This allows for:
  - Speed editing
  - Pre-loading inventory or recipes
  - Programmatic file generation using external scripts

---

## 📄 File Format: `inventory.txt`
```
<ingredient description> (<qty>)
```

**Example:**
```
apple red (1)
apple (1)
apple 1 (1)
```

---

## 📄 File Format: `recipe_book.txt`
```
<recipeName1>
<item1> (<qty1>)
<item2> (<qty2>)

<recipeName2>
<item3> (<qty3>)
<item4> (<qty4>)
```

**Example:**
```
apple_pie
apple (3)
sugar (1)

fruit_salad
apple (1)
banana (2)
```
---

## ⚠️ Handling Large Files or Quantities

To maintain performance and prevent abuse, the system enforces strict upper limits on quantities and data sizes:

### Ingredient Quantity Limits
- Each `Ingredient` quantity is **capped at 99,999**.
- If an operation exceeds this cap (e.g., `60,000 + 70,000`), the final quantity will be **set to 99,999** and **any excess will be discarded silently**.
- Negative or zero quantities are not allowed during additions.

### Catalogue Size Limits
- `Inventory` and each `Recipe` can contain **a maximum of 100 ingredients**.
- The `RecipeBook` can hold **up to 100 recipes**.
- Attempts to add more than the allowed number will be ignored, and the user will be notified if the operation is done via UI.

### Save File Restrictions
Users are discouraged from manually modifying save files. If corrupted or oversized data is loaded (e.g., ingredients > 100, quantity > 99,999), the following rules apply:
- **Only the first items** that fit in any list will be loaded. (eg. only the first 100 ingredients will be loaded into inventory);+
- Any ingredient or recipe exceeding size or quantity limits will be **ignored entirely** during parsing.
- An error message will be shown to the user, before the ASCII art, to inform them of any skipped entries

> ⚠️ This ensures that manual tampering or unintended data corruption does not crash the application or create unstable states.


## 📋 Command Summary

| Screen                     | Command                                      | Description                                                             |
|----------------------------|----------------------------------------------|-------------------------------------------------------------------------|
| Main Menu                  | `inventory`                                  | Switch to the inventory screen                                          |
| Main Menu                  | `recipe`                                     | Switch to the recipebook screen                                         |
| Any (except Main Menu)     | `back`                                       | Return to the previous screen                                           |
| Any                        | `bye`                                        | Exit the application                                                    |
| Any                        | `help`                                       | Display available commands for the current screen                       |
| Inventory                  | `list`                                       | List all ingredients in inventory                                       |
| Inventory                  | `find [keyword]`                             | Find ingredients by keyword (partial, case-insensitive)                 |
| Inventory                  | `add [qty] [ingredient description]`         | Add a new ingredient to inventory                                       |
| Inventory                  | `delete [qty] [ingredient description]`      | Delete a specified quantity of an ingredient                            |
| Inventory                  | `edit [qty] [ingredient description]`        | Set the quantity of a given ingredient                                  |
| Inventory                  | `cookable`                                   | List all recipes that can be made with current ingredients              |
| Inventory                  | `cook [recipe_name]`                         | Attempt to cook a recipe and deduct ingredients                         |
| RecipeBook                 | `list`                                       | List all recipes                                                        |
| RecipeBook                 | `find [keyword]`                             | Find recipes by keyword (partial, case-insensitive)                     |
| RecipeBook                 | `add [recipe_name]`                          | Add a new recipe and enter recipe edit mode                             |
| RecipeBook                 | `delete [recipe_name]`                       | Delete a recipe from RecipeBook                                         |
| RecipeBook                 | `edit [recipe_name]`                         | Edit the specified recipe (enters Recipe screen)                        |
| RecipeBook                 | `cookable`                                   | List all recipes that can be made with current ingredients              |
| RecipeBook                 | `cook [recipe_name]`                         | Attempt to cook a recipe and deduct ingredients                         |
| Recipe (edit)              | `list`                                       | List ingredients for the selected recipe                                |
| Recipe (edit)              | `find [keyword]`                             | Find ingredients in the recipe by keyword (partial, case-insensitive)   |
| Recipe (edit)              | `add [qty] [ingredient description]`         | Add an ingredient to the recipe                                         |
| Recipe (edit)              | `delete [qty] [ingredient description]`      | Remove an ingredient from the recipe                                    |
| Recipe (edit)              | `edit [qty] [ingredient description]`        | Edit the quantity of an ingredient in the recipe                        |
| Recipe (edit)              | `cook`                                       | Cook the currently active recipe and deduct ingredients from inventory  |

---

## Conclusion
KitchenCTRL provides a structured way to manage kitchen inventory and recipes. By following the commands outlined, users can efficiently maintain their ingredient stocks and meal planning.

For any issues, refer to error messages or consult the developer documentation.
