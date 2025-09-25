package org.example.doubles;

public interface AccountManager {
    Account findAccountForUser(String id);
    void updateAccount(Account account);
}
