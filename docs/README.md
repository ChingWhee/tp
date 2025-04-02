# KitchenCTRL

KitchenCTRL is a Java-based application designed to help users manage their kitchen inventory, recipes, and shopping lists efficiently. The application provides a command-line interface for users to add, delete, and list ingredients and recipes, as well as navigate between different sections of the application.

## Features

- **Inventory Management**: Add, delete, and list ingredients in your kitchen inventory.
- **Recipe Management**: Create, delete, and list recipes.
[//]: # (- **Shopping List**: Maintain a shopping list for ingredients you need to buy.)
- **Data Persistence**: All data is stored locally in text files and is automatically saved after each command.

## Useful Links

* [User Guide](UserGuide.md)
* [Developer Guide](DeveloperGuide.md)
* [About Us](AboutUs.md)

## Getting Started

### Prerequisites

- JDK 17 (use the exact version)
- IntelliJ IDEA (latest version recommended)

### Setting Up the Project

1. **Ensure IntelliJ JDK 17 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).
2. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
3. **Verify the setup**: After importing, locate the `src/main/java/ui/KitchenCTRL.java` file, right-click it, and choose `Run KitchenCTRL.main()`. You should see a welcome message from the application.

### Build Automation

This project uses Gradle for build automation and dependency management. Refer to the [Gradle Tutorial](https://se-education.org/guides/tutorials/gradle.html) for more information.

### Testing

- **I/O Redirection Tests**: Navigate to the `text-ui-test` directory and run the `runtest(.bat/.sh)` script.
- **JUnit Tests**: A skeleton JUnit test is provided in `src/test/java/test/KitchenCTRLTest.java`. Refer to the [JUnit Tutorial](https://se-education.org/guides/tutorials/junit.html) for more information.

### Checkstyle

A sample CheckStyle rule configuration is provided in this project. Refer to the [Checkstyle Tutorial](https://se-education.org/guides/tutorials/checkstyle.html) for more information.

### Continuous Integration

The project uses [GitHub Actions](https://github.com/features/actions) for CI. When you push a commit or create a PR, GitHub Actions will automatically build and verify the code.

## Documentation

The `/docs` folder contains the project documentation. To publish the documentation:

1. Go to your fork on GitHub.
2. Click on the `settings` tab.
3. Scroll down to the `GitHub Pages` section.
4. Set the `source` as `master branch /docs folder`.
5. Optionally, use the `choose a theme` button to select a theme for your documentation.
