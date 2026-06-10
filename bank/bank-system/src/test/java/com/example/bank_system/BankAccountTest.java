package com.example.bank_system;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BankAccount Unit Tests")
public class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC001", 1000.0);
    }

    // ─── deposit() tests ───────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-U01: Valid deposit increases balance correctly")
    void testDepositValidAmount() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U02: Depositing minimum positive value (0.01) succeeds")
    void testDepositMinimumAmount() {
        account.deposit(0.01);
        assertEquals(1000.01, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U03: Depositing large amount succeeds")
    void testDepositLargeAmount() {
        account.deposit(1_000_000.0);
        assertEquals(1_001_000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U04: Deposit of zero throws IllegalArgumentException")
    void testDepositZeroThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(0)
        );
        assertEquals("Invalid deposit amount", ex.getMessage());
    }

    @Test
    @DisplayName("TC-U05: Deposit of negative amount throws IllegalArgumentException")
    void testDepositNegativeAmountThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-100.0)
        );
        assertEquals("Invalid deposit amount", ex.getMessage());
    }

    @Test
    @DisplayName("TC-U06: Balance unchanged after failed deposit")
    void testBalanceUnchangedAfterFailedDeposit() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50.0));
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    // ─── withdraw() tests ──────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-U07: Valid withdrawal decreases balance correctly")
    void testWithdrawValidAmount() {
        account.withdraw(400.0);
        assertEquals(600.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U08: Withdrawing exact balance succeeds (zero remaining)")
    void testWithdrawExactBalance() {
        account.withdraw(1000.0);
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U09: Withdrawal of zero throws IllegalArgumentException")
    void testWithdrawZeroThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(0)
        );
        assertEquals("Invalid withdraw amount", ex.getMessage());
    }

    @Test
    @DisplayName("TC-U10: Withdrawal of negative amount throws IllegalArgumentException")
    void testWithdrawNegativeAmountThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(-200.0)
        );
        assertEquals("Invalid withdraw amount", ex.getMessage());
    }

    @Test
    @DisplayName("TC-U11: Withdrawal exceeding balance throws IllegalArgumentException")
    void testWithdrawInsufficientFundsThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(1500.0)
        );
        assertEquals("Insufficient funds", ex.getMessage());
    }

    @Test
    @DisplayName("TC-U12: Balance unchanged after failed withdrawal")
    void testBalanceUnchangedAfterFailedWithdraw() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(9999.0));
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U13: Multiple sequential deposits accumulate correctly")
    void testMultipleDeposits() {
        account.deposit(100.0);
        account.deposit(200.0);
        account.deposit(300.0);
        assertEquals(1600.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("TC-U14: Multiple sequential withdrawals reduce correctly")
    void testMultipleWithdrawals() {
        account.withdraw(100.0);
        account.withdraw(200.0);
        assertEquals(700.0, account.getBalance(), 0.001);
    }

    // ─── constructor / getters ─────────────────────────────────────────────────

    @Test
    @DisplayName("TC-U15: Account id is stored and retrieved correctly")
    void testGetId() {
        assertEquals("ACC001", account.getId());
    }

    @Test
    @DisplayName("TC-U16: Initial balance is stored and retrieved correctly")
    void testGetBalance() {
        assertEquals(1000.0, account.getBalance(), 0.001);
    }
}
