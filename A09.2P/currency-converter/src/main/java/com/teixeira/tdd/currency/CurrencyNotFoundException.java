package com.teixeira.tdd.currency;

public class CurrencyNotFoundException extends CurrencyResolutionException {
    public CurrencyNotFoundException(String message) { super(message); }
}
