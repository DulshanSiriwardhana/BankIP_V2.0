package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginFrame extends JFrame {
    private JLabel accountNumberLabel;
    private JLabel pinLabel;
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JButton loginButton;

    // Declare PrintWriter and BufferedReader as class members
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    // Constructor with PrintWriter, BufferedReader, and Socket parameters
    public LoginFrame(PrintWriter out, BufferedReader in, Socket socket) {
        // Call the default constructor of JFrame
        super("Login");

        // Assign PrintWriter, BufferedReader, and Socket to class members
        this.out = out;
        this.in = in;
        this.socket = socket;

        // Set up the frame
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(20);

        pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(new JLabel()); // Empty space for layout
        panel.add(loginButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void login() {
        // Get account number and PIN from text fields
        String accountNumber = accountNumberField.getText();
        String pin = String.valueOf(pinField.getPassword());

        // Validate account number and PIN (you may need to send them to the server for validation)
        if (accountNumber.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Account number and PIN are required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Send the login request to the server
            out.println("LOGIN:" + accountNumber + ":" + pin);
            try {
                // Wait for response from the server
                String response = in.readLine();
                System.out.println(response);
                if (response.startsWith("LOGIN_SUCCESS:")) {
                    //double balance = Double.parseDouble(response.substring(14));
                    // If login is successful, open the dashboard
                    dispose(); // Close the login frame
                    openDashboard(); // Pass the balance to the dashboard
                } else if (response.equals("LOGIN_FAILED")) {
                    JOptionPane.showMessageDialog(this, "Invalid account number or PIN.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Unknown response from server.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle communication error
            }
        }
    }

    private void openDashboard() {
        // Create a DashboardFrame passing PrintWriter, BufferedReader, and Socket
        new DashboardFrame(out, in, socket);
    }

    // Method to close the resources
    public void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
