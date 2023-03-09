package bll;

import bll.validators.Validator;
import dataAcess.dao.OrderDAO;
import model.Client;
import model.Order;
import model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * OrderBLL is responsible for the business logic involving orders.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;
    private BufferedWriter writer;

    public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();

        orderDAO = new OrderDAO();
    }
    /**
     * Creates a new order entity and saves its data.
     * @param clientId is the identifier of the client who sent the order.
     * @param productId is the identifier of the ordered product.
     * @param quantity represents the ordered quantity.
     */
    public String createOrder(int clientId, int productId, int quantity) throws IOException {
        ClientBLL clientBLL = new ClientBLL();
        Client orderingClient = clientBLL.findClientById(clientId);
        if(orderingClient == null){                                         // check if client exists
            return "The client is missing";
        }
        ProductBLL productBLL = new ProductBLL();
        Product orderedProduct = productBLL.findProductById(productId);
        if(orderedProduct == null){                                         // check if product exists
            return "Product is missing";
        }
        if(!isOnStock(orderedProduct.getQuantity(), quantity)){  // check if there are enough products
            return "Not enough products on stock";
        }
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Order newOrder = new Order(-1, clientId, productId, quantity, orderedProduct.getUnitPrice(),
                LocalDateTime.now());
        //Order newOrder = new Order(-1, clientId, productId, quantity, orderedProduct.getUnitPrice());
        orderedProduct.setQuantity(orderedProduct.getQuantity() - quantity);
        productBLL.updateProduct(orderedProduct);
        insertOrder(newOrder);
        createBill(orderingClient,orderedProduct,newOrder);
        return "Order was successfully created";
    }

    public void createBill(Client client, Product product, Order order) throws IOException {
        openWriter("Log.txt");
        writer.write("Client: " + client.getFirstName() + " " + client.getLastName());
        writer.write("\nProduct name: " + product.getName());
        writer.write("\nQuantity: " + order.getQuantity());
        writer.write("\nUnit price: " + order.getUnitPrice());
        writer.write("\nTotal: " + order.getQuantity()*order.getUnitPrice());
        closeWriter();
    }

    private void openWriter(String fileName) {
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter(){
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Order findOrderById(int id) {
        Order st = orderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Order with id = " + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Order> findAllOrders(){
        return (ArrayList<Order>) orderDAO.findAll();
    }

    public void insertOrder(Order o){
        orderDAO.insert(o);
    }

    public void updateOrder(Order o){
        orderDAO.update(o);
    }

    public void deleteOrder(Order o){
        orderDAO.delete(o);
    }

    public boolean isOnStock(int availableQuantity, int orderedQuantity) {
        return availableQuantity >= orderedQuantity;
    }
}
