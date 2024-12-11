package com.example.demo.FridgeItem;

public class FridgeItemId implements java.io.Serializable{
    private int userId;
    private int itemId;
    public FridgeItemId(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }
    public FridgeItemId() {}
}
