
package com.refactor;

/**
 * EXERCÍCIO: Code Smell - Data Clumps
 * Objetivo:
 *  - Criar classe Address e substituir grupos de campos repetidos
 * Critério de aceite:
 *  - Reduzir repetição dos trios street/city/zip
 */
public class DataClumpsExample {

    /** Classe extraída que representa um endereço completo */
    public static class Address {
        public final String street;
        public final String city;
        public final String zip;

        public Address(String street, String city, String zip) {
            if (street == null || city == null || zip == null) {
                throw new IllegalArgumentException("Endereço inválido");
            }
            this.street = street;
            this.city = city;
            this.zip = zip;
        }

        @Override
        public String toString() {
            return street + ", " + city + " - " + zip;
        }
    }

    public static class Customer {
        public Address address;
        public String name;

        public Customer(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    public static class Supplier {
        public Address address;
        public String companyName;

        public Supplier(String companyName, Address address) {
            this.companyName = companyName;
            this.address = address;
        }
    }
}