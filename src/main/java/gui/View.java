package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View extends JFrame {
    private JPanel mainPanel;
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameTextField, passwordTextField;
    private JButton logInButton, registerButton;

    Controller controller = new Controller(this);

    public View(String name) throws IOException, ClassNotFoundException {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(400, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mainPanel = new JPanel(new GridLayout(3, 2));
        this.setVisible(true);
        this.usernameLabel = new JLabel("Username:", JLabel.CENTER);
        this.mainPanel.add(this.usernameLabel);
        this.usernameTextField = new JTextField();
        this.mainPanel.add(usernameTextField);
        this.passwordLabel = new JLabel("Password:", JLabel.CENTER);
        this.mainPanel.add(this.passwordLabel);
        this.passwordTextField = new JTextField();
        this.mainPanel.add(passwordTextField);
        this.logInButton = new JButton("Log in");
        this.logInButton.setActionCommand("Log in");
        this.logInButton.addActionListener(this.controller);
        this.mainPanel.add(this.logInButton);
        this.registerButton = new JButton("Register now");
        this.registerButton.setActionCommand("Register now");
        this.registerButton.addActionListener(this.controller);
        this.mainPanel.add(this.registerButton);
        this.setContentPane(this.mainPanel);
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }
}
