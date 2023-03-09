package bll.filter;

import model.MenuItem;

public class CaloriesFilter implements IFilter{
    private final int maxCalories;

    public CaloriesFilter(int maxCalories) {
        this.maxCalories = maxCalories;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getCalories() <= maxCalories;
    }
}
