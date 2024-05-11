package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame {
    private JButton balanceButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;

    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();
    }

    private void initComponents() {
        balanceButton = new JButton("Check Balance");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");

        // Add action listeners to the buttons
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBalanceFrame();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDepositFrame();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWithdrawalFrame();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTransferFrame();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(balanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void openBalanceFrame() {
        // Implement opening of BalanceFrame
        // BalanceFrame balanceFrame = new BalanceFrame(balance);
        // balanceFrame.setVisible(true);
    }

    private void openDepositFrame() {
        DepositFrame depositFrame = new DepositFrame();
        depositFrame.setVisible(true);
    }

    private void openWithdrawalFrame() {
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame();
        withdrawalFrame.setVisible(true);
    }

    private void openTransferFrame() {
        TransferFrame transferFrame = new TransferFrame();
        transferFrame.setVisible(true);
    }
}
