
package com.refactor;

import java.time.LocalDate;
import java.time.Period;

/**
 * EXERCÍCIO: Code Smell - Data Class
 * Objetivo:
 *  - Adicionar comportamento relevante em Customer (ex.: elegibilidade de desconto)
 *  - Mover métodos utilitários dispersos para a própria classe (Move Method)
 * Critério de aceite:
 *  - Menos getters/setters "oco", mais comportamento encapsulado
 */

public class DataClassExample {

    public static class Customer {
        private String id;
        private String name;
        private LocalDate since;
        private int loyaltyPoints;

        public Customer(String id, String name, LocalDate since, int loyaltyPoints) {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("id inválido");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("nome inválido");
            if (since == null) throw new IllegalArgumentException("data inválida");
            if (loyaltyPoints < 0) throw new IllegalArgumentException("pontos não podem ser negativos");

            this.id = id;
            this.name = name;
            this.since = since;
            this.loyaltyPoints = loyaltyPoints;
        }

        /** Calcula taxa de desconto combinando tempo de cliente e pontos */
        public double discountRate() {
            int years = Period.between(since, LocalDate.now()).getYears();
            double byYears = years >= 5 ? 0.05 : years >= 2 ? 0.02 : 0.0;
            double byPoints = loyaltyPoints >= 1000 ? 0.05 : loyaltyPoints >= 500 ? 0.02 : 0.0;
            return Math.min(0.15, byYears + byPoints); // limite máximo 15%
        }

        /** Aplica uma compra com desconto e adiciona pontos de fidelidade */
        public double applyPurchase(double amount) {
            if (amount < 0) throw new IllegalArgumentException("valor da compra inválido");
            double paid = amount * (1.0 - discountRate());
            addLoyalty((int) Math.floor(amount)); // ganha pontos pela compra
            return Math.round(paid * 100.0) / 100.0; // arredonda para 2 casas
        }

        /** Adiciona pontos de fidelidade */
        public int addLoyalty(int points) {
            if (points < 0) return loyaltyPoints;
            this.loyaltyPoints += points;
            return this.loyaltyPoints;
        }

        /** Permite renomear cliente */
        public void rename(String newName) {
            if (newName == null || newName.isBlank()) {
                throw new IllegalArgumentException("nome inválido");
            }
            this.name = newName;
        }

        // Getters de leitura (sem setters "ocos")
        public String getId() { return id; }
        public String getName() { return name; }
        public LocalDate getSince() { return since; }
        public int getLoyaltyPoints() { return loyaltyPoints; }
    }
}
