# Rock Paper Scissors Kata (Java + JUnit 5)

Implements the classic **Rock–Paper–Scissors** rules and a simple **best-of series** helper.

## API
- `RockPaperScissors.play(Move p1, Move p2)` → `Outcome`
- `RockPaperScissors.parse(String)` → `Move` (case-insensitive; accepts shortcuts `r/p/s`)
- `RpsSeries` to run a best-of-N (N odd) series; draws don't count.

## How to run
```bash
mvn -q -f pom.xml test
# Demo:
# java -cp target/test-classes:target/classes kata.RockPaperScissors rock scissors
```

