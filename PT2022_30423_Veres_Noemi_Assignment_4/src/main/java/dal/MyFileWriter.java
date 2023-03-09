package dal;

import model.MenuItemToNoOfOrders;
import model.User;
import model.MenuItem;
import model.Order;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class MyFileWriter {
    private BufferedWriter writer;

    public void createBill(User client, Order order, Map<MenuItem, Integer> orderedItemsToQuantity) throws IOException {
        openWriter("Bill.txt");
        writer.write("Client: " + client.getUsername());
        orderedItemsToQuantity.forEach((key, value) -> {
            try {
                writer.write("\n" + key.getTitle()
                        + " - quantity: " + value + " - unit price: " + key.computePrice());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("\nTotal price: " + order.computePrice(orderedItemsToQuantity));
        closeWriter();
    }

    public void createOrderReport(LocalTime startTime, LocalTime endTime, List<Order> orders) throws IOException {
        openWriter("Orders.txt");
        writer.write("The orders placed between " + startTime + " and " + endTime);
        orders.forEach(order -> {
            try {
                writer.write("\nOrder id: " + order.getOrderId() + " Client id: " +
                        order.getClientId() + " Time: " + order.getTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        closeWriter();
    }

    public void createLoyalClientsReport(int minOrders, int minPrice, List<User> clients) throws IOException {
        openWriter("LoyalClients.txt");
        writer.write("The loyal clients placing min " + minOrders + " orders with a value higher than "
                + minPrice);
        clients.forEach(client -> {
            try {
                writer.write("\n" + client.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        closeWriter();
    }

    public void createProductsOrderedADayReport(LocalDate day, List<MenuItemToNoOfOrders> menuItemToNoOfOrders) throws IOException {
        openWriter("ProductsOrderedADay.txt");
        writer.write("The products ordered on " + day);
        menuItemToNoOfOrders.forEach(menuItem -> {
            try {
                writer.write("\n" + menuItem.getTitle() + " - quantity: " + menuItem.getNoOfOrders());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        closeWriter();
    }

    public void createFavouriteProductsReport(int minTimes, List<MenuItem> products) throws IOException {
        openWriter("FavouriteProducts.txt");
        writer.write("The favourite products (ordered more than " + minTimes + " times)");
        products.forEach(product -> {
            try {
                writer.write("\n" + product.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        closeWriter();
    }

    private void openWriter(String fileName) {
        try {
            writer = new BufferedWriter(new java.io.FileWriter(fileName));
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
}
