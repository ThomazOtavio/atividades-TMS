package com.teixeira.tdd.currency;

public class AmbiguousCurrencyException extends CurrencyResolutionException {
    public AmbiguousCurrencyException(String message) { super(message); }
}
