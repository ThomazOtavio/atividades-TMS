package com.manning.junitbook.ch01.flights;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple Flight aggregate holding a set of unique passengers
 * with a fixed capacity.
 */
public class Flight {

    private final String code;
    private final int capacity;
    private final Set<Passenger> passengers = new LinkedHashSet<>();

    public Flight(String code, int capacity) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Flight code must be non-blank.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0.");
        }
        this.code = code;
        this.capacity = capacity;
    }

    public String getCode() { return code; }
    public int getCapacity() { return capacity; }

    public int getPassengerCount() { return passengers.size(); }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers.stream().collect(Collectors.toList()));
    }

    /**
     * Adds a passenger if there is capacity and the passenger is not already present.
     * @return true if added; false if duplicate or capacity reached.
     * @throws IllegalArgumentException if passenger is null
     */
    public boolean addPassenger(Passenger p) {
        if (p == null) throw new IllegalArgumentException("Passenger cannot be null.");
        if (passengers.size() >= capacity) return false;
        return passengers.add(p);
    }

    /** Removes a passenger; returns true if removed. */
    public boolean removePassenger(Passenger p) {
        if (p == null) return false;
        return passengers.remove(p);
    }

    /** Convenience: is the passenger already on the flight? */
    public boolean contains(Passenger p) {
        return passengers.contains(p);
    }
}
