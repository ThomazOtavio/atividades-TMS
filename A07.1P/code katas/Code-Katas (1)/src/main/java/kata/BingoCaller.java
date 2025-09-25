package kata;

import java.util.*;

/** Calls numbers 1..75 in random order, without repetition. */
public final class BingoCaller {
    private final Queue<Integer> queue;

    public BingoCaller(Random rnd) {
        Objects.requireNonNull(rnd, "rnd");
        List<Integer> nums = new ArrayList<>(75);
        for (int i = 1; i <= 75; i++) nums.add(i);
        Collections.shuffle(nums, rnd);
        this.queue = new ArrayDeque<>(nums);
    }

    /** Returns the next called number, or null if exhausted. */
    public Integer next() {
        return queue.poll();
    }

    /** Remaining count. */
    public int remaining() {
        return queue.size();
    }
}
