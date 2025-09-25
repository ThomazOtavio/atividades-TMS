package com.teixeira.tdd.currency;

import java.math.BigDecimal;
import java.util.Objects;

/** Serviço de conversão amigável para o usuário, aceitando nomes ou países. */
public class CurrencyConversionService {
    private final CurrencyConverter converter;
    private final CurrencyResolver resolver;

    public CurrencyConversionService(ExchangeRateProvider provider, CurrencyResolver resolver) {
        this.converter = new CurrencyConverter(Objects.requireNonNull(provider));
        this.resolver = Objects.requireNonNull(resolver);
    }

    public BigDecimal convert(String fromText, String toText, BigDecimal amount) {
        String from = resolver.resolve(fromText);
        String to = resolver.resolve(toText);
        return converter.convert(from, to, amount);
    }
}
