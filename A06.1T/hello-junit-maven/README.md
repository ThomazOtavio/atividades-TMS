# Hello JUnit (Maven + IntelliJ)

This is a minimal project for the activity **HELLO, WORLD TESTS!** using JUnit 5.

## What it contains
- `HelloJUnit` class with method `sayHello()` returning `"Hello, World of Tests!"`.
- `HelloJUnitTest` with one test asserting the expected return.
- Maven configured with JUnit 5 and Surefire.

## Open and run in IntelliJ IDEA
1. **File → New → Project from Existing Sources...** and select the `pom.xml` in this folder.
2. Accept the defaults to import as a **Maven** project.
3. Optional: Check **Project SDK** is a JDK (17+ recommended).
4. Run tests:
   - Click the green gutter icon next to `HelloJUnitTest` → **Run**; or
   - Open the **Maven** tool window → **Lifecycle** → **test**.

## CLI (optional)
```bash
mvn -q -DskipTests=false test
```

