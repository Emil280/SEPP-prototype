package com.example.demo;

import jakarta.persistence.Entity;

@Entity
//@Table
public class RecipeIngredient {
    private int recipeId;
    private int itemId;
    private float quantity;

    public int getRecipeId() {
        return recipeId;
    }

    public int getItemId() {
        return itemId;
    }

    public float getQuantity() {
        return quantity;
    }
}
