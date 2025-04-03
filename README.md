# KitchenCTRL

KitchenCTRL is a Java-based application designed to help users manage their kitchen inventory and recipes efficiently. The application provides a command-line interface for users to add, delete, and list ingredients and recipes, as well as navigate between different sections of the application.

## Features

- **Inventory Management**: Add, delete, and list ingredients in your kitchen inventory.
- **Recipe Management**: Create, delete, and list recipes.
- **Data Persistence**: All data is stored locally in text files and is automatically saved after each command.



## Useful Links

* [User Guide](https://github.com/AY2425S2-CS2113-T13-1/tp/blob/master/docs/UserGuide.md)
* [Developer Guide](https://github.com/AY2425S2-CS2113-T13-1/tp/blob/master/docs/DeveloperGuide.md)
* [About Us](Ahttps://github.com/AY2425S2-CS2113-T13-1/tp/blob/master/docs/AboutUs.md)

## Getting Started

### Prerequisites

- JDK 17 (use the exact version)
- IntelliJ IDEA (latest version recommended)

### Setting Up the Project

1. **Ensure IntelliJ JDK 17 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).
2. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
3. **Verify the setup**: After importing, locate the `src/main/java/ui/KitchenCTRL.java` file, right-click it, and choose `Run KitchenCTRL.main()`. You should see a welcome message from the application as shown below.
```
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes

> Task :run
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
Enter command: 
```

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
