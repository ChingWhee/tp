# Ching Whee - Project Portfolio Page

## Overview
KitchenCTRL is a command-line application designed to help users manage their kitchen. It allows users to maintain 
inventories of ingredients, store recipes, and track shopping lists. The application is implemented in Java and is 
optimized for interaction via the console.

Given below are my contributions to the project:

### Summary of Contributions
* **New Feature**: Implemented the user interface and screen state management for KitchenCTRL.
    [\#45](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/45)
    * *What it does*: The UI component of KitchenCTRL handles all user input/output interactions and manages the 
       transitions between different screens.
    * *Justification*: The UI provides a command-line interface that enhances user interaction with the application. 
       Screen state management ensures smooth transitions between different sections of the app.
    * *Highlights*: The challenge was ensuring proper handling of screen states, where commands in one state would 
       trigger transitions to another state. I also had to integrate these transitions seamlessly with other components, 
       such as the inventory and recipe management functionalities.


* **New Feature**: Ingredient quantity management with user input handling. 
    [\#20](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/20), [\#27](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/27)
    * *What it does*: This feature allows the application to automatically add or delete ingredients from the inventory,
       handling cases where multiple similar items exist. When an ingredient is added or deleted, the user is prompted 
       to either add it as a new entry, update the existing item, or cancel the action.
    * *Justification*: This feature enhances the user experience by reducing manual input errors, streamlining the 
       process of adding and deleting ingredients. It also handles similar ingredients more effectively, allowing users 
       to manage duplicates and prevent inconsistencies in the inventory.
    * *Highlights*: The challenge was ensuring proper handling of screen states, where commands in one state would
      trigger transitions to another state. I also had to integrate these transitions seamlessly with other components,
      such as the inventory and recipe management functionalities.


* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=ChingWhee&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21)


* **Documentation**:
    * User Guide:
        * Added documentation for the inventory commands `add`, `delete`,  and `list` [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
        * Added documentation for the navigation commands `inventory`, `recipe`,  and `back` [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
        * Added documentation for the handling similar entries command [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
    * Developer Guide:
        * Added implementation details of the `UI` feature. [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)


* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#18](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/18), 
      [\#26](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/26), [\#53](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/53), 
      [\#54](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/54)


* **Tools**:
    * Integrated PlantUML into the project