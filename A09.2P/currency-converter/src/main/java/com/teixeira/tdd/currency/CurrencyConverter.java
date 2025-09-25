package com.teixeira.tdd.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.regex.Pattern;

public class CurrencyConverter {
    private static final Pattern CODE = Pattern.compile("[A-Za-z]{3}");
    private static final int SCALE = 2;
    private final ExchangeRateProvider provider;

    public CurrencyConverter(ExchangeRateProvider provider) {
        this.provider = Objects.requireNonNull(provider, "provider");
    }

    /**
     * Converte `amount` de `from` para `to` usando a taxa do provider.
     * Usa arredondamento HALF_UP para 2 casas decimais.
     */
    public BigDecimal convert(String from, String to, BigDecimal amount) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(amount, "amount");
        if (amount.signum() < 0) throw new IllegalArgumentException("amount must be >= 0");

        String f = normalize(from);
        String t = normalize(to);

        if (f.equals(t)) {
            return amount.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal rate = provider.getRate(f, t);
        return amount.multiply(rate).setScale(SCALE, RoundingMode.HALF_UP);
    }

    private String normalize(String code) {
        String c = code.trim().toUpperCase();
        if (!CODE.matcher(c).matches()) {
            throw new IllegalArgumentException("invalid currency code: " + code);
        }
        return c;
    }
}
