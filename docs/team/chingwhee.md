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


* **New Feature**: Ingredient conflict resolution with user-guided quantity updates.
    [\#20](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/20), [\#27](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/27)
    * *What it does*: This feature enables users to add or delete ingredients while resolving conflicts with similar 
       existing items in the inventory. When a similar match is found, the application prompts the user to either 
       update the existing entry, add the item as a new entry, or cancel the operation entirely. For editing, users can 
       select the exact item from a list of similar ingredients and set the new quantity.

    * *Justification*: This improves user experience by preventing accidental duplication of ingredients and reducing 
       manual corrections. It also ensures inventory consistency by guiding users through potential conflicts, 
       especially when similar ingredient names exist.

    * *Highlights*: The main challenge was designing a clear and intuitive user flow for conflict resolution and 
       selection among similar items. I also had to integrate the logic with existing catalogue structures and ensure 
       that user decisions (e.g., canceling or updating) were reflected correctly without breaking the command 
       execution chain.


* **Testing**: UI and Parser Unit Test
    [\#50](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/50)
  * *What it does*: This testing feature ensures that the `Ui` and `Parser` classes function correctly by covering a 
     wide range of user inputs across all screens. It verifies valid command handling and also checks the application's 
     response to invalid, empty, or unexpected inputs, ensuring robustness in both normal and edge cases.

  * *Justification*: These tests are critical for maintaining application reliability. Since the UI and parser are the 
     first points of interaction with the user, any issues here can lead to confusing behavior or system crashes. 
     Thorough testing ensures that user inputs are interpreted correctly and error messages are shown when needed, 
     improving both usability and stability.

  * *Highlights*: One challenge was testing `Ui` methods that rely on console input and output. To handle this, I used 
     dependency injection via input streams and mock outputs to simulate user behavior. For `ParserTest`, I had to 
     account for the contextual behavior of commands across different screens and ensure that incorrect inputs triggered 
     the appropriate exceptions and feedback.


* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=ChingWhee&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21)


* **Documentation**:
    * User Guide:
        * Added documentation for the inventory commands `add`, `delete`,  and `list` [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
        * Added documentation for the navigation commands `inventory`, `recipe`,  and `back` [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
        * Added documentation for the handling similar entries command [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47)
    * Developer Guide:
        * Added implementation details and UML diagram of the `UI` feature. [\#47](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/47), [\#192](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/192)
        * Added implementation details and UML diagram of the `Commands` feature. [\#192](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/192)


* **Community**:
    * PRs reviewed (with non-trivial review comments): \
      * [\#18](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/18)
      * [\#26](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/26)
      * [\#53](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/53)
      * [\#54](https://github.com/AY2425S2-CS2113-T13-1/tp/pull/54)


* **Tools**:
    * Integrated PlantUML into the project
    * Integrated test cases with JUnit 5 and followed Checkstyle coding standards.
