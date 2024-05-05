package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawalFrame extends JFrame {
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton withdrawButton;

    public WithdrawalFrame() {
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
        double amount = Double.parseDouble(amountField.getText());

        // Validate withdrawal amount
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send withdrawal request to the server
        String withdrawalRequest = "WITHDRAW:" + amount;
        // Send withdrawalRequest to the server using socket and receive the response
        // Display the response or show appropriate message
        // Example: out.println(withdrawalRequest);
        //          String response = in.readLine();
        //          JOptionPane.showMessageDialog(this, response);
        //          or
        //          balanceLabel.setText("Balance: " + response);
    }
}
