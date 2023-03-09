package start;

import bll.DeliveryService;
import dal.Serialization;
import gui.View;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Veres Noemi
 * @Since: Mai 10, 2022
 */
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
//		DeliveryService myDeliveryService = new DeliveryService();
//		myDeliveryService.importProductsFromCSV();
		Serialization serialization = new Serialization();
//		String fileName = "C:\\PT2022_30423_Veres_Noemi_Assignment_4\\products.csv";
//		List<String> list = new ArrayList<>();
//
//		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
//
//			//convert it into a List
//			list = stream
//					.distinct()
//					.collect(Collectors.toList());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<BaseProduct> baseProducts = new ArrayList<>();
//		for (String line : list){
//			String[] elements = line.split(",");
//			if(elements[0].equals("Title")) {
//				continue;
//			}
//				BaseProduct newBaseProduct = new BaseProduct(1, elements[0],
//						Double.parseDouble(elements[1]), Integer.parseInt(elements[2]),
//						Integer.parseInt(elements[3]), Integer.parseInt(elements[4]),
//						Integer.parseInt(elements[5]), Integer.parseInt(elements[6]));
//				baseProducts.add(newBaseProduct);
//		}
//
//		serialization.serializeBaseProducts(baseProducts);
//
//		list.forEach(System.out::println);

//		Map<String, User> namesToUsers = new HashMap<>();
//		User user = new User(1,"admin", "test", TypeOfUser.ADMIN);
//		namesToUsers.put(user.getUsername(), user);
//		serialization.serializeNamesToUsers(namesToUsers);

//		List<BaseProduct> baseProducts = new ArrayList<>();
//		BaseProduct baseProduct = new BaseProduct(1,"Strawberry", 4.8, 30, 10, 2, 0, 10);
//		baseProducts.add(baseProduct);
//		serialization.serializeBaseProducts(baseProducts);
//
//		Map<String, MenuItem> namesToMenuItems = new HashMap<>();
//		MenuItem menuItem = new BaseProduct(1,"Corn", 5.0, 60, 12, 5, 2, 5);
//		namesToMenuItems.put(menuItem.getTitle(), menuItem);
//		serialization.serializeNamesToMenuItems(namesToMenuItems);
////
//		Map<Order, Map<MenuItem, Integer>> ordersToItemQuantity = new HashMap<>();
//		Order order = new Order(1,1, LocalDateTime.now());
//		Map<MenuItem, Integer> menuItemToQuantity = new HashMap<>();
//		menuItemToQuantity.put(menuItem,2);
//		ordersToItemQuantity.put(order,menuItemToQuantity);
//		serialization.serializeOrdersToItemQuantity(ordersToItemQuantity);

//		serialization.serializeLoggedInUser(new User(1,"Nono","Noemi", TypeOfUser.CLIENT));


		JFrame frame = new View("Food Delivery System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		frame.setVisible(true);


	}

}
