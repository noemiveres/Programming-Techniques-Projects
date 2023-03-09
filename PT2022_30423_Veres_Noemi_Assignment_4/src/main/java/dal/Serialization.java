package dal;

import model.BaseProduct;
import model.MenuItem;
import model.Order;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serialization {
    public void serializeBaseProducts(List<BaseProduct> baseProducts) {
        try {
            FileOutputStream fos = new FileOutputStream("baseProduct.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(baseProducts);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public ArrayList<BaseProduct> deserializeBaseProducts() {
        ArrayList<BaseProduct> baseProducts = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("baseProduct.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            baseProducts = (ArrayList) ois.readObject();

            ois.close();
            fis.close();

            return baseProducts;
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public void serializeOrdersToItemQuantity(Map<Order, Map<MenuItem, Integer>> ordersToItemQuantity) {
        try {
            FileOutputStream fos = new FileOutputStream("ordersToItemQuantity.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ordersToItemQuantity);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<Order, Map<MenuItem, Integer>> deserializeOrdersToItemQuantity() {
        Map<Order, Map<MenuItem, Integer>> ordersToItemQuantity = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream("ordersToItemQuantity.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ordersToItemQuantity = (HashMap) ois.readObject();

            ois.close();
            fis.close();

            return ordersToItemQuantity;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return null;
    }

    public void serializeNamesToUsers(Map<String, User> namesToUsers) {
        try {
            FileOutputStream fos = new FileOutputStream("namesToUsers.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(namesToUsers);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<String, User> deserializeNamesToUsers() {
        Map<String, User> namesToUsers = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream("namesToUsers.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            namesToUsers = (HashMap) ois.readObject();

            ois.close();
            fis.close();

            return namesToUsers;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return null;
    }

    public void serializeNamesToMenuItems(Map<String, MenuItem> namesToMenuItems) {
        try {
            FileOutputStream fos = new FileOutputStream("namesToMenuItems.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(namesToMenuItems);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<String, MenuItem> deserializeNamesToMenuItems() {
        Map<String, MenuItem> namesToMenuItems = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream("namesToMenuItems.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            namesToMenuItems = (HashMap) ois.readObject();

            ois.close();
            fis.close();

            return namesToMenuItems;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return null;
    }

    public void serializeLoggedInUser(User user) {
        try {
            FileOutputStream fos = new FileOutputStream("loggedInUser.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public User deserializeLoggedInUser() {
        try {
            FileInputStream fis = new FileInputStream("loggedInUser.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            User user = (User) ois.readObject();

            ois.close();
            fis.close();

            return user;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return null;
    }
}
