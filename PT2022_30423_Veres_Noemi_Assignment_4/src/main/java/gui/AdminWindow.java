package gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;

public class AdminWindow extends JFrame {
    private JPanel contentPane, productsPanel, manageProductsPanel, reportPanel, downPanel, 
            compositeProductPanel;
    private JButton logoutButton, importBaseProductsButton, addBaseProductButton, editProductButton,
            deleteProductButton, generateReportButton, finishCompositeProductButton, addItemToMenuButton,
            timeIntervalButton, favouriteProductButton, loyalClientsButton, productsOrderedOnADayButton;
    private JTable baseProductsTable, addBaseProductTable, editProductTable, deleteProductTable, reportTable;
    private JLabel startHourLabel, endHourLabel, orderedMoreThanXTimesLabel, orderedWithValueMoreThanYLabel,
            newCompositeProductLabel, generateReportsLabel, instructionForReportsLabel, dayLabel;
    private JTextField startHourTextField, endHourTextField, orderedMoreThanXTimesTextField,
            orderedWithValueMoreThanYTextField, newCompositeProductTextField, dayTextField;
    ReadingARow myProduct;
    Controller controller = new Controller(this);

    public AdminWindow(String name) throws IOException, ClassNotFoundException {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(950,700);
        //this.setLocation(330,0);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(3,1));
        this.setVisible(true);
        this.prepareWindow();
//        this.prepareFiltersPanel();
//        this.prepareSelectionPanel();
        this.setContentPane(this.contentPane);
        //controller.updateBaseProductTable();
    }

