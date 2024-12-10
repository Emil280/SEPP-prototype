package com.example.demo.RecipeIngredient;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient {
    public RecipeIngredient(int recipeId, int itemId, float quantity) {
        this.recipeId = recipeId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
    public RecipeIngredient() {}
    @Id
    @Column(name = "recipeid")
    private int recipeId;

    @Id
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
