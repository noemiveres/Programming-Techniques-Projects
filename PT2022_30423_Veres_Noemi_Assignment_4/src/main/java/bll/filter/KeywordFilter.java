package bll.filter;
import model.MenuItem;

public class KeywordFilter implements IFilter{
    private final String keyword;

    public KeywordFilter(String keyword){
        this.keyword = keyword;
    }

    @Override
    public boolean filter(MenuItem menuItem) {
        return menuItem.getTitle().toLowerCase().contains(keyword.toLowerCase());
    }
}
