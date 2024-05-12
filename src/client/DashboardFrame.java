package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        balanceButton = new JButton("Check Balance");
        depositButton = new JButton("Deposit Money");
        withdrawButton = new JButton("Withdraw Money");
        transferButton = new JButton("Transfer Money");

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferMoney();
            }
        });

        amountField = new JTextField(10);
        recipientField = new JTextField(10);
        logTextArea = new JTextArea(10, 30);
        logTextArea.setEditable(false);
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(balanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Recipient Account Number:"));
        panel.add(recipientField);

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
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
}
