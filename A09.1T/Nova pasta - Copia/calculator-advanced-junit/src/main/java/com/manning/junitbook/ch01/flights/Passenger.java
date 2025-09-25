package com.manning.junitbook.ch01.flights;

import java.util.Objects;

/**
 * Passenger entity (immutable id, name).
 * Equality is based on id only, ensuring uniqueness per passenger.
 */
public final class Passenger {
    private final String id;
    private final String name;

    public Passenger(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Passenger id must be non-blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Passenger name must be non-blank.");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return id.equals(passenger.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Passenger{id='" + id + "', name='" + name + "'}";
    }
}
