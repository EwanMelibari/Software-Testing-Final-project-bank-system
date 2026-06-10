package com.example.bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class BankRepositoryTest {

    @Test
    void testSaveAndFindById() {
        BankRepository repo = new BankRepository();
        BankAccount acc = new BankAccount("X1", 500.0);
        repo.save(acc);
        BankAccount found = repo.findById("X1");
        assertNotNull(found);
        assertEquals(500.0, found.getBalance(), 0.001);
    }

    @Test
    void testFindByIdNotFound() {
        BankRepository repo = new BankRepository();
        assertNull(repo.findById("NONE"));
    }
}