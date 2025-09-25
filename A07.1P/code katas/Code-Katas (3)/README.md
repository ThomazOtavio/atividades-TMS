# FizzBuzz Kata (Java + JUnit 5)

This project implements the classic FizzBuzz rules:

- If a number is divisible by 3 ⇒ **Fizz**
- If a number is divisible by 5 ⇒ **Buzz**
- If divisible by both 3 and 5 ⇒ **FizzBuzz**
- Otherwise ⇒ the number itself

## How to run

```bash
mvn -q -f pom.xml test
# Or print the sequence 1..100
mvn -q -e -Dexec.mainClass=kata.FizzBuzz exec:java
```

(If `exec-maven-plugin` isn't configured, you can also run the `main` method from an IDE.)

## Files
- `kata/FizzBuzz.java` – implementation (`of(int)` and `generate(int,int)`).
- `kata/FizzBuzzTest.java` – unit tests.

