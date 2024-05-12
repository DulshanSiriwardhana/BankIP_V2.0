package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class WithdrawalFrame extends JFrame {
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton withdrawButton;

    private PrintWriter out;
    private BufferedReader in;

    public WithdrawalFrame(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;

        setTitle("Withdraw Money");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        amountLabel = new JLabel("Withdrawal Amount:");
        amountField = new JTextField(10);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawMoney();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(withdrawButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void withdrawMoney() {
        // Get withdrawal amount from text field
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate withdrawal amount
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Send withdrawal request to the server
            out.println("WITHDRAW:" + amount);
            // Receive the response from the server
            String response = in.readLine();
            // Display the response or show appropriate message
            JOptionPane.showMessageDialog(this, response);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error occurred during withdrawal: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
