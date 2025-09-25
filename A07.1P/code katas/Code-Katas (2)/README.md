# CodeKata 09 – Back to the Checkout (Java + JUnit 5)

Implements a checkout with unit prices and multi-buy offers.
Default rules (from the kata examples):
- A = 50 (3 for 130)
- B = 30 (2 for 45)
- C = 20
- D = 15

## API
- `PricingRules` – register SKUs, unit prices, and offers.
- `Checkout.scan(char)` / `scan(String)` – add items.
- `Checkout.total()` – compute the total.

## How to run
```bash
mvn -q -f pom.xml test
```

You can change prices/offers in the test or create your own `main` to try different baskets.
