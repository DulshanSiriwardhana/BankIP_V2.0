package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class LoginFrame extends JFrame {
    private JLabel accountNumberLabel;
    private JLabel pinLabel;
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JButton loginButton;

    // Declare PrintWriter and BufferedReader as class members
    private PrintWriter out;
    private BufferedReader in;

    // Constructor with PrintWriter and BufferedReader parameters
    public LoginFrame(PrintWriter out, BufferedReader in) {
        // Call the default constructor of JFrame
        super("Login");

        // Assign PrintWriter and BufferedReader to class members
        this.out = out;
        this.in = in;

        // Set up the frame
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        initComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    private void initComponents() {
        accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(20);

        pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(new JLabel()); // Empty space for layout
        panel.add(loginButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void login() {
        // Get account number and PIN from text fields
        String accountNumber = accountNumberField.getText();
        String pin = String.valueOf(pinField.getPassword());

        // Validate account number and PIN (you may need to send them to the server for
        // validation)
        if (accountNumber.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Account number and PIN are required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // If valid, close the login frame and open the dashboard
            dispose();
            openDashboard();
        }
    }

    private void openDashboard() {
        DashboardFrame dashboard = new DashboardFrame();
        dashboard.setVisible(true);
    }
}
