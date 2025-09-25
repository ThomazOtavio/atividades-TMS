package com.teixeira.tdd.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceTest {

    @Mock
    ExchangeRateProvider provider;

    CurrencyConversionService service;
    CurrencyResolver resolver;

    @BeforeEach
    void setup() {
        resolver = new CurrencyResolver();
        service = new CurrencyConversionService(provider, resolver);
    }

    @Test
    @DisplayName("Converte usando nomes amigáveis: Dólar Americano -> Real Brasileiro")
    void convertsUsingFriendlyNames() {
        when(provider.getRate("USD","BRL")).thenReturn(new BigDecimal("5.0"));
        BigDecimal out = service.convert("Dólar Americano", "Real Brasileiro", new BigDecimal("10"));
        assertEquals(new BigDecimal("50.00"), out);
    }

    @Test
    @DisplayName("Converte usando nomes de países: Estados Unidos -> Brasil")
    void convertsUsingCountryNames() {
        when(provider.getRate("USD","BRL")).thenReturn(new BigDecimal("5.0"));
        BigDecimal out = service.convert("Estados Unidos", "Brasil", new BigDecimal("10"));
        assertEquals(new BigDecimal("50.00"), out);
    }

    @Test
    @DisplayName("Mistura código e nome: usd -> euro")
    void mixCodeAndName() {
        when(provider.getRate("USD","EUR")).thenReturn(new BigDecimal("0.90"));
        BigDecimal out = service.convert("usd", "euro", new BigDecimal("10"));
        assertEquals(new BigDecimal("9.00"), out);
    }

    @Test
    @DisplayName("Sinônimos e acentuação: "Iene" e "Yen" resolvem para JPY")
    void synonymsAndAccents() {
        when(provider.getRate("JPY","EUR")).thenReturn(new BigDecimal("0.006"));
        BigDecimal out = service.convert("Íene", "Euro", new BigDecimal("1000"));
        assertEquals(new BigDecimal("6.00"), out);
    }

    @Test
    @DisplayName("Ambiguidade: "Dólar" sem país deve falhar")
    void ambiguousDollar() {
        assertThrows(AmbiguousCurrencyException.class,
                () -> service.convert("Dólar", "Real Brasileiro", new BigDecimal("10")));
    }

    @Test
    @DisplayName("Moeda desconhecida deve falhar")
    void unknownCurrency() {
        assertThrows(CurrencyNotFoundException.class,
                () -> service.convert("Galleon", "Real Brasileiro", new BigDecimal("10")));
    }

    @Test
    @DisplayName("País mapeado para moeda certa: Chile -> CLP")
    void countryToCurrency() {
        when(provider.getRate("CLP","USD")).thenReturn(new BigDecimal("0.0011"));
        BigDecimal out = service.convert("Chile", "USD", new BigDecimal("10000"));
        assertEquals(new BigDecimal("11.00"), out);
    }
}
