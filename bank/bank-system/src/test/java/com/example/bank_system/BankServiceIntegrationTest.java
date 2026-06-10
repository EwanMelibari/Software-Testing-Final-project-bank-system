package com.example.bank_system;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BankService Integration Tests")
public class BankServiceIntegrationTest {

    private BankRepository repository;
    private BankService service;

    @BeforeEach
    void setUp() {
        repository = new BankRepository();
        service = new BankService(repository);
        service.createAccount("A1", 1000.0);
        service.createAccount("A2", 500.0);
    }

    // ─── Task 2, Scenario 1: Transfer between two accounts ────────────────────

    @Test
    @DisplayName("TC-I01: Transfer moves correct amount between accounts")
    void testTransferBetweenAccounts() {
        service.transfer("A1", "A2", 300.0);
        assertEquals(700.0, repository.findById("A1").getBalance(), 0.001);
        assertEquals(800.0, repository.findById("A2").getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I02: Transfer of full balance leaves sender at zero")
    void testTransferFullBalance() {
        service.transfer("A1", "A2", 1000.0);
        assertEquals(0.0, repository.findById("A1").getBalance(), 0.001);
        assertEquals(1500.0, repository.findById("A2").getBalance(), 0.001);
    }

    // ─── Task 2, Scenario 2: Transfer failure due to insufficient balance ──────

    @Test
    @DisplayName("TC-I03: Transfer fails with insufficient funds — throws exception")
    void testTransferInsufficientFunds() {
        assertThrows(IllegalArgumentException.class,
                () -> service.transfer("A1", "A2", 2000.0));
    }

    @Test
    @DisplayName("TC-I04: Both balances unchanged after failed transfer")
    void testBalancesUnchangedAfterFailedTransfer() {
        assertThrows(IllegalArgumentException.class,
                () -> service.transfer("A1", "A2", 5000.0));
        assertEquals(1000.0, repository.findById("A1").getBalance(), 0.001);
        assertEquals(500.0,  repository.findById("A2").getBalance(), 0.001);
    }

    // ─── Task 2, Scenario 3: Multiple sequential operations ───────────────────

    @Test
    @DisplayName("TC-I05: Deposit via service increases account balance")
    void testDepositViaService() {
        service.deposit("A1", 200.0);
        assertEquals(1200.0, repository.findById("A1").getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I06: Withdraw via service decreases account balance")
    void testWithdrawViaService() {
        service.withdraw("A2", 100.0);
        assertEquals(400.0, repository.findById("A2").getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I07: Multiple sequential operations — deposit, withdraw, transfer")
    void testMultipleSequentialOperations() {
        service.deposit("A1", 500.0);        // A1 = 1500
        service.withdraw("A2", 200.0);       // A2 = 300
        service.transfer("A1", "A2", 400.0); // A1 = 1100, A2 = 700

        assertEquals(1100.0, repository.findById("A1").getBalance(), 0.001);
        assertEquals(700.0,  repository.findById("A2").getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I08: Back-and-forth transfers maintain correct totals")
    void testBackAndForthTransfers() {
        service.transfer("A1", "A2", 200.0); // A1=800, A2=700
        service.transfer("A2", "A1", 100.0); // A1=900, A2=600

        assertEquals(900.0, repository.findById("A1").getBalance(), 0.001);
        assertEquals(600.0, repository.findById("A2").getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I09: createAccount stores account retrievable by id")
    void testCreateAccountStored() {
        service.createAccount("A3", 750.0);
        BankAccount acc = repository.findById("A3");
        assertNotNull(acc);
        assertEquals(750.0, acc.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-I10: Service deposit with invalid amount propagates exception")
    void testServiceDepositInvalidAmountThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> service.deposit("A1", -50.0));
    }

    @Test
    @DisplayName("TC-I11: Service withdraw with insufficient funds propagates exception")
    void testServiceWithdrawInsufficientFundsThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> service.withdraw("A2", 9999.0));
    }
}
