package bll;

import bll.filter.IFilter;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IDeliveryServiceProcessing {
    /**
     * Creates a new base product by adding it to the menu, having the given attributes.
     *
     * @param title is the name of the product
     * @param rating is the rating oh the product
     * @param calories is the number of calories
     * @param protein is the protein amount for the product
     * @param fat is the fat amount
     * @param sodium is the sodium amount
     * @param price is the price of the product
     * @pre there is a logged-in user
     * @pre the type of user is admin
     * @pre there isn't already a product with that name
     * @post a new product with the specified attributes is present
     * @post the number of products is increased by 1
     * @invariant isWellFormed
     */
    void createBaseProduct(String title, double rating, int calories, int protein, int fat,
                           int sodium, int price);
    /**
     * If the provided userName matches the password, the user can log-in.
     *
     * @param userName is the username of the user who wants to log in
     * @param password is the provided password
     * @return true if userName and password match
     * @pre there is no other logged-in user
     * @post loggedInUser will become the user with userName and given password
     * @invariant isWellFormed()
     */
    boolean logIn(String userName, String password);

    /**
     * The currently loggedInUser logs out
     *
     * @pre there is a logged-in user
     * @post there is no logged-in user anymore
     * @invariant isWellFormed()
     */
    void logOut();

    /**
     * Registers a new user to the food delivery system
     *
     * @param userName is the username of the new user
     * @param password is the password of the new user
     * @param type is the type of the user (client, employee, admin)
     * @return the user
     * @pre there is no user with the provided username and password
     * @post a user with the provided username and password will exist, the number of users increases by 1
     * @invariant isWellFormed()
     */
    User registerUser(String userName, String password, TypeOfUser type);

    /**
     * Determines whether a user with the provided username exists
     *
     * @param userName is the name of the user we want to find
     * @return true if a user with the given userName exists, false otherwise
     * @pre userName != null
     * @invariant isWellFormed()
     */
    boolean existsUserWithUsername(String userName);

    /**
     * Imports all the products from the CSV file to the list of products, eliminating duplicates.
     * @post the list of products will contain the products from CSV
     * @invariant isWellFormed().
     */
    void importProductsFromCSV();

    /**
     * Shows whether the object is in a valid state.
     *
     * @return true if the object is in a valid state, false otherwise.
     */
    boolean isWellFormed();

    boolean existsProductWithTitle(String title);

    /**
     * A menu item with the provided title is obtained
     *
     * @param title is the title of the item we search for
     * @return the MenuItem with the given title if it exists
     * @pre the MenuItem should exist
     * @invariant isWellFormed
     */
    MenuItem getProductWithTitle(String title);

    void deleteProduct(String title);

    void modifyBaseProduct(String title, double newRating, int newCalories,
                           int newProteinsAmount, int newFatAmount, int newSodiumAmount, int newPrice);

    void createCompositeProduct(String title, List<String> componentNames);

    void modifyCompositeProduct(String oldTitle, String newTitle, List<String> newComponentNames);

    List<Order> getOrdersInTimeInterval(LocalTime startTime, LocalTime endTime);

    List<MenuItem> getProductsOrderedMoreThanXTimes(int minTimes);

    List<User> getLoyalClients(int minOrders, int minPrice);

    List<MenuItemToNoOfOrders> getMenuItemsOrderedOnSpecificDay(LocalDate day);

    List<MenuItem> getFilteredMenuItems(List<IFilter> filters);

    Order createOrder(User client, Map<String, Integer> orderedItemNamesToQuantity,
                      Map<String, MenuItem> menuItemNamesToMenuItems) throws IOException;

}
