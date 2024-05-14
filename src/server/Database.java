package server;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, Account> accounts;

    public Database() {
        accounts = new HashMap<>();
        initializeAccounts();
    }

    private void initializeAccounts() {
        Account account1 = new Account("123456", 1234, 1000.0);
        Account account2 = new Account("654321", 4321, 2000.0);
        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(), account2);
    }

    public boolean validateAccount(String accountNumber, int pin) {
        Account account = accounts.get(accountNumber);
        return account != null && account.getPin() == pin;
    }

    public double getAccountBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        return account != null && account.withdraw(amount);
    }

    public boolean transfer(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Account senderAccount = accounts.get(senderAccountNumber);
        Account recipientAccount = accounts.get(recipientAccountNumber);
        return senderAccount != null && recipientAccount != null && senderAccount.transfer(recipientAccount, amount);
    }

    public synchronized boolean createAccount(String accountNumber, int pin, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account creation failed: Account already exists");
            return false; // Account already exists
        }
        Account newAccount = new Account(accountNumber, pin, initialBalance);
        accounts.put(accountNumber, newAccount);
        System.out.println("Account created: " + accountNumber);
        return true; // Account successfully created
    }

}
