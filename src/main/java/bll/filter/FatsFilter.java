package bll.filter;

import model.MenuItem;

public class FatsFilter implements IFilter{
    private final int maxFats;

    public FatsFilter(int maxFats) {
        this.maxFats = maxFats;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getFat() <= maxFats;
    }
}
