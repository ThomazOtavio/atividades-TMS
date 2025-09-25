package com.teixeira.tdd.unit;

import java.util.Locale;
import java.util.Map;

public class UnitConverter {

    // ===== Length =====
    public enum LengthUnit {
        M(1.0),          // meter as base
        KM(1000.0),
        CM(0.01),
        MI(1609.344),
        IN(0.0254);      // 1 in = 2.54 cm = 0.0254 m

        final double toMeters;
        LengthUnit(double toMeters) { this.toMeters = toMeters; }
        double toBase(double v) { return v * toMeters; }
        double fromBase(double meters) { return meters / toMeters; }

        static LengthUnit resolve(String s) {
            try {
                return LengthUnit.valueOf(s.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                throw new InvalidUnitException("Invalid length unit: " + s);
            }
        }
    }

    // ===== Mass =====
    public enum MassUnit {
        G(1.0),            // gram as base
        KG(1000.0),
        LB(453.59237),     // lb defined as exactly 0.45359237 kg
        OZ(28.349523125);  // 1 oz = 1/16 lb = 28.349523125 g

        final double toGrams;
        MassUnit(double toGrams) { this.toGrams = toGrams; }
        double toBase(double v) { return v * toGrams; }
        double fromBase(double grams) { return grams / toGrams; }

        static MassUnit resolve(String s) {
            try {
                return MassUnit.valueOf(s.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                throw new InvalidUnitException("Invalid mass unit: " + s);
            }
        }
    }

    // ===== Temperature =====
    public enum TemperatureUnit {
        C, F, K;

        static TemperatureUnit resolve(String s) {
            try {
                return TemperatureUnit.valueOf(s.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                throw new InvalidUnitException("Invalid temperature unit: " + s);
            }
        }
    }

    public double convertLength(double value, String from, String to) {
        LengthUnit f = LengthUnit.resolve(from);
        LengthUnit t = LengthUnit.resolve(to);
        double meters = f.toBase(value);
        return t.fromBase(meters);
    }

    public double convertMass(double value, String from, String to) {
        MassUnit f = MassUnit.resolve(from);
        MassUnit t = MassUnit.resolve(to);
        double grams = f.toBase(value);
        return t.fromBase(grams);
    }

    public double convertTemperature(double value, String from, String to) {
        TemperatureUnit f = TemperatureUnit.resolve(from);
        TemperatureUnit t = TemperatureUnit.resolve(to);

        if (f == t) return value;
        double celsius;

        // normalize to Celsius
        switch (f) {
            case C: celsius = value; break;
            case K: celsius = value - 273.15; break;
            case F: celsius = (value - 32.0) * 5.0 / 9.0; break;
            default: throw new InvalidUnitException("Invalid temperature unit: " + from);
        }

        // convert from Celsius to target
        switch (t) {
            case C: return celsius;
            case K: return celsius + 273.15;
            case F: return celsius * 9.0 / 5.0 + 32.0;
            default: throw new InvalidUnitException("Invalid temperature unit: " + to);
        }
    }
}
