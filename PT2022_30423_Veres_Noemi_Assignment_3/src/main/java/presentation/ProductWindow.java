package presentation;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
/**
 * A special window which contains the tables, buttons and labels needed to make operations on products.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class ProductWindow extends JFrame {
    private JPanel contentPane, operationsPanel, productsPanel;
    private JLabel operationLabel;
    private JButton addProductButton, editProductButton, deleteProductButton, viewAllProductsButton;
    private JTable productsTable, addProductTable, editProductTable, deleteProductTable;
    Controller controller = new Controller(this);

    public ProductWindow(String name){
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(950,700);
        this.setLocation(330,0);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(2,1));
        this.setVisible(true);
        this.prepareOperationsPanel();
        this.prepareProductsPanel();
        this.setContentPane(this.contentPane);
        controller.updateProductTable();
    }

    private void prepareProductsPanel(){
        this.productsPanel = new JPanel();
        this.productsPanel.setLayout(new GridLayout(1,1));
        this.productsTable = new JTable();
        JTableHeader header = productsTable.getTableHeader();
        header.setBackground(Color.YELLOW);
        JScrollPane sp = new JScrollPane(productsTable);
        this.productsPanel.add(sp);
        this.contentPane.add(productsPanel);
    }
    
    private void prepareOperationsPanel(){
        this.operationsPanel = new JPanel();
        this.operationsPanel.setLayout(new GridLayout(8,1));
        this.operationLabel = new JLabel("Select the operation: ", JLabel.CENTER);
        this.operationsPanel.add(this.operationLabel);
        this.addProductButton = new JButton("Add new product");
        this.addProductButton.setActionCommand("Add new product");
        this.addProductButton.addActionListener(this.controller);
        this.operationsPanel.add(this.addProductButton);
        String data1[][] = {{"", "", ""}};
        String column1[] = {"name", "unitPrice", "quantity"};
        this.addProductTable = new JTable(data1,column1);
        JScrollPane sp1 = new JScrollPane(addProductTable);
        this.operationsPanel.add(sp1);
        this.editProductButton = new JButton("Edit product");
        this.editProductButton.setActionCommand("Edit product");
        this.editProductButton.addActionListener(this.controller);
        this.operationsPanel.add(this.editProductButton);
        String data2[][] = {{"", "", "", ""}};
        String column2[] = {"id", "name", "unitPrice", "quantity"};
        this.editProductTable = new JTable(data2,column2);
        JScrollPane sp2 = new JScrollPane(editProductTable);
        this.operationsPanel.add(sp2);
        this.deleteProductButton = new JButton("Delete product");
        this.deleteProductButton.setActionCommand("Delete product");
        this.deleteProductButton.addActionListener(this.controller);
        this.operationsPanel.add(this.deleteProductButton);
        String data3[][] = {{""}};
        String column3[] = {"id"};
        this.deleteProductTable = new JTable(data3,column3);
        JScrollPane sp3 = new JScrollPane(deleteProductTable);
        this.operationsPanel.add(sp3);
        this.viewAllProductsButton = new JButton("View all products");
        this.viewAllProductsButton.setActionCommand("View all products");
        this.viewAllProductsButton.addActionListener(this.controller);
        this.operationsPanel.add(this.viewAllProductsButton);
        this.contentPane.add(operationsPanel);
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JTable getAddProductTable() {
        return addProductTable;
    }

    public JTable getEditProductTable() {
        return editProductTable;
    }

    public JTable getDeleteProductTable() {
        return deleteProductTable;
    }
}
