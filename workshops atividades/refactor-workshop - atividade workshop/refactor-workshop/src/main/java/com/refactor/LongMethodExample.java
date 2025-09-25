
package com.refactor;

import java.time.LocalDate;
import java.util.List;

/**
 * EXERCÍCIO: Code Smell - Long Method
 * Objetivo:
 *  - Usar Extract Method no método processOrder para dividir responsabilidades
 *  - (Opcional) Introduce Parameter Object para agrupar parâmetros de frete/cupom
 * Critério de aceite:
 *  - processOrder deve ficar curto (<= 15 linhas) e legível
 *  - Nenhuma mudança de comportamento
 */
public class LongMethodExample {

    public static class Item {
        public final String name;
        public final int quantity;
        public final double price;
        public Item(String name, int quantity, double price) {
            this.name = name; this.quantity = quantity; this.price = price;
        }
    }

    public static class Order {
        public final String customerId;
        public final List<Item> items;
        public final LocalDate date = LocalDate.now();
        public Order(String customerId, List<Item> items) {
            this.customerId = customerId; this.items = items;
        }
    }

    // Novo objeto para agrupar opções de checkout
    public static class CheckoutOptions {
        public final String couponCode;
        public final String shippingMethod;
        public CheckoutOptions(String couponCode, String shippingMethod) {
            this.couponCode = couponCode;
            this.shippingMethod = shippingMethod;
        }
    }

    // Método principal agora está enxuto
    public double processOrder(Order order, CheckoutOptions opts) {
        double subtotal = calculateSubtotal(order);
        double discount = calculateDiscount(subtotal, opts.couponCode);
        double totalAfterDiscount = subtotal - discount;
        double shipping = calculateShipping(opts.shippingMethod);
        double tax = calculateTax(totalAfterDiscount);

        logOrder(order, subtotal, discount, shipping, tax);

        return totalAfterDiscount + shipping + tax;
    }

    private double calculateSubtotal(Order order) {
        double subtotal = 0.0;
        for (Item i : order.items) {
            subtotal += i.price * i.quantity;
        }
        return subtotal;
    }

    private double calculateDiscount(double subtotal, String couponCode) {
        double discount = 0.0;
        if (couponCode != null && !couponCode.isBlank()) {
            if (couponCode.startsWith("SAVE10")) discount = subtotal * 0.10;
            else if (couponCode.startsWith("SAVE20")) discount = subtotal * 0.20;
        }
        return discount;
    }

    private double calculateShipping(String shippingMethod) {
        if ("EXPRESS".equalsIgnoreCase(shippingMethod)) return 39.90;
        if ("STANDARD".equalsIgnoreCase(shippingMethod)) return 19.90;
        return 29.90;
    }

    private double calculateTax(double totalAfterDiscount) {
        return totalAfterDiscount * 0.12;
    }

    private void logOrder(Order order, double subtotal, double discount, double shipping, double tax) {
        System.out.printf("[order=%s] subtotal=%.2f discount=%.2f shipping=%.2f tax=%.2f%n",
                order.customerId, subtotal, discount, shipping, tax);
    }
}