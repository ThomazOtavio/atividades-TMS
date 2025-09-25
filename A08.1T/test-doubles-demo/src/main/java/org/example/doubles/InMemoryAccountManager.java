package org.example.doubles;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountManager implements AccountManager {
    private final Map<String, Account> db = new HashMap<>();

    public void put(Account a) {
        db.put(a.getId(), a);
    }

    @Override
    public Account findAccountForUser(String id) {
        return db.get(id);
    }

    @Override
    public void updateAccount(Account account) {
        db.put(account.getId(), account);
    }
}
