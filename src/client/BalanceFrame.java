package client;

import javax.swing.*;
import java.awt.*;

public class BalanceFrame extends JFrame {
    private JLabel balanceLabel;

    public BalanceFrame(double balance) {
        setTitle("Account Balance");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents(balance);
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents(double balance) {
        balanceLabel = new JLabel("Balance: " + balance);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.add(balanceLabel);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}
