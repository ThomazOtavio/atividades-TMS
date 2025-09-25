
package com.refactor;

/**
 * EXERCÍCIO: Code Smell - Desvio Condicional (switch/if by type)
 * Objetivo:
 *  - Substituir o switch por polimorfismo: criar hierarquia Account
 * Critério de aceite:
 *  - Eliminar switch em computeFee
 */

public class ConditionalDivergenceExample {

    public enum AccountType {
        BASIC {
            @Override
            public double fee(double amount) {
                return amount * 0.02 + 1.0;
            }
        },
        PREMIUM {
            @Override
            public double fee(double amount) {
                return amount * 0.015 + 0.5;
            }
        },
        ENTERPRISE {
            @Override
            public double fee(double amount) {
                return amount * 0.01;
            }
        };

        public abstract double fee(double amount);
    }

    public double computeFee(AccountType type, double amount) {
        return type.fee(amount);
    }
}
