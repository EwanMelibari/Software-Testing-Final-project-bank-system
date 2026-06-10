package com.example.bank_system;

import java.util.HashMap;
import java.util.Map;

public class BankRepository {

    private Map<String, BankAccount> accounts = new HashMap<>();

    public void save(BankAccount account) {
        accounts.put(account.getId(), account);
    }

    public BankAccount findById(String id) {
        return accounts.get(id);
    }
    
}