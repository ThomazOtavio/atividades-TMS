package kata;

import java.util.*;

/** Checkout that scans SKUs and computes total according to PricingRules. */
public final class Checkout {
    private final PricingRules pricing;
    private final Map<Character, Integer> counts = new HashMap<>();

    public Checkout(PricingRules pricing) {
        this.pricing = Objects.requireNonNull(pricing);
    }

    /** Scan a single SKU (letter). */
    public void scan(char sku) {
        if (!Character.isLetter(sku)) throw new IllegalArgumentException("SKU must be a letter");
        counts.merge(Character.toUpperCase(sku), 1, Integer::sum);
    }

    /** Convenience: scans a string like "ABCDABAA". */
    public void scan(String items) {
        if (items == null) return;
        for (char c : items.toCharArray()) {
            if (Character.isWhitespace(c)) continue;
            scan(c);
        }
    }

    /** Calculates the total price in integer currency units (e.g., cents). */
    public int total() {
        int sum = 0;
        for (var e : counts.entrySet()) {
            ItemPricing p = pricing.forSku(e.getKey());
            sum += p.priceFor(e.getValue());
        }
        return sum;
    }

    /** Clears the basket. */
    public void clear() {
        counts.clear();
    }
}
