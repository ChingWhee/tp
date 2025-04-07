# weiliangng - Project Portfolio Page

## Overview

KitchenCTRL is a Java-based command-line application for managing kitchen inventory and recipes. It supports inventory tracking, recipe storage, and provides a simple text interface optimized for usability and modular design.

Given below are my contributions to the project:

### Summary of Contributions

+ **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=weiliangng&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&since=2025-02-21&tabAuthor=weiliangng&tabRepo=AY2425S2-CS2113-T13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

+ **New Feature**: Enhanced UI Prompts and Help System
  + What it does: Displays contextual help messages and usage instructions based on the current screen.
  + Justification: Improves user experience by providing clear, relevant guidance and feedback.
  + Highlights: Includes dynamic command listings and user-friendly messages for both success and errors.


+ **Refactor**: IngredientCatalogue Abstract Base Class
  + What it does: Consolidates shared ingredient logic for Inventory and Recipe classes.
  + Justification: Promotes code reuse and consistent functionality across catalogues.
  + Highlights: Uses `getCatalogueLabel()` for dynamic context and separates UI (ConflictHelper) from core logic.
  + Challenges: The refactoring was challenging as it required extensive changes to existing code.


+ **New Feature**: Ingredient Quantity Adjustment via `editItem()`
  + What it does: Lets users update the quantity of an ingredient, including setting it to zero to remove it.
  + Justification: Provides more flexible and accurate control of ingredient amounts.
  + Highlights: Supports exact or similar matches and strictly validates input values.


+ **Refactor**: Improved Command Execution Routing
  + What it does: Routes `Add`, `Delete`, and `Edit` commands based on current screen state (Inventory or Recipe).
  + Justification: Avoids misrouting of operations and enables shared command usage across contexts.
  + Highlights: Includes validation and assertion checks to ensure correct catalogue usage.


+ **New Feature**: Recipe Selection and Entry (`EditRecipeCommand`)
  + What it does: Allows the user to enter editing mode for a specific recipe.
  + Justification: Streamlines the workflow for modifying recipe contents.
  + Highlights: Automatically sets the active recipe and transitions to the RECIPE screen.


+ **Bug Fixes**: Validation and Exception Handling Improvements
  + What it does: Adds input validation and error handling across commands and ingredient models.
  + Justification: Prevents runtime errors and ensures consistent, user-friendly feedback.
  + Highlights: Implements regex-based parsing, null/empty checks, and controlled exception reporting.
  + Challenges: Found multiple re-implementations of the same method, was challenging to unify the code


+ **Contribution to UG**
  + Refactored and expanded Table of Contents for clarity and completeness.
  + Reorganized navigation and CRUD command sections under clearer screen-based headings.
  + Improved formatting consistency and added examples for commands (`add`, `edit`, `cook`, etc.).
  + Clarified behavior for handling similar entries (ingredients and recipes).
  + Documented dual behavior for `cook` command depending on current screen.
  + Detailed how data is stored and edited manually in the system.


+ **Contribution to DG**
  + Explained the `Model` component architecture, including its key classes
  + Created and maintained the UML diagram (`model.puml`) representing the structure of the model.


+ **Review/mentoring contributions**:
  [PR comments link](https://nus-cs2113-ay2425s2.github.io/dashboards/contents/tp-comments.html)
  + Check under @weiliangng


+ **Contributions beyond the team project**
  + Reviewed PR extensively for other team during PED:
    [Link](https://github.com/weiliangng/ped/issues)
