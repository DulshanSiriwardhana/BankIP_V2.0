package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Database database;
    private Integer loggedInAccountNumber;

    public ClientHandler(Socket socket, Database database) {
        this.clientSocket = socket;
        this.database = database;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                String response = processRequest(inputLine);
                out.println(response);
                if (inputLine.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    private String processRequest(String request) {
        try {
            if (request.startsWith("LOGIN:")) {
                return handleLogin(request);
            } else if (request.startsWith("BALANCE")) {
                return handleBalance(request);
            } else if (request.startsWith("DEPOSIT:")) {
                return handleDeposit(request);
            } else if (request.startsWith("WITHDRAW:")) {
                return handleWithdraw(request);
            } else if (request.startsWith("TRANSFER:")) {
                return handleTransfer(request);
            } else if (request.startsWith("CREATE_ACCOUNT:")) {
                return handleCreateAccount(request);
            } else {
                return "UNKNOWN_REQUEST";
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    private String handleLogin(String request) {
        String[] loginInfo = request.substring(6).split(":");
        if (loginInfo.length != 2) return "LOGIN_FAILED";
        String accountNumber = loginInfo[0];
        int pin;
        try {
            pin = Integer.parseInt(loginInfo[1]);
        } catch (NumberFormatException e) {
            return "LOGIN_FAILED";
        }
        if (database.validateAccount(accountNumber, pin)) {
            setLoggedInAccountNumber(Integer.parseInt(accountNumber));
            return "LOGIN_SUCCESS";
        } else {
            return "LOGIN_FAILED";
        }
    }

    private String handleBalance(String request) {
        if (loggedInAccountNumber == null) return "NOT_LOGGED_IN";
        double balance = database.getAccountBalance(loggedInAccountNumber.toString());
        return "BALANCE:" + balance;
    }

    private String handleDeposit(String request) {
        if (loggedInAccountNumber == null) return "NOT_LOGGED_IN";
        String[] depositInfo = request.split(":");
        if (depositInfo.length != 2) return "INVALID_DEPOSIT_REQUEST";
        double amount;
        try {
            amount = Double.parseDouble(depositInfo[1]);
        } catch (NumberFormatException e) {
            return "INVALID_AMOUNT";
        }
        database.deposit(loggedInAccountNumber.toString(), amount);
        return "DEPOSIT_SUCCESS";
    }

    private String handleWithdraw(String request) {
        if (loggedInAccountNumber == null) return "NOT_LOGGED_IN";
        String[] withdrawInfo = request.split(":");
        if (withdrawInfo.length != 2) return "INVALID_WITHDRAWAL_REQUEST";
        double amount;
        try {
            amount = Double.parseDouble(withdrawInfo[1]);
        } catch (NumberFormatException e) {
            return "INVALID_AMOUNT";
        }
        boolean success = database.withdraw(loggedInAccountNumber.toString(), amount);
        return success ? "WITHDRAW_SUCCESS" : "INSUFFICIENT_FUNDS";
    }

    private String handleTransfer(String request) {
        if (loggedInAccountNumber == null) return "NOT_LOGGED_IN";
        String[] transferInfo = request.split(":");
        if (transferInfo.length != 3) return "INVALID_TRANSFER_REQUEST";
        String recipientAccountNumber = transferInfo[1];
        double amount;
        try {
            amount = Double.parseDouble(transferInfo[2]);
        } catch (NumberFormatException e) {
            return "INVALID_AMOUNT";
        }
        boolean success = database.transfer(loggedInAccountNumber.toString(), recipientAccountNumber, amount);
        return success ? "TRANSFER_SUCCESS" : "TRANSFER_FAILED";
    }

    private String handleCreateAccount(String request) {
        String[] accountInfo = request.split(":");
        if (accountInfo.length != 4) return "INVALID_CREATE_ACCOUNT_REQUEST";
        System.out.println(request);
        String accountNumber = accountInfo[1];
        int pin;
        double initialBalance;
        try {
            pin = Integer.parseInt(accountInfo[2]);
            initialBalance = Double.parseDouble(accountInfo[3]);
        } catch (NumberFormatException e) {
            return "INVALID_CREATE_ACCOUNT_REQUEST";
        }
        boolean success = database.createAccount(accountNumber, pin, initialBalance);
        return success ? "CREATE_ACCOUNT_SUCCESS" : "CREATE_ACCOUNT_FAILED";
    }

    public void setLoggedInAccountNumber(Integer accountNumber) {
        this.loggedInAccountNumber = accountNumber;
    }

    public Integer getLoggedInAccountNumber() {
        return loggedInAccountNumber;
    }
}
