package utils;

public class Protocol {
    // Command codes
    public static final String LOGIN = "LOGIN";
    public static final String BALANCE = "BALANCE";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final String TRANSFER = "TRANSFER";
    
    // Error messages
    public static final String INVALID_COMMAND = "Invalid command";
    public static final String INVALID_ACCOUNT = "Invalid account number or PIN";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds";
    public static final String TRANSACTION_SUCCESSFUL = "Transaction successful";
}
