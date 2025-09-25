package kata;

import java.util.*;

/** Collection of pricing rules by SKU. */
public final class PricingRules {
    private final Map<Character, ItemPricing> rules = new HashMap<>();

    public PricingRules add(char sku, int unitPrice, Offer... offers) {
        rules.put(Character.toUpperCase(sku), new ItemPricing(unitPrice, Arrays.asList(offers)));
        return this;
    }

    public ItemPricing forSku(char sku) {
        ItemPricing p = rules.get(Character.toUpperCase(sku));
        if (p == null) throw new IllegalArgumentException("Unknown SKU: " + sku);
        return p;
    }

    public Set<Character> skus() {
        return Collections.unmodifiableSet(rules.keySet());
    }
}
