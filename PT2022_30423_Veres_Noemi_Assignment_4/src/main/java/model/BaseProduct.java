package model;

import java.io.Serializable;
/**
 * Represents a base product from which we can further compose other products
 */
public class BaseProduct extends MenuItem implements Serializable {
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public BaseProduct(int id, String title, double rating, int calories,
                       int protein, int fat, int sodium, int price) {
        super(id, title);
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public boolean contains(MenuItem menuItem) {
        return this.equals(menuItem);
    }

    @Override
    public boolean contains(String menuItemName) {
        return this.getTitle().equals(menuItemName);
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    @Override
    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    @Override
    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int computePrice() {
        return getPrice();
    }

    @Override
    public double getRating() {
        return rating;
    }
}
