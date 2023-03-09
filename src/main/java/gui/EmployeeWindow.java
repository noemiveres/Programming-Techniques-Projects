package gui;

import model.MenuItem;
import model.Order;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class EmployeeWindow extends JFrame {
    private JPanel contentPane;
    private JButton logoutButton;
    private JLabel youWillBeNotifiedWhenAnOrderIsPlacedLabel;
    private JTable orderTable;
    Order order;
    Controller controller = new Controller(this);

    public EmployeeWindow(String name) throws IOException, ClassNotFoundException {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(500,500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(1,1));
        this.setVisible(true);
        this.prepareWindow();
//        this.prepareFiltersPanel();
//        this.prepareSelectionPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareWindow() {
        youWillBeNotifiedWhenAnOrderIsPlacedLabel = new JLabel("You will be notified when an order is placed", JLabel.CENTER);
        contentPane.add(youWillBeNotifiedWhenAnOrderIsPlacedLabel);
    }

    public JLabel getYouWillBeNotifiedWhenAnOrderIsPlacedLabel() {
        return youWillBeNotifiedWhenAnOrderIsPlacedLabel;
    }
}
