package gui;

import bll.DeliveryService;
import bll.filter.*;
import dal.MyFileWriter;
import model.*;
import utils.TableFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

public class Controller implements ActionListener, Observer {
    private View view;
    private ClientWindow clientWindow;
    private AdminWindow adminWindow;
    private EmployeeWindow employeeWindow;
    private RegisterWindow registerWindow;

    private DeliveryService deliveryService = new DeliveryService();
    private MyFileWriter myFileWriter = new MyFileWriter();

    private String title, userName;
    private double rating;
    private int calories, protein, fat, sodium, price, quantity, N;
    private LocalTime startTime, endTime;
    private LocalDate day;
    Order order;
    Map<String, Integer> orderedItemNamesToQuantity = new HashMap<>();
    Map<String, MenuItem> menuItemNamesToMenuItems = new HashMap<>();
    List<String> componentNames = new ArrayList<>();


    public Controller(View view) throws IOException, ClassNotFoundException {
        this.view = view;
    }

    public Controller(ClientWindow clientWindow) throws IOException, ClassNotFoundException {
        deliveryService.addObserver(this);
        this.clientWindow = clientWindow;
    }

    public Controller(AdminWindow adminWindow) throws IOException, ClassNotFoundException {
        this.adminWindow = adminWindow;
    }

    public Controller(EmployeeWindow employeeWindow) throws IOException, ClassNotFoundException {
        this.employeeWindow = employeeWindow;
    }

    public Controller(RegisterWindow registerWindow) throws IOException, ClassNotFoundException {
        this.registerWindow = registerWindow;
    }

