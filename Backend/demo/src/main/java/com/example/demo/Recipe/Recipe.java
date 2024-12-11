package com.example.demo.Recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe{

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "recipeID")
    private int id;

    @Column(name = "instructions", length = 10000)
    private String instructions;

    @Column(name = "timetomake")
    private String prepTime;

    private List<String> ingredients;

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", prepTime='" + prepTime + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

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

        if (prepTime.length() == 8){
            String[] values = prepTime.split(":");
            Duration duration = Duration.ofHours(Integer.parseInt(values[0]));
            duration = duration.plusMinutes(Integer.parseInt(values[1]));
            duration = duration.plusSeconds(Integer.parseInt(values[2]));
            return (int) duration.getSeconds();
        } else if (prepTime.length() == 5) {
            String[] values = prepTime.split(":");
            Duration duration = Duration.ofMinutes(Integer.parseInt(values[0]));
            duration = duration.plusSeconds(Integer.parseInt(values[1]));
            return (int) duration.getSeconds();
        } else {
            return Integer.parseInt(prepTime);
        }
    }



}