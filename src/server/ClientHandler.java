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
        if (request.startsWith("LOGIN:")) {
            String[] loginInfo = request.substring(6).split(":");
            String accountNumber = loginInfo[0];
            int pin = Integer.parseInt(loginInfo[1]);
            System.out.println(accountNumber);
            System.out.println(pin);
            if (database.validateAccount(accountNumber, pin)) {
                double balance = database.getAccountBalance(accountNumber);
                System.out.println(accountNumber);
                return "LOGIN_SUCCESS:" + balance; // Send success response
            } else {
                return "LOGIN_FAILED"; // Send failure response
            }
        } else {
            return "Unknown request"; // Add a default return value for other requests
        }
    }

}
