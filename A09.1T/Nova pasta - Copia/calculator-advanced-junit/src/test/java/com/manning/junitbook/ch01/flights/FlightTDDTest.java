package com.manning.junitbook.ch01.flights;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("TDD – Gerenciador de Voos (do zero)")
class FlightTDDTest {

    @Nested
    @DisplayName("Criação do voo")
    class Creation {

        @Test
        @DisplayName("Cria voo com código e capacidade; começa vazio")
        void creates_flight_with_capacity() {
            Flight f = new Flight("AB123", 2);
            assertEquals("AB123", f.getCode());
            assertEquals(2, f.getCapacity());
            assertEquals(0, f.getPassengerCount());
            assertTrue(f.getPassengers().isEmpty());
        }

        @ParameterizedTest(name = "Capacidade inválida: {0}")
        @CsvSource({"-1"})
        void invalid_capacity_throws(int cap) {
            assertThrows(IllegalArgumentException.class, () -> new Flight("X", cap));
        }

        @ParameterizedTest(name = "Código inválido: "{0}"")
        @CsvSource({",", "' '"})
        void invalid_code_throws(String code) {
            assertThrows(IllegalArgumentException.class, () -> new Flight(code, 1));
        }
    }

    @Nested
    @DisplayName("Regras de passageiros")
    class PassengerRules {

        @Test
        @DisplayName("Adiciona passageiro novo até esgotar capacidade")
        void adds_passenger_up_to_capacity() {
            Flight f = new Flight("AB123", 2);
            Passenger p1 = new Passenger("P001", "Alice");
            Passenger p2 = new Passenger("P002", "Bob");

            assertTrue(f.addPassenger(p1));
            assertTrue(f.addPassenger(p2));
            assertEquals(2, f.getPassengerCount());
            assertThat(f.getPassengers(), contains(p1, p2));
        }

        @Test
        @DisplayName("Não adiciona o mesmo passageiro duas vezes (mesma identidade)")
        void does_not_add_same_passenger_twice() {
            Flight f = new Flight("AB123", 3);
            Passenger p = new Passenger("P001", "Alice");

            assertTrue(f.addPassenger(p));
            assertFalse(f.addPassenger(new Passenger("P001", "Alice Clone"))); // mesma id
            assertEquals(1, f.getPassengerCount());
            assertTrue(f.contains(p));
        }

        @Test
        @DisplayName("Respeita limite de capacidade: ao atingir limite, novas adições falham")
        void capacity_limit_enforced() {
            Flight f = new Flight("AB123", 2);
            assertTrue(f.addPassenger(new Passenger("P001", "Alice")));
            assertTrue(f.addPassenger(new Passenger("P002", "Bob")));
            assertFalse(f.addPassenger(new Passenger("P003", "Carol"))); // sem vagas
            assertEquals(2, f.getPassengerCount());
        }

        @Test
        @DisplayName("Remoção: depois de remover, pode adicionar outro (libera vaga)")
        void remove_then_add_allows_new() {
            Flight f = new Flight("AB123", 1);
            Passenger p1 = new Passenger("P001", "Alice");
            Passenger p2 = new Passenger("P002", "Bob");

            assertTrue(f.addPassenger(p1));
            assertFalse(f.addPassenger(p2)); // sem vagas

            assertTrue(f.removePassenger(p1));
            assertTrue(f.addPassenger(p2)); // agora cabe
            assertEquals(1, f.getPassengerCount());
            assertFalse(f.contains(p1));
            assertTrue(f.contains(p2));
        }

        @Test
        @DisplayName("Entradas inválidas: não aceita Passenger nulo")
        void null_passenger_rejected() {
            Flight f = new Flight("AB123", 1);
            assertThrows(IllegalArgumentException.class, () -> f.addPassenger(null));
            assertFalse(f.removePassenger(null)); // idempotente
        }
    }
}
