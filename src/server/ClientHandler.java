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
    private Integer loggedInAccountNumber; // Variable to store the logged-in account number

    public ClientHandler(Socket socket, Database database) {
        this.clientSocket = socket;
        this.database = database; // Assign Database instance
    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams for communication with the client
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Handle communication with the client
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);

                // Process client request and send response
                String response = processRequest(inputLine);
                out.println(response);

                if (inputLine.equals("exit")) {
                    break; // Exit the loop if the client sends "exit"
                }
            }

            // Close resources
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private String processRequest(String request) {
        System.out.println(request);
        if (request.startsWith("LOGIN:")) {
            // Handle login request
            String[] loginInfo = request.substring(6).split(":");
            String accountNumber = loginInfo[0];
            int pin = Integer.parseInt(loginInfo[1]);
            if (database.validateAccount(accountNumber, pin)) {
                setLoggedInAccountNumber(Integer.parseInt(accountNumber));
                return "LOGIN_SUCCESS"; // Send success response without balance
            } else {
                return "LOGIN_FAILED"; // Send failure response
            }
        } else if (request.startsWith("BALANCE")) {
            // Handle balance inquiry request
            // Extract account number from request
            String accountNumber = null;
            if (request.length() > 7) {
                accountNumber = request.substring(8);
            }
            // Retrieve balance from the database
            double balance = database.getAccountBalance(accountNumber);
            // Send balance as response
            return "BALANCE:" + String.valueOf(balance);
        } else if (request.startsWith("DEPOSIT:")) {
            // Handle deposit request
            // Extract account number and amount from request
            String[] depositInfo = request.split(":");
            System.out.println(depositInfo[0]);
            System.out.println(depositInfo[1]);
            if (depositInfo.length < 2) return "INVALID_DEPOSIT_REQUEST";
            String accountNumber = String.valueOf(this.loggedInAccountNumber);
            double amount = Double.parseDouble(depositInfo[1]);
            // Perform deposit operation in the database
            database.deposit(accountNumber, amount);
            // Send success response
            return "DEPOSIT_SUCCESS";
        } else if (request.startsWith("WITHDRAW:")) {
            // Handle withdrawal request
            // Extract account number and amount from request
            String[] withdrawInfo = request.substring(9).split(":");
            if (withdrawInfo.length < 2) return "INVALID_WITHDRAWAL_REQUEST";
            String accountNumber = withdrawInfo[0];
            double amount = Double.parseDouble(withdrawInfo[1]);
            // Perform withdrawal operation in the database
            boolean success = database.withdraw(accountNumber, amount);
            // Send success or failure response
            if (success) {
                return "WITHDRAW_SUCCESS";
            } else {
                return "INSUFFICIENT_FUNDS";
            }
        } else if (request.startsWith("TRANSFER:")) {
            // Handle transfer request
            // Extract sender account number, recipient account number, and amount from request
            String[] transferInfo = request.substring(9).split(":");
            if (transferInfo.length < 3) return "INVALID_TRANSFER_REQUEST";
            String senderAccountNumber = transferInfo[0];
            String recipientAccountNumber = transferInfo[1];
            double amount = Double.parseDouble(transferInfo[2]);
            // Perform transfer operation in the database
            boolean success = database.transfer(senderAccountNumber, recipientAccountNumber, amount);
            // Send success or failure response
            if (success) {
                return "TRANSFER_SUCCESS";
            } else {
                return "TRANSFER_FAILED";
            }
        } else {
            // Handle unknown request
            return "Unknown request";
        }
    }

    // Method to set the logged-in account number
    public void setLoggedInAccountNumber(Integer accountNumber) {
        this.loggedInAccountNumber = accountNumber;
    }

    // Method to get the logged-in account number
    public Integer getLoggedInAccountNumber() {
        return loggedInAccountNumber;
    }
}
