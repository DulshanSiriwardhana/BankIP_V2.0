package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositFrame extends JFrame {
    private JLabel amountLabel;
    private JTextField amountField;
    private JButton depositButton;

    public DepositFrame() {
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
        double amount = Double.parseDouble(amountField.getText());

        // Validate deposit amount
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send deposit request to the server
        String depositRequest = "DEPOSIT:" + amount;
        // Send depositRequest to the server using socket and receive the response
        // Display the response or show appropriate message
        // Example: out.println(depositRequest);
        //          String response = in.readLine();
        //          JOptionPane.showMessageDialog(this, response);
        //          or
        //          balanceLabel.setText("Balance: " + response);
    }
}
