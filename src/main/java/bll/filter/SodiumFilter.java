package bll.filter;

import model.MenuItem;

public class SodiumFilter implements IFilter {
    private int maxSodium;

    public SodiumFilter(int maxSodium) {
        this.maxSodium = maxSodium;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getSodium() <= maxSodium;
    }
}
