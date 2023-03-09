package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {
    private int id;
    private String title;

    public MenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract int computePrice();

    public abstract double getRating();

    public abstract int getProtein();

    public abstract int getSodium();

    public abstract int getFat();

    public abstract int getCalories();

    /**
     * Specifies whether this item contains directly or indirectly, as component, the given
     * menuItem.
     *
     * @param menuItem is the item which is searches as a component.
     * @return true if this item contains the searched menu item, false otherwise.
     */
    public abstract boolean contains(MenuItem menuItem);

    /**
     * Specifies whether this item contains directly or indirectly, as component, a menu item
     * with the given name.
     *
     * @param menuItemName is the item's name which is searches as a component.
     * @return true if this item contains the searched menu item, false otherwise.
     */
    public abstract boolean contains(String menuItemName);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
