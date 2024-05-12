package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class DepositFrame extends JFrame {
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton depositButton;

    private PrintWriter out;
    private BufferedReader in;
    private BalanceFrame balanceFrame;

    public DepositFrame(PrintWriter out, BufferedReader in, BalanceFrame balanceFrame) {
        this.out = out;
        this.in = in;
        this.balanceFrame = balanceFrame;

        setTitle("Deposit Money");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        amountLabel = new JLabel("Deposit Amount:");
        amountField = new JTextField(10);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositMoney();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void depositMoney() {
        // Get deposit amount from text field
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate deposit amount
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Send deposit request to the server
            out.println("DEPOSIT:" + amount);
            // Receive the response from the server
            String response = in.readLine();
            // If the deposit is successful, update the balance in BalanceFrame
            if (response.startsWith("DEPOSIT_SUCCESS")) {
                // Extract the new balance from the response
                double newBalance = Double.parseDouble(response.substring(15));
                // Update the balance in BalanceFrame
                balanceFrame.updateBalance(newBalance);
                // Display success message
                JOptionPane.showMessageDialog(this, "Deposit successful. New balance: " + newBalance);
            } else {
                // Display error message received from the server
                JOptionPane.showMessageDialog(this, response, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error occurred while depositing money: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
