# KitchenCTRL User Guide

## Introduction

KitchenCTRL is a command-line application designed to manage an inventory of ingredients and recipes. Users can add, delete, and list ingredients, as well as manage recipes efficiently.

## Quick Start

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `KitchenCTRL` from [here](https://github.com/AY2425S2-CS2113-T13-1/tp).

## Features

- Add, delete, and list ingredients in the inventory.

- Add and delete recipes.

- Handle ingredient quantities.

- Manage shopping lists.

- Load and save inventory and recipe catalogues.

## User Commands

### Adding an Ingredient: `add ingredient`
Adds a new ingredient to the inventory.

Format:
`add ingredient [name] [quantity]`
- If a similar ingredient exists, the system will prompt for confirmation.

Example of usage:

`add ingredient Sugar 2`
Adds 2 units of sugar to the inventory.

### Deleting an Ingredient: `delete ingredient`
Removes an ingredient from the inventory.

Format:
`delete ingredient [name] [quantity]`

Example of usage:

`delete ingredient Sugar 1`
Removes 1 unit of sugar from the inventory.

### Listing Ingredients: `list ingredients`
Displays all ingredients currently stored in the inventory.

Format:
`list ingredients`

### Adding a Recipe: `add recipe`
Creates a new recipe in the system.

Format:
`add recipe [recipe_name]`
- The system will prompt for the required ingredients.

Example Usage:
`add recipe ChocolateCake`

### Deleting a Recipe: `delete recipe`
Removes a recipe from the catalogue.

Format:
`delete recipe [recipe_name]`

Example Usage:
`delete recipe ChocolateCake`
Deletes the recipe for "ChocolateCake" from the system.

### Exiting the Application: `bye`
Terminates the program and saves the current state.

Format:
`bye`

## System Behavior
### Handling Similar Items
- If an ingredient with a similar name exists in the inventory, the system prompts the user to either:
    - Add as a new item.
    - Update an existing item.
    - Cancel the action.
### Error Handling
- If invalid input is entered, the system will prompt the user to enter a valid option.
- Commands must follow the exact format; otherwise, an error message will be displayed.

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

## Conclusion
KitchenCTRL provides a structured way to manage kitchen inventory and recipes. By following the commands outlined, users can efficiently maintain their ingredient stocks and meal planning.

For any issues, refer to error messages or consult the developer documentation.
