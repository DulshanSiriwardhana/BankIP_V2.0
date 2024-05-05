package server;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, Account> accounts;

    public Database() {
        // Initialize the accounts map
        accounts = new HashMap<>();
        // Populate the accounts map with sample account data
        initializeAccounts();
    }

    // Method to initialize sample account data
    private void initializeAccounts() {
        // Add sample account data (replace with actual database data)
        Account account1 = new Account("123456", 1234, 1000.0);
        Account account2 = new Account("654321", 4321, 2000.0);

        // Add accounts to the database
        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(), account2);
    }

    // Method to validate account number and PIN
    public boolean validateAccount(String accountNumber, int pin) {
        // Retrieve account from the database
        Account account = accounts.get(accountNumber);

        // Check if account exists and PIN matches
        return account != null && account.getPin() == pin;
    }

    // Method to retrieve account balance
    public double getAccountBalance(String accountNumber) {
        // Retrieve account from the database
        Account account = accounts.get(accountNumber);

        // Return account balance if account exists, otherwise return 0
        return account != null ? account.getBalance() : 0;
    }

    // Method to deposit money into an account
    public void deposit(String accountNumber, double amount) {
        // Retrieve account from the database
        Account account = accounts.get(accountNumber);

        // Deposit money into the account
        if (account != null) {
            account.deposit(amount);
        }
    }

    // Method to withdraw money from an account
    public boolean withdraw(String accountNumber, double amount) {
        // Retrieve account from the database
        Account account = accounts.get(accountNumber);

        // Withdraw money from the account
        return account != null && account.withdraw(amount);
    }

    // Method to transfer money between accounts
    public boolean transfer(String senderAccountNumber, String recipientAccountNumber, double amount) {
        // Retrieve sender and recipient accounts from the database
        Account senderAccount = accounts.get(senderAccountNumber);
        Account recipientAccount = accounts.get(recipientAccountNumber);

        // Transfer money between accounts
        if (senderAccount != null && recipientAccount != null) {
            return senderAccount.transfer(recipientAccount, amount);
        } else {
            return false; // One or both accounts not found
        }
    }
}
