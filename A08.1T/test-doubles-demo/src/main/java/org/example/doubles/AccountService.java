package org.example.doubles;

public class AccountService {
    private final AccountManager accountManager;

    public AccountService(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void transfer(String fromId, String toId, int amount) {
        Account from = accountManager.findAccountForUser(fromId);
        Account to = accountManager.findAccountForUser(toId);
        if (from == null || to == null) throw new IllegalArgumentException("Conta inexistente");
        if (from.getBalance() < amount) throw new IllegalArgumentException("Saldo insuficiente");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountManager.updateAccount(from);
        accountManager.updateAccount(to);
    }
}
