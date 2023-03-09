package bll.filter;

import model.MenuItem;

public class RatingFilter implements IFilter {
    private double minRating;

    public RatingFilter(double minRating) {
        this.minRating = minRating;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getRating() >= minRating;
    }
}
