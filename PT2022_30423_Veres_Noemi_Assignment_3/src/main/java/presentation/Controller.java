package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;
import utils.TableFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
/** Deals with the connection between the Model and the View. */
public class Controller implements ActionListener {
    private View view;
    private ClientWindow clientWindow;
    private OrderWindow orderWindow;
    private ProductWindow productWindow;
    ClientBLL clientBLL = new ClientBLL();
    OrderBLL orderBLL = new OrderBLL();
    ProductBLL productBLL = new ProductBLL();
    private int quantity = 0, id, unitPrice, clientId, productId;
    private String userName, firstName, lastName, address, phoneNumber, emailAddress;
    private String name, unitPriceString, quantityString;

    public Controller(View view) {
        this.view = view;
    }

    public Controller(ClientWindow clientWindow) {
        this.clientWindow = clientWindow;
    }

    public Controller(OrderWindow orderWindow) {
        this.orderWindow = orderWindow;
    }

    public Controller(ProductWindow productWindow) {
        this.productWindow = productWindow;
    }

    public void updateClientTable() {
        ArrayList<Client> objects = clientBLL.findAllClients();
        try {
            clientWindow.getClientsTable().setModel(TableFactory.createTable(objects));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateProductTable() {
        ArrayList<Product> objects = productBLL.findAllProducts();
        try {
            productWindow.getProductsTable().setModel(TableFactory.createTable(objects));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderTables() {
        ArrayList<Order> objects = orderBLL.findAllOrders();
        ArrayList<Client> clients = clientBLL.findAllClients();
        ArrayList<Product> products = productBLL.findAllProducts();
        try{
            orderWindow.getOrdersTable().setModel(TableFactory.createTable(objects));
            orderWindow.getClientsTable().setModel(TableFactory.createTable(clients));
            orderWindow.getProductsTable().setModel(TableFactory.createTable(products));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "Client") {
            ClientWindow clientWindow = new ClientWindow("Client table");
        } else if (command == "Add new client") {
            userName = clientWindow.getAddClientTable().getValueAt(0,0).toString();
            firstName = clientWindow.getAddClientTable().getValueAt(0,1).toString();
            lastName = clientWindow.getAddClientTable().getValueAt(0,2).toString();
            address = clientWindow.getAddClientTable().getValueAt(0,3).toString();
            phoneNumber = clientWindow.getAddClientTable().getValueAt(0,4).toString();
            emailAddress = clientWindow.getAddClientTable().getValueAt(0,5).toString();
            Client newClient = new Client(-1, userName, firstName, lastName, address, phoneNumber,
                    emailAddress);
            clientBLL.insertClient(newClient);
            updateClientTable();
        } else if (command == "Edit client") {
            id = Integer.parseInt(clientWindow.getEditClientTable().getValueAt(0,0).toString());
            userName = clientWindow.getEditClientTable().getValueAt(0,1).toString();
            firstName = clientWindow.getEditClientTable().getValueAt(0,2).toString();
            lastName = clientWindow.getEditClientTable().getValueAt(0,3).toString();
            address = clientWindow.getEditClientTable().getValueAt(0,4).toString();
            phoneNumber = clientWindow.getEditClientTable().getValueAt(0,5).toString();
            emailAddress = clientWindow.getEditClientTable().getValueAt(0,6).toString();
            clientBLL.updateClient(id,userName,firstName,lastName,address,phoneNumber,emailAddress);
            updateClientTable();
        } else if (command == "Delete client") {
            id = Integer.parseInt(clientWindow.getDeleteClientTable().getValueAt(0, 0).toString());
            Client client = clientBLL.findClientById(id);
            clientBLL.deleteClient(client);
            updateClientTable();
        } else if (command == "Order") {
            OrderWindow orderWindow = new OrderWindow("Order table");
        } else if (command == "Product") {
            ProductWindow productWindow = new ProductWindow("Product table");
        } else if (command == "Add new product") {
            name = productWindow.getAddProductTable().getValueAt(0,0).toString();
            unitPrice = Integer.parseInt(productWindow.getAddProductTable().getValueAt(0,1).toString());
            quantity = Integer.parseInt(productWindow.getAddProductTable().getValueAt(0,2).toString());
            Product newProduct = new Product(-1,name,unitPrice,quantity);
            productBLL.insertProduct(newProduct);
            updateProductTable();
        } else if (command == "Edit product") {
            id = Integer.parseInt(productWindow.getEditProductTable().getValueAt(0,0).toString());
            name = productWindow.getEditProductTable().getValueAt(0,1).toString();
            unitPriceString = productWindow.getEditProductTable().getValueAt(0,2).toString();
            quantityString = productWindow.getEditProductTable().getValueAt(0,3).toString();
            productBLL.updateProduct(id,name,unitPriceString,quantityString);
            updateProductTable();
        } else if (command == "Delete product") {
            id = Integer.parseInt(productWindow.getDeleteProductTable().getValueAt(0, 0).toString());
            Product product = productBLL.findProductById(id);
            productBLL.deleteProduct(product);
            updateProductTable();
        } else if (command == "ORDER") {
            try {
                quantity = Integer.parseInt(orderWindow.getQuantityTextField().getText());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong input data!\nYou should insert integers.", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            ListSelectionModel model1 = orderWindow.getClientsTable().getSelectionModel();
            int lead1 = model1.getLeadSelectionIndex();
            clientId = Integer.parseInt(orderWindow.getClientsTable().getValueAt(lead1, 0).toString());
            ListSelectionModel model2 = orderWindow.getProductsTable().getSelectionModel();
            int lead2 = model2.getLeadSelectionIndex();
            productId = Integer.parseInt(orderWindow.getProductsTable().getValueAt(lead2, 0).toString());
            String message = null;
            try {
                message = orderBLL.createOrder(clientId, productId, quantity);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (message.equals("Order was successfully created")) {
                JOptionPane.showMessageDialog(new JFrame(),
                        message, "Order was placed.",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        message, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            updateOrderTables();
        }
    }
}
