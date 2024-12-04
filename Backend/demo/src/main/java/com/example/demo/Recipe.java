package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Entity
//@Table
public class Recipe{
    private String name;
    private int id;
    private String instructions;
    private int time;
    private int recipeType;
    //time is in minutes
    //private image image idk how to do this yet


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getTime() {
        return time;
    }

    public int getRecipeType() {
        return recipeType;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", instructions='" + instructions + '\'' +
                ", time=" + time +
                ", recipeType=" + recipeType +
                '}';
    }
}