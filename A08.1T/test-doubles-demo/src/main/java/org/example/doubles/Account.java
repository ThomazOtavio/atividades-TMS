package org.example.doubles;

public class Account {
    private final String id;
    private int balance; // centavos/unidade simples

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }
    public String getId() { return id; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }
}
