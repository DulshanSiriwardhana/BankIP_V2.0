package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DashboardFrame extends JFrame {
    private JButton balanceButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;

    // Text fields for deposit and transfer operations
    private JTextField amountField;
    private JTextField recipientField;
    private JTextArea logTextArea;

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public DashboardFrame(PrintWriter out, BufferedReader in, Socket socket) {
        this.out = out;
        this.in = in;
        this.socket = socket;

        setTitle("Dashboard");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        // Buttons with custom styles
        balanceButton = createStyledButton("Check Balance");
        depositButton = createStyledButton("Deposit Money");
        withdrawButton = createStyledButton("Withdraw Money");
        transferButton = createStyledButton("Transfer Money");

        // Text fields with custom styles
        amountField = createStyledTextField(10);
        recipientField = createStyledTextField(10);

        // Text area for logs
        logTextArea = new JTextArea(10, 30);
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        styleTextArea(logTextArea);

        // Add action listeners to buttons
        balanceButton.addActionListener(e -> checkBalance());
        depositButton.addActionListener(e -> depositMoney());
        withdrawButton.addActionListener(e -> withdrawMoney());
        transferButton.addActionListener(e -> transferMoney());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 102, 204)); // Set background color
        button.setForeground(Color.WHITE); // Set text color
        button.setFocusPainted(false); // Remove the focus border
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size and style
        return button;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size and style
        return textField;
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size and style
        textArea.setBackground(new Color(240, 240, 240)); // Light background for better readability
        textArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Light border for a neat look
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10)); // Add spacing between components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        panel.setBackground(new Color(60, 63, 65));

        // Add buttons
        panel.add(balanceButton);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(depositButton);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(withdrawButton);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(transferButton);
        panel.add(new JLabel()); // Empty label for alignment

        // Add labels and text fields
        panel.add(createLabel("Amount:"));
        panel.add(amountField);
        panel.add(createLabel("Recipient Account Number:"));
        panel.add(recipientField);

        // Add the log text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the scroll pane

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size and style
        label.setForeground(Color.WHITE);
        return label;
    }

    private void checkBalance() {
        try {
            // Send request to the server to check balance
            out.println("BALANCE");
            // Receive the response from the server
            String response = in.readLine();
            // Display the response in the log area
            logTextArea.append(response + "\n");
        } catch (IOException e) {
            logTextArea.append("Error occurred while checking balance: " + e.getMessage() + "\n");
        }
    }

    private void depositMoney() {
        try {
            // Get deposit amount from text field
            double amount = Double.parseDouble(amountField.getText());

            // Send deposit request to the server
            out.println("DEPOSIT:" + amount);
            // Receive the response from the server
            String response = in.readLine();
            // Display the response in the log area
            logTextArea.append(response + "\n");
        } catch (NumberFormatException e) {
            logTextArea.append("Invalid amount format.\n");
        } catch (IOException e) {
            logTextArea.append("Error occurred while depositing money: " + e.getMessage() + "\n");
        }
    }

    private void withdrawMoney() {
        try {
            // Get withdrawal amount from text field
            double amount = Double.parseDouble(amountField.getText());

            // Send withdrawal request to the server
            out.println("WITHDRAW:" + amount);
            // Receive the response from the server
            String response = in.readLine();
            // Display the response in the log area
            logTextArea.append(response + "\n");
        } catch (NumberFormatException e) {
            logTextArea.append("Invalid amount format.\n");
        } catch (IOException e) {
            logTextArea.append("Error occurred while withdrawing money: " + e.getMessage() + "\n");
        }
    }

    private void transferMoney() {
        try {
            // Get recipient account number and amount from text fields
            String recipientAccountNumber = recipientField.getText();
            double amount = Double.parseDouble(amountField.getText());

            // Send transfer request to the server
            String transferRequest = "TRANSFER:" + recipientAccountNumber + ":" + amount;
            out.println(transferRequest);
            // Receive the response from the server
            String response = in.readLine();
            // Display the response in the log area
            logTextArea.append(response + "\n");
        } catch (NumberFormatException e) {
            logTextArea.append("Invalid amount format.\n");
        } catch (IOException e) {
            logTextArea.append("Error occurred while transferring money: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        // For testing purposes
        SwingUtilities.invokeLater(() -> {
            PrintWriter out = null;
            BufferedReader in = null;
            Socket socket = null;

            new DashboardFrame(out, in, socket);
        });
    }
}
