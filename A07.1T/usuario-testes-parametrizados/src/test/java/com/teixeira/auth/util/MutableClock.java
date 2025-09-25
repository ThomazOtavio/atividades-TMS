package com.teixeira.auth.util;

import java.time.*;

public class MutableClock extends Clock {
    private Instant currentInstant;
    private final ZoneId zone;

    public MutableClock(Instant initialInstant, ZoneId zone) {
        this.currentInstant = initialInstant;
        this.zone = zone;
    }

    public static MutableClock nowUTC() {
        return new MutableClock(Instant.now(), ZoneOffset.UTC);
    }

    @Override
    public ZoneId getZone() { return zone; }

    @Override
    public Clock withZone(ZoneId zone) { return new MutableClock(currentInstant, zone); }

    @Override
    public Instant instant() { return currentInstant; }

    public void advanceSeconds(long seconds) { currentInstant = currentInstant.plusSeconds(seconds); }
    public void advanceMillis(long millis) { currentInstant = currentInstant.plusMillis(millis); }
}
