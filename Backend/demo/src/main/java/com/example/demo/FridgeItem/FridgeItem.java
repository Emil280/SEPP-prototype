package com.example.demo.FridgeItem;


import jakarta.persistence.*;

@Entity
@Table(name = "fridge")
@IdClass(FridgeItemId.class)
public class FridgeItem {

    @Id
    @Column(name="userID")
    private int userId;

    @Id
    @Column(name="itemID")
    private int itemId;

    @Column(name = "quantity")
    private float quantity;

    public FridgeItem(int userId, int itemId, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
    public FridgeItem(){}
    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public float getQuantity() {
        return quantity;
    }
}
