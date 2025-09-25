package com.teixeira.tdd.calc;

public class CalcStats {

    public static Stats analyze(int[] values) {
        if (values == null) throw new NullPointerException("values");
        if (values.length == 0) throw new IllegalArgumentException("empty sequence");

        int min = values[0];
        int max = values[0];
        long sum = 0L;

        for (int v : values) {
            if (v < min) min = v;
            if (v > max) max = v;
            sum += v;
        }

        int count = values.length;
        double avg = sum / (double) count;
        return new Stats(min, max, count, avg);
    }

    public record Stats(int min, int max, int count, double average) { }
}
