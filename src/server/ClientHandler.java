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

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
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

    // Method to process client request and generate response
    private String processRequest(String request) {
        // Implement your logic for processing client requests here
        // For example, you can parse the request, perform database operations, etc.
        // This is just a placeholder method, replace it with your actual logic
        return "Response to: " + request;
    }
}
