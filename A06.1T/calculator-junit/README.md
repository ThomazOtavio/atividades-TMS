# Calculator (JUnit 5) — IntelliJ / Maven

Implements a simple calculator and a comprehensive JUnit 5 test suite.

## What’s included
- `Calculator` with: `add`, `subtract`, `multiply`, `divide`.
- Original `CalculatorTest` (your example) testing `add(10, 50) -> 60`.
- `CalculatorExtendedTest`:
  - Parameterized tests for add/subtract/multiply/divide
  - Invalid scenarios: division by zero, NaN inputs

## Open in IntelliJ
1. **File → New → Project from Existing Sources…** and select `pom.xml`.
2. Ensure the Project SDK is a JDK (17+ recommended).
3. Run tests:
   - From the green gutter icon in each test class; or
   - **Maven** tool window → **Lifecycle** → **test**.

## Notes on invalid scenarios
- Division by zero throws `IllegalArgumentException`.
- Any operation receiving `NaN` throws `IllegalArgumentException` (keeps results predictable in tests).

## CLI (optional)
```bash
mvn -q -DskipTests=false test
```
