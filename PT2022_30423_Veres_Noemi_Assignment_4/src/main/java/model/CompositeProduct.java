package model;

import java.util.List;

public class CompositeProduct extends MenuItem {
    private List<MenuItem> components;

    public CompositeProduct(int id, String title, List<MenuItem> components) {
        super(id, title);
        this.components = components;
    }

    @Override
    public int computePrice() {
        return components.stream().mapToInt(MenuItem::computePrice).sum();
    }

    @Override
    public double getRating() {
        return components.stream().mapToDouble(MenuItem::getRating).average().getAsDouble();
    }

    @Override
    public int getCalories() {
        return components.stream().mapToInt(MenuItem::getCalories).sum();
    }

    @Override
    public int getProtein() {
        return components.stream().mapToInt(MenuItem::getProtein).sum();
    }

    @Override
    public int getSodium() {
        return components.stream().mapToInt(MenuItem::getSodium).sum();
    }

    @Override
    public int getFat() {
        return components.stream().mapToInt(MenuItem::getFat).sum();
    }

    @Override
    public boolean contains(MenuItem menuItem) {
        return this.equals(menuItem) ||
                components.stream().anyMatch(component -> component.contains(menuItem));
    }

    @Override
    public boolean contains(String menuItemName) {
        return this.getTitle().equals(menuItemName) ||
                components.stream().anyMatch(component -> component.contains(menuItemName));
    }

    public void updateComponents(List<MenuItem> components) {
        this.components = components;
    }
}
