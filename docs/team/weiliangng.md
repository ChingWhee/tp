# weiliangng - Project Portfolio Page

## Overview

KitchenCTRL is a Java-based command-line application for managing kitchen inventory and recipes. It supports inventory tracking, recipe storage, and provides a simple text interface optimized for usability and modular design.

Given below are my contributions to the project:

### Summary of Contributions

### New Feature: Command Parsing System
* **Files**: `Parser.java`
* **What it does**: Implemented a robust command parsing system that interprets user input and maps it to corresponding actions based on screen state (e.g., INVENTORY, RECIPEBOOK, RECIPE).
* **Justification**: This parsing mechanism ensures modular command handling and proper screen-specific behavior, improving UX consistency and scalability.
* **Highlights**: I handled multiple command contexts (inventory vs. recipe) using concise `switch` expressions and ensured graceful error handling for malformed commands.

### New Feature: Add and Delete Commands
* **Files**: `AddCommand.java`, `DeleteCommand.java`
* **What it does**: Enabled users to add and delete both ingredients and recipes depending on the screen. The `AddCommand` supports ingredient quantities and recipe creation, while `DeleteCommand` validates entries and removes items accordingly.
* **Justification**: Core functionality for kitchen management. Without this, users cannot dynamically update their inventory or recipe book.
* **Highlights**:
    * Used Java assertions for early bug detection and input validation.
    * Handled polymorphic behavior for Catalogue types: `Inventory`, `Recipe`, and `RecipeBook`.

### Refactoring: Model Component for Enhanced OOP
* **Files**: `Ingredient.java`, `Catalogue.java`, `Recipe.java`, `Inventory.java`, `RecipeBook.java`
* **What it does**: Refactored model classes to enforce OOP principles such as abstraction, encapsulation, and polymorphism. Ensured that ingredient handling logic is reusable across Inventory and Recipe through inheritance from `Catalogue<T>`.
* **Justification**: Promotes code reuse and modularity. By abstracting common behavior into the `Catalogue` class, we reduce duplication and improve maintainability.
* **Highlights**:
    * Centralized ingredient handling logic like `addItem`, `deleteItem`, and `findItem`.
    * Enforced data integrity in `Ingredient` with proper setters, equality checks, and validation.
    * Made `Recipe`, `Inventory`, and `RecipeBook` cleanly extend `Catalogue<T>`, leveraging polymorphic behavior for UI and command execution.

### Testing
* **Files**: `ParserTest.java`
* **What it does**: Developed comprehensive JUnit tests for the command parser, covering valid and invalid inputs for all screens.
* **Highlights**:
    * Validated correct instantiation of commands (e.g., `AddCommand`, `DeleteCommand`, `BackCommand`).
    * Ensured parsing logic reacts predictably to edge cases like missing or malformed input.

### Documentation
* **Developer Guide**: Wrote documentation and drew UML diagrams for Model.
* **User Guide**: Helped document supported commands for all screen states, detailing input formats and expected behavior.

### Community
* Reviewed and tested logic involving command transitions and error-handling mechanics for related features.
* Collaborated closely with team members integrating the UI, ensuring `Parser` output aligned with front-end prompts.

### Tools
* Refactored parser logic for readability and extensibility.
* Integrated PlantUML into the project
