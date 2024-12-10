package com.example.myapplication;

import java.util.List;

public class Recipe {
    private String name;
    private String prepTime;
    private List<String> ingredients;

    // Constructor
    public Recipe(String name, String prepTime, List<String> ingredients) {
        this.name = name;
        this.prepTime = prepTime;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
