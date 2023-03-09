package presentation;

import javax.swing.*;
import java.awt.*;
/** Responsible for creating a user-friendly graphical interface. */
public class View extends JFrame {
    private JPanel mainPanel;
    private JLabel classesLabel;
    private JButton clientButton, orderButton, productButton;

    Controller controller = new Controller(this);

    public View(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(350, 350);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mainPanel = new JPanel(new GridLayout(4, 1));
        //this.prepareSelectionPanel();
        this.classesLabel = new JLabel("Choose one:", JLabel.CENTER);
        this.mainPanel.add(this.classesLabel);
        this.clientButton = new JButton("Client");
        this.clientButton.setActionCommand("Client");
        this.clientButton.addActionListener(this.controller);
        this.mainPanel.add(this.clientButton);
        this.orderButton = new JButton("Order");
        this.orderButton.setActionCommand("Order");
        this.orderButton.addActionListener(this.controller);
        this.mainPanel.add(this.orderButton);
        this.productButton = new JButton("Product");
        this.productButton.setActionCommand("Product");
        this.productButton.addActionListener(this.controller);
        this.mainPanel.add(this.productButton);
        this.setContentPane(this.mainPanel);
    }


}
