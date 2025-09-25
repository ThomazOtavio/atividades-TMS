package com.teixeira.tdd.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterTest {

    @Mock
    ExchangeRateProvider provider;

    CurrencyConverter converter;

    @Captor
    org.mockito.ArgumentCaptor<String> codeCaptor;

    @BeforeEach
    void setUp() {
        converter = new CurrencyConverter(provider);
    }

    @Test
    @DisplayName("Converte 10 USD para 50 BRL quando taxa USD→BRL = 5.0")
    void convertsUsdToBrl() {
        when(provider.getRate("USD", "BRL")).thenReturn(new BigDecimal("5.0"));

        BigDecimal result = converter.convert("USD", "BRL", new BigDecimal("10"));

        assertEquals(new BigDecimal("50.00"), result);
        verify(provider).getRate("USD", "BRL");
    }

    @Test
    @DisplayName("Mesma moeda retorna o próprio valor com 2 casas e não consulta o provider")
    void sameCurrencyShortCircuits() {
        BigDecimal result = converter.convert("USD", "USD", new BigDecimal("123.45"));
        assertEquals(new BigDecimal("123.45").setScale(2, RoundingMode.HALF_UP), result);
        verify(provider, never()).getRate(anyString(), anyString());
    }

    @Test
    @DisplayName("Arredonda HALF_UP para 2 casas decimais")
    void roundsHalfUpToTwoDecimals() {
        when(provider.getRate("GBP", "USD")).thenReturn(new BigDecimal("1.2345"));
        BigDecimal result = converter.convert("GBP", "USD", new BigDecimal("10"));
        assertEquals(new BigDecimal("12.35"), result); // 10 * 1.2345 = 12.345 → 12.35
    }

    @Test
    @DisplayName("Zero permanece zero com 2 casas")
    void zeroAmount() {
        when(provider.getRate("EUR", "USD")).thenReturn(new BigDecimal("1.10"));
        BigDecimal result = converter.convert("EUR", "USD", new BigDecimal("0"));
        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    @DisplayName("Valor negativo gera IllegalArgumentException")
    void negativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert("USD", "BRL", new BigDecimal("-1")));
    }

    @Test
    @DisplayName("Códigos aceitam minúsculas e são normalizados para MAIÚSCULAS")
    void normalizesCurrencyCodes() {
        when(provider.getRate("USD", "BRL")).thenReturn(new BigDecimal("5.0"));
        converter.convert("usd", "brl", new BigDecimal("1"));
        verify(provider).getRate(eq("USD"), eq("BRL"));
    }

    @Test
    @DisplayName("Código de moeda inválido (não-ISO de 3 letras) gera IllegalArgumentException")
    void rejectsInvalidCurrencyCode() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert("US", "BRL", new BigDecimal("10")));
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert("USD", "BR", new BigDecimal("10")));
        assertThrows(IllegalArgumentException.class,
                () -> converter.convert("U$D", "BRL", new BigDecimal("10")));
    }

    @Test
    @DisplayName("Propaga erro quando taxa não estiver disponível")
    void propagatesProviderException() {
        when(provider.getRate("USD", "XYZ")).thenThrow(new ExchangeRateUnavailableException("No rate"));
        assertThrows(ExchangeRateUnavailableException.class,
                () -> converter.convert("USD", "XYZ", new BigDecimal("10")));
    }
}
