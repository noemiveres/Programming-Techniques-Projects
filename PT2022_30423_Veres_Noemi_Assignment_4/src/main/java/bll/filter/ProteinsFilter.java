package bll.filter;

import model.MenuItem;

public class ProteinsFilter implements IFilter{
    private final int minProteins;

    public ProteinsFilter(int minProteins) {
        this.minProteins = minProteins;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getProtein() >= minProteins;
    }
}
