package com.example.bank_system;

public class BankService {

    private BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    public void createAccount(String id, double balance) {
        BankAccount account = new BankAccount(id, balance);
        repository.save(account);
    }

    public void deposit(String id, double amount) {
        BankAccount account = repository.findById(id);
        account.deposit(amount);
    }

    public void withdraw(String id, double amount) {
        BankAccount account = repository.findById(id);
        account.withdraw(amount);
    }

    public void transfer(String fromId, String toId, double amount) {
        BankAccount from = repository.findById(fromId);
        BankAccount to = repository.findById(toId);
        from.withdraw(amount);
        to.deposit(amount);
    }
}