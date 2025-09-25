package com.teixeira.tdd.currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider simples em memória para uso manual/demos.
 * Não é utilizado pelos testes (que usam mocks).
 */
public class InMemoryExchangeRateProvider implements ExchangeRateProvider {
    private final Map<String, BigDecimal> rates = new ConcurrentHashMap<>();

    private static String key(String from, String to) {
        return from.toUpperCase() + "->" + to.toUpperCase();
    }

    public InMemoryExchangeRateProvider put(String from, String to, BigDecimal rate) {
        Objects.requireNonNull(from); Objects.requireNonNull(to); Objects.requireNonNull(rate);
        rates.put(key(from, to), rate);
        return this;
    }

    @Override
    public BigDecimal getRate(String from, String to) throws ExchangeRateUnavailableException {
        BigDecimal r = rates.get(key(from, to));
        if (r == null) throw new ExchangeRateUnavailableException("No rate for " + from + "->" + to);
        return r;
    }
}
