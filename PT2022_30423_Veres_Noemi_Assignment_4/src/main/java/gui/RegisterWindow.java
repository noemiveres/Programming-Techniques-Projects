package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterWindow extends JFrame{
    private JPanel mainPanel;
    private JLabel usernameLabel, password1Label, password2Label, chooseTypeOfUserLabel;
    private JTextField usernameTextField, password1TextField, password2TextField;
    private JComboBox typeOfUserComboBox;
    private JButton registerButton, alreadyMemberButton;

    Controller controller = new Controller(this);

    public RegisterWindow(String name) throws IOException, ClassNotFoundException {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(400, 200);
        //this.setLocation(0,200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mainPanel = new JPanel(new GridLayout(5, 2));
        this.setVisible(true);
        this.usernameLabel = new JLabel("Enter a username:", JLabel.CENTER);
        this.mainPanel.add(this.usernameLabel);
        this.usernameTextField = new JTextField();
        this.mainPanel.add(usernameTextField);
        this.password1Label = new JLabel("Enter password:", JLabel.CENTER);
        this.mainPanel.add(this.password1Label);
        this.password1TextField = new JTextField();
        this.mainPanel.add(password1TextField);
        this.password2Label = new JLabel("Confirm your password:", JLabel.CENTER);
        this.mainPanel.add(this.password2Label);
        this.password2TextField = new JTextField();
        this.mainPanel.add(password2TextField);
        this.chooseTypeOfUserLabel = new JLabel("Select type of user", JLabel.CENTER);
        this.mainPanel.add(this.chooseTypeOfUserLabel);
        String[] types = new String[]{"Administrator", "Client", "Employee"};
        this.typeOfUserComboBox = new JComboBox(types);
        this.mainPanel.add(typeOfUserComboBox);
        this.registerButton = new JButton("Register");
        this.registerButton.setActionCommand("Register");
        this.registerButton.addActionListener(this.controller);
        this.mainPanel.add(this.registerButton);
        this.alreadyMemberButton = new JButton("I'm already a member");
        this.alreadyMemberButton.setActionCommand("I'm already a member");
        this.alreadyMemberButton.addActionListener(this.controller);
        this.mainPanel.add(this.alreadyMemberButton);
        this.setContentPane(this.mainPanel);
    }

    public JComboBox getTypeOfUserComboBox() {
        return typeOfUserComboBox;
    }

    public JTextField getPassword1TextField(){ return password1TextField;}

    public JTextField getPassword2TextField(){ return password2TextField; }

    public JTextField getUsernameTextField(){ return usernameTextField;}
}
