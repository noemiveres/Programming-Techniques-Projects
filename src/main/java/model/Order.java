package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private int clientId;
    private LocalDateTime time;

    public Order(int orderId, int clientId, LocalDateTime time) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.time = time;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Two orders are equal, if both their ids and clientIds are equal.
     *
     * @param o is the object to compare with.
     * @return true if this object is equal to o, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId == order.orderId && clientId == order.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId);
    }

    /**
     * Computes the price of a list of items mapped to their quantity.
     *
     * @param itemsToQuantity is the list of items mapped to their quantity.
     * @return the total price of the products.
     */
    public static int computePrice(Map<MenuItem, Integer> itemsToQuantity) {
        return itemsToQuantity.keySet().stream().mapToInt(item ->
                itemsToQuantity.get(item) * item.computePrice()).sum();
    }
}
