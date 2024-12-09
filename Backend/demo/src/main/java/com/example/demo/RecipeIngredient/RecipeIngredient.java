package com.example.demo.RecipeIngredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredients")
public class RecipeIngredient {
    public RecipeIngredient(int recipeId, int itemId, float quantity) {
        this.recipeId = recipeId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
    @Id
    @Column(name = "recipeid")
    private int recipeId;
    @Column(name="itemid")
    private int itemId;
    @Column(name = "quantity")
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
