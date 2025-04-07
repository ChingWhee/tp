# Chuan Hui - Project Portfolio Page

## Overview
KitchenCTRL is a command-line application designed to help users manage their kitchen. It allows users to maintain
inventories of ingredients, store recipes, and track shopping lists. The application is implemented in Java and is
optimized for interaction via the console.

Given below are my contributions to the project:

### Summary of Contributions

* **New Feature**: Implemented the `find` command for Inventory, RecipeBook, and Recipe.
* [\#68](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/68)
    * *What it does*: Allows users to search for ingredients or recipes by name in their respective catalogues using a keyword.
    * *Justification*: Improves usability by enabling efficient lookup of items without requiring exact names.
    * *Highlights*: Designed a generic `findItem` method in the abstract `Catalogue` class, and overrode it in `Inventory`, `Recipe`, and `RecipeBook` to match their content structures. Also created a `FindCommand` class to generalize the behavior.

* **New Feature**: RecipeBook enhancements with proper error handling and null safety.
* [\#68](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/68)
    * *What it does*: Strengthened the `addItem`, `deleteItem`, and `editItem` methods in `RecipeBook` with robust checks for null, empty names, and duplicates.
    * *Justification*: Prevents system crashes from malformed user input, improving overall application stability.
    * *Highlights*: Refactored `getRecipeNameLowercase()` to validate name integrity before comparison.

* **Refactoring of `Parser`**:
    * *What it does*: Refactored the `Parser` class to strictly enforce command syntax, ensuring that commands which are designed to be used without arguments (such as `list`, `back`, `help`, `bye`) now throw an error if extra input is provided.
    * *Justification*: Prevents user confusion and potential bugs caused by invalid command formats like `list extra`, which previously would be accepted silently. Enforcing strict validation improves command clarity and predictability.
    * *Highlights*:
        - Switched from keyword-based parsing (`command`, `args`) to full command-line validation (`commandLine`) in the `parseWelcomeCommand` method.
        - Applied the same validation logic to screen-specific methods such as `parseInventoryCommand`, `parseRecipeBookCommand`, and `parseRecipeCommand`.
        - Used structured switch-cases and `yield` expressions to enforce correct usage with clear, actionable error messages for commands misused with extra input.
        - Removed unused parsing methods like `prepareUpdate`, replacing them with context-aware `prepareEdit` that handles `edit` functionality with proper argument checking.

* **Code Contributed**: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=[YourGitHubName]&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21)

* **Documentation**:
    * User Guide:
        * Added usage instructions for the `find` command in the Inventory, RecipeBook, and Recipe screens.
    * Developer Guide:
        * Documented the design, implementation, Architecture diagram, and PlantUML object diagram for `RecipeBook`.
        * Documented new methods including `findItem`.

* **Testing**:
    * Added unit tests in:
        - `RecipeBookTest`
        - `RecipeTest`
        - `InventoryTest`
    * Ensured edge cases like empty query and non-matching names are handled.

* **Community**:
    * PRs reviewed (with suggestions and feedback):
        * [\#50](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/50)
        * [\#35](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/35)
        * [\#93](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/93)

* **Tools**:
    * Integrated test cases with JUnit 5 and followed Checkstyle coding standards.
    * Helped improve test coverage of `UiTest`, `InputParserTest` and other unit testing for different screen prompts.

