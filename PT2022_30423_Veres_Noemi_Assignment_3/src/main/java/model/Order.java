package model;

import java.time.LocalDateTime;

/**
 * Represents and order from a certain client of a certain product (with a specified quantity and unit price) for
 * the warehouse.
 * @Author: Veres Noemi
 * @Since: Apr 14, 2022
 */
public class Order {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;
    private int unitPrice;
    private LocalDateTime time;

    public Order(int id, int clientId, int productId, int quantity, int unitPrice, LocalDateTime timestamp) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.time = timestamp;
    }

    /** Parameterless constructor required for each database entity. */
    public Order() {

    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", time=" + time +
                '}';
    }
}
