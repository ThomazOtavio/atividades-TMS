# Calculator Advanced (JUnit 5) — IntelliJ / Maven

Extends the calculator with **square root** and **power** and adds a comprehensive test suite using different JUnit 5 alternatives (parameterized, method sources, nested, dynamic tests, assertAll).

## What’s included
- `Calculator` methods: `add`, `subtract`, `multiply`, `divide`, `sqrt`, `pow`.
- Rules:
  - Reject `NaN` inputs.
  - Division by zero → `IllegalArgumentException`.
  - `sqrt(x)` only for `x >= 0` (negatives → `IllegalArgumentException`).
  - `pow(base, exp)`:
    - `base < 0` only if `exp` is an integer.
    - `0^exp` allowed **only** for `exp > 0` (`0^0` and `0^(-k)` → `IllegalArgumentException`).
    - Overflow/underflow to `Infinity`/`NaN` → `ArithmeticException`.
- Tests:
  - **SqrtTests**: cases with integers, decimals, and boundary values (`Double.MIN_VALUE`, `Double.MAX_VALUE`).
  - **PowTests**: integers, decimals, negatives, boundary values; invalid scenarios (negative base with fractional exponent, `0^0`, `0^(-1)`, overflow).
  - **Dynamic tests** checking identity `sqrt(x^2) = |x|` on a sample set.
  - Example with `assertAll` for grouped assertions.

## Open in IntelliJ
1. **File → New → Project from Existing Sources…** and select `pom.xml`.
2. Ensure Project SDK is JDK 17+.
3. Run tests from the green gutter icons or via **Maven → Lifecycle → test**.

## CLI
```bash
mvn -q -DskipTests=false test
```
