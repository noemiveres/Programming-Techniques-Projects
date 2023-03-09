package model;

public class MenuItemToNoOfOrders {
    private int id;
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;
    private int noOfOrders;

    public MenuItemToNoOfOrders(int id, String title, double rating, int calories, int protein, int fat,
                                int sodium, int price, int noOfOrders) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
        this.noOfOrders = noOfOrders;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPrice() {
        return price;
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }
}