    private void prepareWindow() {
        productsPanel = new JPanel(new GridLayout(2,1));
        manageProductsPanel = new JPanel(new GridLayout(6,1));
        reportPanel = new JPanel(new GridLayout(5,4));
        downPanel = new JPanel(new GridLayout(2,1));
        compositeProductPanel = new JPanel(new GridLayout(3,2));
        this.importBaseProductsButton = new JButton("Import base products");
        this.importBaseProductsButton.setActionCommand("Import base products");
        this.importBaseProductsButton.addActionListener(this.controller);
        this.productsPanel.add(importBaseProductsButton);
        this.baseProductsTable = new JTable();
        JTableHeader header = baseProductsTable.getTableHeader();
        header.setBackground(Color.ORANGE);
        JScrollPane sp = new JScrollPane(baseProductsTable);
        this.productsPanel.add(sp);
        contentPane.add(productsPanel);
        this.addBaseProductButton = new JButton("Add base product");
        this.addBaseProductButton.setActionCommand("Add base product");
        this.addBaseProductButton.addActionListener(this.controller);
        this.manageProductsPanel.add(addBaseProductButton);
        String data1[][] = {{"", "", "", "", "", "", ""}};
        String column1[] = {"title", "rating", "calories", "protein", "fats",
                "sodium", "price"};
        this.addBaseProductTable = new JTable(data1,column1);
        JScrollPane sp1 = new JScrollPane(addBaseProductTable);
        this.manageProductsPanel.add(sp1);
        this.editProductButton = new JButton("Edit product");
        this.editProductButton.setActionCommand("Edit product");
        this.editProductButton.addActionListener(this.controller);
        this.manageProductsPanel.add(editProductButton);
        String data2[][] = {{"", "", "", "", "", "", ""}};
        String column2[] = {"title", "rating", "calories", "protein", "fats",
                "sodium", "price"};
        this.editProductTable = new JTable(data2,column2);
        JScrollPane sp2 = new JScrollPane(editProductTable);
        this.manageProductsPanel.add(sp2);
        this.deleteProductButton = new JButton("Delete product");
        this.deleteProductButton.setActionCommand("Delete product");
        this.deleteProductButton.addActionListener(this.controller);
        this.manageProductsPanel.add(deleteProductButton);
        String data3[][] = {{""}};
        String column3[] = {"title"};
        this.deleteProductTable = new JTable(data3,column3);
        JScrollPane sp3 = new JScrollPane(deleteProductTable);
        this.manageProductsPanel.add(sp3);
        contentPane.add(manageProductsPanel);

        this.generateReportsLabel = new JLabel("GENERATE REPORTS", JLabel.CENTER);
        this.reportPanel.add(this.generateReportsLabel);
        this.orderedMoreThanXTimesLabel = new JLabel("Ordered more than ... times:  ", JLabel.RIGHT);
        this.reportPanel.add(this.orderedMoreThanXTimesLabel);
        this.orderedMoreThanXTimesTextField = new JTextField();
        this.reportPanel.add(this.orderedMoreThanXTimesTextField);
        this.favouriteProductButton = new JButton("Favourite products report");
        this.favouriteProductButton.setActionCommand("Favourite products report");
        this.favouriteProductButton.addActionListener(this.controller);
        this.reportPanel.add(favouriteProductButton);
        this.instructionForReportsLabel = new JLabel("Type in the info for reports", JLabel.CENTER);
        this.reportPanel.add(instructionForReportsLabel);
        this.orderedWithValueMoreThanYLabel = new JLabel("Ordered with value more than ... :  ", JLabel.RIGHT);
        this.reportPanel.add(this.orderedWithValueMoreThanYLabel);
        this.orderedWithValueMoreThanYTextField = new JTextField();
        this.reportPanel.add(this.orderedWithValueMoreThanYTextField);
        this.loyalClientsButton = new JButton("Loyal clients report");
        this.loyalClientsButton.setActionCommand("Loyal clients report");
        this.loyalClientsButton.addActionListener(this.controller);
        this.reportPanel.add(loyalClientsButton);
        this.startHourLabel = new JLabel("Start hour:  ", JLabel.RIGHT);
        this.reportPanel.add(this.startHourLabel);
        this.startHourTextField = new JTextField();
        this.reportPanel.add(this.startHourTextField);
        this.dayLabel = new JLabel("Day:  ", JLabel.RIGHT);
        this.reportPanel.add(this.dayLabel);
        this.dayTextField = new JTextField();
        this.reportPanel.add(this.dayTextField);
        this.endHourLabel = new JLabel("End hour:  ", JLabel.RIGHT);
        this.reportPanel.add(this.endHourLabel);
        this.endHourTextField = new JTextField();
        this.reportPanel.add(this.endHourTextField);
        this.timeIntervalButton = new JButton("Time interval report");
        this.timeIntervalButton.setActionCommand("Time interval report");
        this.timeIntervalButton.addActionListener(this.controller);
        this.reportPanel.add(timeIntervalButton);
        this.productsOrderedOnADayButton = new JButton("Products ordered on a day report");
        productsOrderedOnADayButton.setActionCommand("Products ordered on a day report");
        productsOrderedOnADayButton.addActionListener(controller);
        reportPanel.add(productsOrderedOnADayButton);
        downPanel.add(reportPanel);


        this.newCompositeProductLabel = new JLabel("Title of new composite product", JLabel.CENTER);
        this.compositeProductPanel.add(this.newCompositeProductLabel);
        this.newCompositeProductTextField = new JTextField();
        this.compositeProductPanel.add(this.newCompositeProductTextField);
        this.myProduct = new ReadingARow(baseProductsTable, "Selected product: ");
        this.compositeProductPanel.add(myProduct.label);
        this.contentPane.add(compositeProductPanel);
        this.addItemToMenuButton = new JButton("Add item to menu");
        this.addItemToMenuButton.setActionCommand("Add item to menu");
        this.addItemToMenuButton.addActionListener(this.controller);
        this.compositeProductPanel.add(addItemToMenuButton);
        this.finishCompositeProductButton = new JButton("Finish creating composite product");
        this.finishCompositeProductButton.setActionCommand("Finish creating composite product");
        this.finishCompositeProductButton.addActionListener(this.controller);
        this.compositeProductPanel.add(finishCompositeProductButton);
        this.logoutButton = new JButton("LOG OUT");
        this.logoutButton.setActionCommand("LOG OUT");
        this.logoutButton.addActionListener(this.controller);
        this.compositeProductPanel.add(logoutButton);
        downPanel.add(compositeProductPanel);
        contentPane.add(downPanel);
    }

    public JTable getBaseProductsTable() {
        return baseProductsTable;
    }

    public JTable getAddBaseProductTable() {
        return addBaseProductTable;
    }

    public JTable getDeleteProductTable() {
        return deleteProductTable;
    }

    public JTable getEditProductTable() {
        return editProductTable;
    }

    public JTextField getStartHourTextField() {
        return startHourTextField;
    }

    public JTextField getEndHourTextField() {
        return endHourTextField;
    }

    public JTextField getOrderedMoreThanXTimesTextField() {
        return orderedMoreThanXTimesTextField;
    }

    public JTextField getOrderedWithValueMoreThanYTextField() {
        return orderedWithValueMoreThanYTextField;
    }

    public JTextField getNewCompositeProductTextField() {
        return newCompositeProductTextField;
    }

    public JTextField getDayTextField() {
        return dayTextField;
    }
}
