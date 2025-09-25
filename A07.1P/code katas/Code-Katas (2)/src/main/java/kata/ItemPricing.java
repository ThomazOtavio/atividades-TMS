package kata;

import java.util.*;

/** Pricing for a single SKU: unit price plus optional multi-buy offers. */
public final class ItemPricing {
    private final int unitPrice;
    private final List<Offer> offers;

    public ItemPricing(int unitPrice, List<Offer> offers) {
        if (unitPrice < 0) throw new IllegalArgumentException("unitPrice must be >= 0");
        this.unitPrice = unitPrice;
        List<Offer> sorted = new ArrayList<>(offers == null ? List.of() : offers);
        Collections.sort(sorted); // largest qty first
        this.offers = Collections.unmodifiableList(sorted);
    }

    public int unitPrice() { return unitPrice; }
    public List<Offer> offers() { return offers; }

    /** Computes the price for a given quantity applying offers greedily (largest qty first). */
    public int priceFor(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");
        int remaining = quantity;
        int total = 0;
        for (Offer o : offers) {
            if (o.qty() <= 0) continue;
            int packs = remaining / o.qty();
            if (packs > 0) {
                total += packs * o.price();
                remaining -= packs * o.qty();
            }
        }
        total += remaining * unitPrice;
        return total;
    }
}
