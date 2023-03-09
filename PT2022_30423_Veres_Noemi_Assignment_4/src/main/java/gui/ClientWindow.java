package gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;

public class ClientWindow extends JFrame {
    private JPanel downPanel, downButtonsPanel, contentPane, productsPanel, filtersPanel, selectionPanel, quantityPanel;
    private JButton logoutButton, addToOrderButton, placeOrderButton, applyButton;
    private JLabel filtersLabel, keywordLabel, ratingLabel, caloriesLabel, proteinsLabel, fatsLabel,
            sodiumLabel, priceLabel, quantityLabel, instructionLabel;
    private JTextField keywordTextField, ratingTextField, caloriesTextField,
            proteinsTextField, fatsTextField, sodiumTextField, priceTextField, quantityTextField;
    private JTable productsTable;
    ReadingARow myProduct;
    Controller controller = new Controller(this);

    public ClientWindow(String name) throws IOException, ClassNotFoundException {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(950,700);
        //this.setLocation(330,0);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(3,1));
        this.setVisible(true);
        prepareFiltersPanel();
        this.prepareProductsPanel();
        this.prepareDownPanel();
        this.setContentPane(this.contentPane);
        controller.updateProductTable();
        //controller.updateClientTable();
    }

    private void prepareProductsPanel() {
        productsPanel = new JPanel(new GridLayout(2,1));
        this.instructionLabel = new JLabel(" You can apply some filters, select a product, " +
                "enter the quantity, add it to the order, repeat these steps " +
                "and finally place an order", JLabel.CENTER);
        this.productsPanel.add(instructionLabel);
        this.productsTable = new JTable();
        JTableHeader header = productsTable.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane spProduct = new JScrollPane(productsTable);
        this.productsPanel.add(spProduct);
        contentPane.add(productsPanel);
    }

    private void prepareDownPanel() {
        downPanel = new JPanel(new GridLayout(2,1));
        this.selectionPanel = new JPanel();
        this.selectionPanel.setLayout(new GridLayout(1,3));
        quantityPanel = new JPanel(new GridLayout(2,1));
        this.myProduct = new ReadingARow(productsTable, "Selected product: ");
        this.selectionPanel.add(myProduct.label);
        this.contentPane.add(selectionPanel);
        this.quantityLabel = new JLabel("   Enter the quantity: ");
        this.quantityPanel.add(quantityLabel);
        this.quantityTextField = new JTextField();
        this.quantityPanel.add(quantityTextField);
        this.selectionPanel.add(quantityPanel);
        this.addToOrderButton = new JButton("ADD TO ORDER");
        this.addToOrderButton.setActionCommand("ADD TO ORDER");
        this.addToOrderButton.addActionListener(this.controller);
        this.selectionPanel.add(addToOrderButton);
        downButtonsPanel = new JPanel(new GridLayout(2,1));
        this.placeOrderButton = new JButton("Place order");
        this.placeOrderButton.setActionCommand("Place order");
        this.placeOrderButton.addActionListener(this.controller);
        this.downButtonsPanel.add(placeOrderButton);
        this.logoutButton = new JButton("LOG OUT");
        this.logoutButton.setActionCommand("LOG OUT");
        this.logoutButton.addActionListener(this.controller);
        this.downButtonsPanel.add(logoutButton);
        downPanel.add(selectionPanel);
        downPanel.add(downButtonsPanel);
        this.contentPane.add(downPanel);
    }

    private void prepareFiltersPanel() {
        this.filtersPanel = new JPanel();
        this.filtersPanel.setLayout(new GridLayout(8,2));
        this.filtersLabel = new JLabel("    Filters");
        filtersPanel.add(filtersLabel);
        this.applyButton = new JButton("APPLY");
        this.applyButton.setActionCommand("APPLY");
        this.applyButton.addActionListener(this.controller);
        this.filtersPanel.add(applyButton);
        this.keywordLabel = new JLabel("keyword", JLabel.CENTER);
        this.filtersPanel.add(keywordLabel);
        this.keywordTextField = new JTextField();
        this.filtersPanel.add(keywordTextField);
        this.ratingLabel = new JLabel("min rating", JLabel.CENTER);
        this.filtersPanel.add(ratingLabel);
        this.ratingTextField = new JTextField();
        this.filtersPanel.add(ratingTextField);
        this.caloriesLabel = new JLabel("max calories", JLabel.CENTER);
        this.filtersPanel.add(caloriesLabel);
        this.caloriesTextField = new JTextField();
        this.filtersPanel.add(caloriesTextField);
        this.proteinsLabel = new JLabel("min proteins", JLabel.CENTER);
        this.filtersPanel.add(proteinsLabel);
        this.proteinsTextField = new JTextField();
        this.filtersPanel.add(proteinsTextField);
        this.fatsLabel = new JLabel("max fats", JLabel.CENTER);
        this.filtersPanel.add(fatsLabel);
        this.fatsTextField = new JTextField();
        this.filtersPanel.add(fatsTextField);
        this.sodiumLabel = new JLabel("max sodium", JLabel.CENTER);
        this.filtersPanel.add(sodiumLabel);
        this.sodiumTextField = new JTextField();
        this.filtersPanel.add(sodiumTextField);
        this.priceLabel = new JLabel("max price", JLabel.CENTER);
        this.filtersPanel.add(priceLabel);
        this.priceTextField = new JTextField();
        this.filtersPanel.add(priceTextField);
        contentPane.add(filtersPanel);
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JTextField getKeywordTextField() {
        return keywordTextField;
    }

    public JTextField getRatingTextField() {
        return ratingTextField;
    }

    public JTextField getCaloriesTextField() {
        return caloriesTextField;
    }

    public JTextField getProteinsTextField() {
        return proteinsTextField;
    }

    public JTextField getFatsTextField() {
        return fatsTextField;
    }

    public JTextField getSodiumTextField() {
        return sodiumTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }
}
