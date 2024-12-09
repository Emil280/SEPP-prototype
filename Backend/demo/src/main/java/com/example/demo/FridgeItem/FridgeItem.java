package com.example.demo.FridgeItem;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fridge")
public class FridgeItem {

    @Column(name="userID")
    private int userId;

    @Column(name="itemID")
    private int itemId;

    @Column(name = "quantity")
    private int quantity;

    public FridgeItem(int userId, int itemId, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
