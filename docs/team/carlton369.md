# Carlton - Project Portfolio Page

## Overview
KitchenCTRL is a command-line application designed to help users manage their kitchen. It allows users to maintain
inventories of ingredients, store recipes, and track shopping lists. The application is implemented in Java and is
optimized for interaction via the console.

Given below are my contributions to the project:

### Summary of Contributions
* **New Feature**:  Implemented the CookableRecipesCommand to determine cookable recipes based on inventory.
  [\#69](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/69)
    * *What it does*:  The CookableRecipesCommand retrieves recipes from the RecipeBook and checks if they can be cooked using available ingredients in the Inventory. 
       A recipe is cookable if all required ingredients are present in sufficient quantity.
    * *Justification*: This feature enables users to efficiently determine which recipes they can prepare without manually checking their inventory. 
      It enhances usability by providing clear feedback on available cooking options.
    * *Highlights*:  The main challenge was transitioning from using an ArrayList<Recipe> to a RecipeBook for better modularity and integration with existing functionalities.
      I also ensured that the method listItems() correctly formats the output of cookable recipes.


* **New Feature**: Implemented the CookRecipeCommand to deduct ingredients when cooking a recipe.
  [\#29](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/29), [\#54](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/54)
    * *What it does*: The CookRecipeCommand ensures a recipe can be cooked by verifying ingredient availability in the Inventory. 
       It then deducts the necessary quantities while handling interactions across multiple classes, including Recipe, Ingredient, and Inventory. 
       If ingredients are missing, it provides a detailed list of shortages to show the user.
    * *Justification*: This feature allows users to simulate cooking by automatically reducing ingredient quantities, ensuring accurate inventory management. 
       It enhances realism and usability within the application.
    * *Highlights*: The key challenge was managing the flow of information across multiple classes to ensure correct inventory updates. 
       The command had to correctly retrieve, check, and modify ingredient data across different objects while preventing errors in data consistency. 
       This required careful handling of object references and state updates to avoid unintended side effects.



* **New Feature**: Implemented Ingredient class to manage ingredient data efficiently
  [\#14](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/14), [\#21](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/21)
    * *What it does*: The Ingredient class serves as the foundational data structure for managing ingredient information within the application. 
       It encapsulates essential attributes like the ingredient name and quantity, ensuring controlled access through getter and setter methods.
    * *Justification*: Establishing a dedicated Ingredient class early on provided a standardized way to handle ingredient-related data across multiple components, such as inventory and recipes. 
      This abstraction made it easier to integrate and modify ingredient-related functionalities.
    * *Highlights*: The challenge was designing a flexible yet structured class that could seamlessly interact with other components. 
      Since multiple classes—such as Inventory and Recipe—depend on Ingredient, I had to ensure that its implementation supported key operations like quantity updates while preventing unintended modifications. 
      This class later became a crucial part of the system, streamlining how ingredients were stored, retrieved, and modified.

* **New Feature**: Implemented ListCommandsCommand to improve User Experience
   [\#159](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/159)
    * *What it does*: This Command extracts the printing of all available commands upon after every command, and instead shows them once upon any screen change. <br>
       Users who have forgotten what commands are available to them in that current screen can simply input "help" to view the available commands once again.
    * *Justification*: Previous implementation of printing every available command after every input from the user clogged up the CLI, making it difficult for the user to deciper the results of their previous command. <br>
       By extracting the command printing, this allows the user to have a much cleaner interface and smooth user experience, while still giving them the option to view them should the user have need to.
    * *Highlights*: Had to keep track of all the available commands in each screen to prevent any commands from being "left out" and not made obvious to the user. <br>
       Any modifications to the commands in each section had to be reflected in this class accordingly. <br>      
       Additionally, also had to ensure that the user are still able to access the relevant information on request.

* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Carlton369&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21)


* **Documentation**:
    * User Guide:
        * Added documentation for the CookRecipeCommand class [\#54](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/54)
        * Added documentation for the CookableRecipesCommand class [\#69](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/69)
        * Added documentation for the handling similar entries command [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
    * Developer Guide:
        * Added implementation details of the `Commands` class. [\#59](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/59)
        * Added sequence diagram for an `Add` operation in the `Inventory` screen, and saving to file [\#178](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/178) [\#180](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/180/files)
        * Added UML diagram for the `Model` Package [\#178](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/178) [\#180](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/180/files)

* **Community**:
    * PRs reviewed (with non-trivial review comments): 
      [\#36](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/36),
      [\#27](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/27),
      [\#81](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/81)
      [\#184](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/184)
  