    public void updateBaseProductTable() {
        ArrayList<BaseProduct> objects = deliveryService.getBaseProducts();
        try {
            adminWindow.getBaseProductsTable().setModel(TableFactory.createTable(objects));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateProductTable() {
        ArrayList<BaseProduct> objects = deliveryService.getBaseProducts();
        try {
            clientWindow.getProductsTable().setModel(TableFactory.createTable(objects));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "Log in") {
            if (view.getUsernameTextField().getText().isEmpty() ||
                    view.getPasswordTextField().getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Empty fields.\nPlease make sure that no fields are empty",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (!deliveryService.existsUserWithUsername(view.getUsernameTextField().getText())) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Unsuccessful log-in.\nPlease make sure that you are registered",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (deliveryService.logIn(view.getUsernameTextField().getText(),
                    view.getPasswordTextField().getText())) {
                switch (deliveryService.getLoggedInUser().getTypeOfUser()) {
                    case ADMIN:
                        try {
                            deliveryService.serializeData();
                            AdminWindow adminWindow = new AdminWindow("Administrator");
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case CLIENT:
                        try {
                            deliveryService.serializeData();
                            ClientWindow clientWindow = new ClientWindow("Client");
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case EMPLOYEE:
                        try {
                            deliveryService.serializeData();
                            EmployeeWindow employeeWindow = new EmployeeWindow("Employee");
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong password!\n" +
                                "Please make sure that you insert the correct password.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command == "Register now") {
            try {
                deliveryService.serializeData();
                RegisterWindow registerWindow = new RegisterWindow("Register");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (command == "Register") {
            if (registerWindow.getUsernameTextField().getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "You must have a username!\nPlease make sure that you choose a username.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (registerWindow.getPassword1TextField().getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "You must have a password!\nPlease make sure that you choose a password.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (!registerWindow.getPassword1TextField().getText().equals(registerWindow.
                    getPassword2TextField().getText())) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "The two passwords do not match!\nPlease make sure that you insert the " +
                                "same password.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                String typeOfUser = String.valueOf(registerWindow.getTypeOfUserComboBox().getSelectedItem());
                TypeOfUser type = TypeOfUser.CLIENT;
                switch (typeOfUser) {
                    case "Administrator":
                        type = TypeOfUser.ADMIN;
                        break;
                    case "Client":
                        type = TypeOfUser.CLIENT;
                        break;
                    case "Employee":
                        type = TypeOfUser.EMPLOYEE;
                        break;
                }
                deliveryService.registerUser(registerWindow.getUsernameTextField().getText(),
                        registerWindow.getPassword1TextField().getText(), type);
                deliveryService.serializeData();
                try {
                    View view = new View("FOOD DELIVERY SYSTEM");
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command == "I'm already a member") {
            try {
                View view = new View("FOOD DELIVERY SYSTEM");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (command == "LOG OUT") {
            deliveryService.serializeData();
            deliveryService.logOut();
            try {
                View view = new View("FOOD DELIVERY SYSTEM");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(new JFrame(),
                    "You successfully logged out\nPlease close the bottom window", "INFO", JOptionPane.INFORMATION_MESSAGE);
        } else if (command == "Import base products") {
            deliveryService.importProductsFromCSV();
            updateBaseProductTable();
        } else if (command == "Add base product") {
            title = adminWindow.getAddBaseProductTable().getValueAt(0, 0).toString();
            rating = Double.parseDouble(adminWindow.getAddBaseProductTable().getValueAt(0, 1).toString());
            calories = Integer.parseInt(adminWindow.getAddBaseProductTable().getValueAt(0, 2).toString());
            protein = Integer.parseInt(adminWindow.getAddBaseProductTable().getValueAt(0, 3).toString());
            fat = Integer.parseInt(adminWindow.getAddBaseProductTable().getValueAt(0, 4).toString());
            sodium = Integer.parseInt(adminWindow.getAddBaseProductTable().getValueAt(0, 5).toString());
            price = Integer.parseInt(adminWindow.getAddBaseProductTable().getValueAt(0, 6).toString());
            deliveryService.createBaseProduct(title, rating, calories, protein, fat, sodium, price);
            updateBaseProductTable();
        } else if (command == "Delete product") {
            title = adminWindow.getDeleteProductTable().getValueAt(0, 0).toString();
            deliveryService.deleteProduct(title);
            updateBaseProductTable();
        } else if (command == "Edit product") {
            title = adminWindow.getEditProductTable().getValueAt(0, 0).toString();
            rating = Double.parseDouble(adminWindow.getEditProductTable().getValueAt(0, 1).toString());
            calories = Integer.parseInt(adminWindow.getEditProductTable().getValueAt(0, 2).toString());
            protein = Integer.parseInt(adminWindow.getEditProductTable().getValueAt(0, 3).toString());
            fat = Integer.parseInt(adminWindow.getEditProductTable().getValueAt(0, 4).toString());
            sodium = Integer.parseInt(adminWindow.getEditProductTable().getValueAt(0, 5).toString());
            price = Integer.parseInt(adminWindow.getEditProductTable().getValueAt(0, 6).toString());
            deliveryService.modifyBaseProduct(title, rating, calories, protein, fat, sodium, price);
            updateBaseProductTable();
        } else if (command == "Time interval report") {
            startTime = LocalTime.parse(adminWindow.getStartHourTextField().getText());
            endTime = LocalTime.parse(adminWindow.getEndHourTextField().getText());
            List<Order> orders = deliveryService.getOrdersInTimeInterval(startTime, endTime);
            try {
                myFileWriter.createOrderReport(startTime, endTime, orders);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (command == "Loyal clients report") {
            N = Integer.parseInt(adminWindow.getOrderedMoreThanXTimesTextField().getText());
            price = Integer.parseInt(adminWindow.getOrderedWithValueMoreThanYTextField().getText());
            List<User> users = deliveryService.getLoyalClients(N, price);
            try {
                myFileWriter.createLoyalClientsReport(N,price,users);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (command == "Products ordered on a day report"){
            day = LocalDate.parse(adminWindow.getDayTextField().getText());
            List<MenuItemToNoOfOrders> productsWithNoOfOrders = deliveryService.getMenuItemsOrderedOnSpecificDay(day);
            try {
                myFileWriter.createProductsOrderedADayReport(day,productsWithNoOfOrders);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(command == "Favourite products report"){
            N = Integer.parseInt(adminWindow.getOrderedMoreThanXTimesTextField().getText());
            List<MenuItem> products = deliveryService.getProductsOrderedMoreThanXTimes(N);
            try {
                myFileWriter.createFavouriteProductsReport(N,products);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (command == "Add item to menu"){
            ListSelectionModel model = adminWindow.getBaseProductsTable().getSelectionModel();
            int lead = model.getLeadSelectionIndex();
            title = adminWindow.getBaseProductsTable().getValueAt(lead, 0).toString();
            componentNames.add(title);
        } else if (command == "Finish creating composite product"){
            title = adminWindow.getNewCompositeProductTextField().getText();
            deliveryService.createCompositeProduct(title,componentNames);
            updateBaseProductTable();
            componentNames = new ArrayList<>();
        }
        else if (command == "APPLY") {
            List<IFilter> filters = new ArrayList<>();
            if (!clientWindow.getKeywordTextField().getText().isEmpty()) {
                KeywordFilter keywordFilter = new KeywordFilter(clientWindow.getKeywordTextField().getText());
                filters.add(keywordFilter);
            }
            if (!clientWindow.getRatingTextField().getText().isEmpty()) {
                RatingFilter ratingFilter = new RatingFilter(Double.parseDouble(clientWindow.getRatingTextField().getText()));
                filters.add(ratingFilter);
            }
            if (!clientWindow.getCaloriesTextField().getText().isEmpty()) {
                CaloriesFilter caloriesFilter = new CaloriesFilter(Integer.parseInt(clientWindow.getCaloriesTextField().getText()));
                filters.add(caloriesFilter);
            }
            if (!clientWindow.getProteinsTextField().getText().isEmpty()) {
                ProteinsFilter proteinsFilter = new ProteinsFilter(Integer.parseInt(clientWindow.getProteinsTextField().getText()));
                filters.add(proteinsFilter);
            }
            if (!clientWindow.getCaloriesTextField().getText().isEmpty()) {
                CaloriesFilter caloriesFilter = new CaloriesFilter(Integer.parseInt(clientWindow.getCaloriesTextField().getText()));
                filters.add(caloriesFilter);
            }
            if (!clientWindow.getFatsTextField().getText().isEmpty()) {
                FatsFilter fatsFilter = new FatsFilter(Integer.parseInt(clientWindow.getFatsTextField().getText()));
                filters.add(fatsFilter);
            }
            if (!clientWindow.getSodiumTextField().getText().isEmpty()) {
                SodiumFilter sodiumFilter = new SodiumFilter(Integer.parseInt(clientWindow.getSodiumTextField().getText()));
                filters.add(sodiumFilter);
            }
            if (!clientWindow.getPriceTextField().getText().isEmpty()) {
                PriceFilter priceFilter = new PriceFilter(Integer.parseInt(clientWindow.getPriceTextField().getText()));
                filters.add(priceFilter);
            }
            ArrayList<MenuItem> filteredMenuItems = (ArrayList<MenuItem>) deliveryService.getFilteredMenuItems(filters);
            try {
                clientWindow.getProductsTable().setModel(TableFactory.createTable(filteredMenuItems));
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        } else if (command == "ADD TO ORDER") {
            ListSelectionModel model = clientWindow.getProductsTable().getSelectionModel();
            int lead = model.getLeadSelectionIndex();
            title = clientWindow.getProductsTable().getValueAt(lead, 0).toString();
            quantity = Integer.parseInt(clientWindow.getQuantityTextField().getText());
            orderedItemNamesToQuantity.put(title, quantity);
            menuItemNamesToMenuItems.put(title, deliveryService.getProductWithTitle(title));
        } else if (command == "Place order") {
            try {
                deliveryService.createOrder(deliveryService.getLoggedInUser(), orderedItemNamesToQuantity, menuItemNamesToMenuItems);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            deliveryService.serializeData();
            EmployeeWindow employeeWindow = null;
            try {
                employeeWindow = new EmployeeWindow("Employee");
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            final String[] string = {null};
            string[0] = "You have to prepare: ";
            orderedItemNamesToQuantity.forEach((key, value) -> string[0] += " <br> " + key
                    + " - quantity: " + value);
            employeeWindow.getYouWillBeNotifiedWhenAnOrderIsPlacedLabel().setText("<html> " + string[0]
                    + " <html>");
            menuItemNamesToMenuItems = new HashMap<>();
            orderedItemNamesToQuantity = new HashMap<>();
            deliveryService.serializeData();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Order order = (Order) arg;
        JOptionPane.showMessageDialog(new JFrame(),
                "A new order with id " + order.getOrderId() + " was placed.\n Employee will prepare it for delivery",
                "New order",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
