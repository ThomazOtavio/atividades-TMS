# Bingo Kata (Java + JUnit 5)

Implements a standard US Bingo (75-ball) card and gameplay utilities.

## Features
- `BingoCard.generate(Random)` – builds a 5x5 card (B:1–15, I:16–30, N:31–45, G:46–60, O:61–75), center is **FREE** (pre-marked).
- `BingoCard.mark(int)` – marks a called number.
- `BingoCard.hasBingo()` – detects win on rows, columns, or diagonals.
- `BingoCaller` – calls 1..75 in random order without repetition.
- `BingoGame` – small demo to play until Bingo.

## How to run
```bash
mvn -q -f pom.xml test
# Demo (seeded):
# java -cp target/test-classes:target/classes kata.BingoGame 123
```

