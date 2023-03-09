package presentation;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
/**
 * A special window which contains the tables, buttons and labels needed to make operations on orders.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class OrderWindow extends JFrame {
    private JPanel contentPane, quantityPanel, ordersPanel, selectionPanel;
    
    private JLabel quantityLabel, instructionLabel;
    private JButton orderButton;
    private JTextField quantityTextField;
    private JTable ordersTable, clientsTable, productsTable;
    ReadingARow myClient, myProduct;
    Controller controller = new Controller(this);

    public OrderWindow(String name){
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(950,600);
        this.setLocation(330,0);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(4,1));
        this.setVisible(true);
        this.prepareWindow();
        this.prepareQuantityPanel();
        this.setContentPane(this.contentPane);
        this.controller.updateOrderTables();
    }

    private void prepareQuantityPanel() {
        this.quantityPanel = new JPanel();
        this.quantityPanel.setLayout(new GridLayout(1,2));
        this.quantityLabel = new JLabel("   Enter the quantity: ");
        this.quantityPanel.add(quantityLabel);
        this.quantityTextField = new JTextField();
        this.quantityPanel.add(quantityTextField);
        this.selectionPanel.add(quantityPanel);
        this.orderButton = new JButton("ORDER");
        this.orderButton.setActionCommand("ORDER");
        this.orderButton.addActionListener(this.controller);
        this.selectionPanel.add(orderButton);
        this.contentPane.add(selectionPanel);
    }

    private void prepareWindow(){
        this.ordersPanel = new JPanel();
        this.ordersPanel.setLayout(new GridLayout(2,1));
        this.selectionPanel = new JPanel();
        this.selectionPanel.setLayout(new GridLayout(2,1));
        this.ordersTable = new JTable();
        JTableHeader header = ordersTable.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane sp = new JScrollPane(ordersTable);
        this.ordersPanel.add(sp);
        this.instructionLabel = new JLabel(
                "Select a client and a product, enter the quantity and then press the ORDER button", JLabel.CENTER);
        this.ordersPanel.add(instructionLabel);
        this.contentPane.add(ordersPanel);
        this.clientsTable = new JTable();
        JTableHeader headerClient = clientsTable.getTableHeader();
        headerClient.setBackground(Color.PINK);
        JScrollPane spClient = new JScrollPane(clientsTable);
        this.contentPane.add(spClient);
        this.myClient = new ReadingARow(clientsTable, "Selected client: ");
        this.selectionPanel.add(myClient.label);
        this.productsTable = new JTable();
        JTableHeader headerProduct = productsTable.getTableHeader();
        headerProduct.setBackground(Color.YELLOW);
        JScrollPane spProduct = new JScrollPane(productsTable);
        this.contentPane.add(spProduct);
        //this.contentPane.add(productsPanel);
        this.myProduct = new ReadingARow(productsTable, "Selected product: ");
        this.selectionPanel.add(myProduct.label);
        this.contentPane.add(selectionPanel);
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }

    public JTable getClientsTable() {
        return clientsTable;
    }

    public JTable getProductsTable() {
        return productsTable;
    }
}
