package com.example.demo.RecipeIngredient;

public class RecipeIngredientId implements java.io.Serializable{
    private int recipeId;
    private int itemId;
    public RecipeIngredientId(int recipeId, int itemId) {
        this.recipeId = recipeId;
        this.itemId = itemId;
    }
    public RecipeIngredientId() {}
}
