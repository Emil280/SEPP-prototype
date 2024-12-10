package com.example.demo;

public class Request{
    private String search;
    private int recipeType;
    private int prepTime;
    private int userId;

    public Request(String search, int recipeType, int prepTime,int userId) {
        this.search = search;
        this.recipeType = recipeType;
        this.prepTime = prepTime;
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public int getRecipeType() {
        return recipeType;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "search='" + search + '\'' +
                ", recipeType=" + recipeType +
                ", prepTime=" + prepTime +
                ", userId=" + userId +
                '}';
    }
}