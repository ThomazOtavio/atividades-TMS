package com.teixeira.tdd.currency;

import java.math.BigDecimal;

/**
 * Dependência externa para obtenção de taxa de câmbio.
 * Será simulada em testes com dublês (mocks/stubs).
 */
public interface ExchangeRateProvider {
    /**
     * Retorna a taxa de câmbio de `from` para `to` (ex.: USD→BRL).
     * @throws ExchangeRateUnavailableException se taxa não puder ser obtida
     */
    BigDecimal getRate(String from, String to) throws ExchangeRateUnavailableException;
}
