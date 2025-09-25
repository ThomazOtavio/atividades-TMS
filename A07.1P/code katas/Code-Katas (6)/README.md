# Berlin Clock Kata (Java + JUnit 5)

Implements the Berlin Clock with 5 output rows:
1) Seconds: `Y` if even, `O` if odd
2) Five-hour row: 4 lamps `R`
3) One-hour row: 4 lamps `R`
4) Five-minute row: 11 lamps (`Y` for 5-min steps, `R` at 15/30/45)
5) One-minute row: 4 lamps `Y`

Input format: `HH:MM:SS` (24h). Special-case: `24:00:00` is allowed.

## Example
```
12:32:00
Y
RROO
RROO
YYRYYROOOOO
YYOO
```

## How to run
```bash
mvn -q -f pom.xml test
# Demo:
# java -cp target/test-classes:target/classes kata.BerlinClock 16:50:06
```

