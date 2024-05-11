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

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public DashboardFrame(PrintWriter out, BufferedReader in, Socket socket) {
        this.out = out;
        this.in = in;
        this.socket = socket;

        setTitle("Dashboard");
        setSize(400, 150);
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

    private void checkBalance() {
        // Send request to the server to check balance
        out.println("BALANCE");
    }

    private void depositMoney() {
        // Open DepositFrame
        new DepositFrame(out, in);
    }

    private void withdrawMoney() {
        // Open WithdrawalFrame
        new WithdrawalFrame(out, in);
    }

    private void transferMoney() {
        // Open TransferFrame
        new TransferFrame(out, in);
    }
}
