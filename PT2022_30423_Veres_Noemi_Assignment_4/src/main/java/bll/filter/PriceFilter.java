package bll.filter;

import model.MenuItem;

public class PriceFilter implements IFilter{
    private final int maxPrice;

    public PriceFilter(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.computePrice() <= maxPrice;
    }
}
