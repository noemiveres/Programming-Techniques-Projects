package bll;

import bll.filter.IFilter;
import dal.MyFileWriter;
import dal.Serialization;
import gui.Controller;
import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private ArrayList<BaseProduct> baseProducts;
    public Map<Order, Map<MenuItem, Integer>> ordersToItemQuantity;
    private final Map<String, MenuItem> namesToMenuItems;
    private final Map<String, User> namesToUsers;
    private User loggedInUser;
    private MyFileWriter fileWriter = new MyFileWriter();
    private Serialization serialization;
    private Controller controller;

    public DeliveryService() {
        serialization = new Serialization();
        baseProducts = serialization.deserializeBaseProducts();
        ordersToItemQuantity = serialization.deserializeOrdersToItemQuantity();
        namesToMenuItems = serialization.deserializeNamesToMenuItems();
        namesToUsers = serialization.deserializeNamesToUsers();
        loggedInUser = serialization.deserializeLoggedInUser();
    }

    public void serializeData(){
        serialization.serializeBaseProducts(baseProducts);
        serialization.serializeNamesToUsers(namesToUsers);
        serialization.serializeOrdersToItemQuantity(ordersToItemQuantity);
        serialization.serializeNamesToMenuItems(namesToMenuItems);
        serialization.serializeLoggedInUser(loggedInUser);
    }

    @Override
    public void createBaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        assert isWellFormed();
        assert loggedInUser != null;
        assert loggedInUser.getTypeOfUser() == TypeOfUser.ADMIN;
        assert !namesToMenuItems.containsKey(title);
        int preNoOfMenuItems = namesToMenuItems.size();
        MenuItem menuItem = new BaseProduct(generateMenuItemId(), title, rating, calories, protein, fat,
                sodium, price);
        baseProducts.add((BaseProduct) menuItem);
        namesToMenuItems.put(menuItem.getTitle(), menuItem);
        assert isWellFormed();
        assert preNoOfMenuItems + 1 == namesToMenuItems.size();
        assert namesToMenuItems.containsKey(title);
        assert namesToMenuItems.get(title).getRating() == rating;
        assert namesToMenuItems.get(title).getCalories() == calories;
        assert namesToMenuItems.get(title).getProtein() == protein;
        assert namesToMenuItems.get(title).getFat() == fat;
        assert namesToMenuItems.get(title).getSodium() == sodium;
        assert namesToMenuItems.get(title).computePrice() == price;
    }

    @Override
    public void importProductsFromCSV() {
        String fileName = "C:\\PT2022_30423_Veres_Noemi_Assignment_4\\products.csv";
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            //convert it into a List
            list = stream
                    .distinct()
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //list.forEach(System.out::println);
        baseProducts = new ArrayList<>();
        for (String line : list){
            String[] elements = line.split(",");
            if(elements[0].equals("Title")) {
                continue;
            }
            else if(getBaseProductId(elements[0]) == -1){   // if it's not present so far
                BaseProduct newBaseProduct = new BaseProduct(generateMenuItemId(), elements[0],
                        Double.parseDouble(elements[1]), Integer.parseInt(elements[2]),
                        Integer.parseInt(elements[3]), Integer.parseInt(elements[4]),
                        Integer.parseInt(elements[5]), Integer.parseInt(elements[6]));
                baseProducts.add(newBaseProduct);
                namesToMenuItems.put(newBaseProduct.getTitle(), newBaseProduct);
            }
        }
    }

    public ArrayList<BaseProduct> getBaseProducts(){
        return baseProducts;
    }

    public int getBaseProductId(String title) {
        if (baseProducts.contains(getProductWithTitle(title))) {
            return getProductWithTitle(title).getId();
        } else {
            return -1;
        }
    }

    public int getMenuItemId(String title) {
        if (namesToMenuItems.containsKey(title)) {
            return namesToMenuItems.get(title).getId();
        } else {
            return -1;

        }
    }

    @Override
    public boolean isWellFormed() {
        return !namesToMenuItems.isEmpty() && !namesToMenuItems.containsKey(null)
                && !namesToMenuItems.containsValue(null) && !namesToUsers.isEmpty()
                && !namesToUsers.containsKey(null) && !namesToUsers.containsValue(null)
                && !ordersToItemQuantity.isEmpty() && !ordersToItemQuantity.containsKey(null)
                && !ordersToItemQuantity.containsValue(null)
                && ordersToItemQuantity.values().stream()
                        .flatMap(menuItemToQuantityMap -> menuItemToQuantityMap.entrySet().stream())
                        .noneMatch(element -> (element.getKey() == null || element.getValue() == null));
    }   // fiecare order sa aiba cel putin un element/menuItem

    @Override
    public Order createOrder(User client, Map<String, Integer> orderedItemNamesToQuantity,
                             Map<String, MenuItem> menuItemNamesToMenuItems) throws IOException {
        Order order = new Order(generateOrderId(), client.getId(), LocalDateTime.now());
        Map<MenuItem, Integer> menuItemToQuantity = new HashMap<>();
        Set<String> orderedMenuItemNames = orderedItemNamesToQuantity.keySet();
        for(String menuItemName : orderedMenuItemNames){
            menuItemToQuantity.put(menuItemNamesToMenuItems.get(menuItemName),
                    orderedItemNamesToQuantity.get(menuItemName));
        }
        ordersToItemQuantity.put(order, menuItemToQuantity);
        fileWriter.createBill(client,order,menuItemToQuantity);
        setChanged();
        notifyObservers(order);
        return order;
    }


    private int generateOrderId() {
        int tryId;
        do {
            tryId = (int) (Math.random() * Integer.MAX_VALUE);
        } while (existsOrderWithId(tryId));
        return tryId;
    }

    private boolean existsOrderWithId(int id) {
        return ordersToItemQuantity.keySet().stream().anyMatch(order -> order.getOrderId() == id);
    }

    @Override
    public List<Order> getOrdersInTimeInterval(LocalTime startTime, LocalTime endTime) {
        return ordersToItemQuantity.keySet().stream()
                .filter(order -> order.getTime().toLocalTime().isAfter(startTime) &&
                        order.getTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> getProductsOrderedMoreThanXTimes(int minTimes) {
        return ordersToItemQuantity.values().stream()
                .flatMap(itemsToQuantity -> itemsToQuantity.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .filter(itemToNoOfOrders -> itemToNoOfOrders.getValue() >= minTimes)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getLoyalClients(int minOrders, int minPrice) {
        return ordersToItemQuantity.keySet().stream()
                .filter(order -> Order.computePrice(ordersToItemQuantity.get(order)) >= minPrice)
                .collect(Collectors.groupingBy(Order::getClientId, Collectors.counting()))
                .entrySet().stream()
                .filter(clientIdToNoOfOrders -> clientIdToNoOfOrders.getValue() >= minOrders)
                .map(client -> getUserWithId(client.getKey(), namesToUsers).get())
                .collect(Collectors.toList());
    }

    private Optional<User> getUserWithId(int userId, Map<String, User> namesToUsers) {
        List<User> matchingUsers =
                namesToUsers.values().stream().filter(user -> user.getId() == userId).collect(Collectors.toList());
        if (matchingUsers.size() > 0) {
            return Optional.of(matchingUsers.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MenuItemToNoOfOrders> getMenuItemsOrderedOnSpecificDay(LocalDate day) {
        return  ordersToItemQuantity.entrySet().stream()
                .filter(orderToItemQuantity -> orderToItemQuantity.getKey().getTime().toLocalDate()
                        .equals(day))
                .map(Map.Entry::getValue)
                .flatMap(itemToQuantityMap -> itemToQuantityMap.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .map(menuItemToNoOfOrders -> new MenuItemToNoOfOrders(menuItemToNoOfOrders.getKey().getId(),
                        menuItemToNoOfOrders.getKey().getTitle(), menuItemToNoOfOrders.getKey().getRating(),
                        menuItemToNoOfOrders.getKey().getCalories(), menuItemToNoOfOrders.getKey().getProtein(),
                        menuItemToNoOfOrders.getKey().getFat(), menuItemToNoOfOrders.getKey().getSodium(),
                        menuItemToNoOfOrders.getKey().computePrice(), menuItemToNoOfOrders.getValue()))
                .collect(Collectors.toList());
    }

    public int generateMenuItemId() {
        int tryId;
        do {
            tryId = (int) (Math.random() * Integer.MAX_VALUE);
        } while (existsMenuItemWithId(tryId));
        return tryId;
    }

    private boolean existsMenuItemWithId(int id) {
        return namesToMenuItems.values().stream().anyMatch(menuItem -> menuItem.getId() == id);
    }

    @Override
    public boolean existsProductWithTitle(String title) {
        return namesToMenuItems.containsKey(title);
    }

    @Override
    public MenuItem getProductWithTitle(String title) {
        return namesToMenuItems.get(title);
    }

    @Override
    public void deleteProduct(String title) {
        MenuItem menuItem = getProductWithTitle(title);
        baseProducts.remove((BaseProduct) menuItem);
        namesToMenuItems.remove(title);
    }

    @Override
    public void modifyBaseProduct(String title, double newRating, int newCalories,
                                  int newProteinsAmount, int newFatAmount, int newSodiumAmount,
                                  int newPrice) {
        BaseProduct baseProduct = (BaseProduct) namesToMenuItems.get(title);
        baseProduct.setRating(newRating);
        baseProduct.setCalories(newCalories);
        baseProduct.setProtein(newProteinsAmount);
        baseProduct.setFat(newFatAmount);
        baseProduct.setSodium(newSodiumAmount);
        baseProduct.setPrice(newPrice);
        namesToMenuItems.remove(title);
        baseProducts.remove(baseProduct);
        namesToMenuItems.put(title, baseProduct);
        baseProducts.add(baseProduct);
    }

    @Override
    public void createCompositeProduct(String title, List<String> componentNames) {
        CompositeProduct newCompositeProduct = new CompositeProduct(generateMenuItemId(), title,
                getMenuItemsWithNames(componentNames));
        namesToMenuItems.put(newCompositeProduct.getTitle(), newCompositeProduct);
        BaseProduct baseProduct = new BaseProduct(newCompositeProduct.getId(), title,
                newCompositeProduct.getRating(), newCompositeProduct.getCalories(),
                newCompositeProduct.getProtein(), newCompositeProduct.getFat(),
                newCompositeProduct.getSodium(), newCompositeProduct.computePrice());
        baseProducts.add(baseProduct);

    }

    @Override
    public void modifyCompositeProduct(String oldTitle, String newTitle, List<String> newComponentNames) {
        CompositeProduct compositeProduct= (CompositeProduct) namesToMenuItems.get(oldTitle);
        compositeProduct.setTitle(newTitle);
        compositeProduct.updateComponents(getMenuItemsWithNames(newComponentNames));
        namesToMenuItems.remove(oldTitle);
        namesToMenuItems.put(compositeProduct.getTitle(), compositeProduct);
    }

    private List<MenuItem> getMenuItemsWithNames(Collection<String> names) {
        List<MenuItem> menuItems = new ArrayList<>();
        for (String itemName : names) {
            menuItems.add(namesToMenuItems.get(itemName));
        }
        return menuItems;
    }

    @Override
    public List<MenuItem> getFilteredMenuItems(List<IFilter> filters) {
        Stream<MenuItem> menuItemStream = namesToMenuItems.values().stream();
        for (IFilter f : filters){
            menuItemStream = menuItemStream.filter(f::filter);
        }
        return menuItemStream.collect(Collectors.toList());
    }

    @Override
    public boolean logIn(String userName, String password) {
        assert loggedInUser == null;
        User loggingUser = namesToUsers.get(userName);
        loggedInUser = loggingUser;
        return loggingUser.getPassword().equals(password);
    }

    @Override
    public void logOut() {
        assert loggedInUser != null;
        loggedInUser = null;
        assert loggedInUser == null;
    }

    @Override
    public User registerUser(String userName, String password, TypeOfUser type) {
        assert !existsUserWithUsername(userName);
        int preNoOfUsers = namesToUsers.size();
        User newUser = new User(generateClientId(), userName, password, type);
        namesToUsers.put(newUser.getUsername(), newUser);
        assert preNoOfUsers + 1 == namesToUsers.size();
        return newUser;
    }

    private int generateClientId() {
        int tryId;
        do {
            tryId = (int) (Math.random() * Integer.MAX_VALUE);
        } while (existsUserWithId(tryId));
        return tryId;
    }

    private boolean existsUserWithId(int id) {
        return namesToUsers.values().stream().anyMatch(user -> user.getId() == id);
    }

    @Override
    public boolean existsUserWithUsername(String userName) {
        assert userName != null;
        return namesToUsers.get(userName) != null;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }
}
