package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class TransferFrame extends JFrame {
    private JLabel recipientLabel;
    private JTextField recipientField;
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton transferButton;

    public TransferFrame(PrintWriter out, BufferedReader in) {
        setTitle("Transfer Money");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        recipientLabel = new JLabel("Recipient Account Number:");
        recipientField = new JTextField(20);

        amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        transferButton = new JButton("Transfer");
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferMoney();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(recipientLabel);
        panel.add(recipientField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(transferButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void transferMoney() {
        // Get recipient account number and amount from text fields
        String recipientAccountNumber = recipientField.getText();
        double amount = Double.parseDouble(amountField.getText());

        // Validate recipient account number
        if (recipientAccountNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Recipient account number is required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate transfer amount
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid transfer amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send transfer request to the server
        String transferRequest = "TRANSFER:" + recipientAccountNumber + ":" + amount;
        // Send transferRequest to the server using socket and receive the response
        // Display the response or show appropriate message
        // Example: out.println(transferRequest);
        // String response = in.readLine();
        // JOptionPane.showMessageDialog(this, response);
        // or
        // balanceLabel.setText("Balance: " + response);
    }
}
