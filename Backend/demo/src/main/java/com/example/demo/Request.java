package com.example.demo;

import java.time.Duration;

public class Request{
    private String search;
    private int recipeType;
    private String time;
    private int userId;

    public Request(String search, int recipeType, String prepTime,int userId) {
        this.search = search;
        this.recipeType = recipeType;
        this.time = prepTime;
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public int getRecipeType() {
        return recipeType;
    }

    public int getTime() {

        if (time.length() == 8){
            String[] values = time.split(":");
            Duration duration = Duration.ofHours(Integer.parseInt(values[0]));
            duration = duration.plusMinutes(Integer.parseInt(values[1]));
            duration = duration.plusSeconds(Integer.parseInt(values[2]));
            return (int) duration.getSeconds();
        } else if (time.length() == 5) {
            String[] values = time.split(":");
            Duration duration = Duration.ofMinutes(Integer.parseInt(values[0]));
            duration = duration.plusSeconds(Integer.parseInt(values[1]));
            return (int) duration.getSeconds();
        } else {
            return Integer.parseInt(time);
        }
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "search='" + search + '\'' +
                ", recipeType=" + recipeType +
                ", time=" + time +
                ", userId=" + userId +
                '}';
    }
}