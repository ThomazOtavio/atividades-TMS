package kata;

/** A multi-buy offer like "3 for 130". */
public record Offer(int qty, int price) implements Comparable<Offer> {
    public Offer {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
    }
    @Override public int compareTo(Offer o) {
        return Integer.compare(o.qty, this.qty); // descending by qty
    }
}
