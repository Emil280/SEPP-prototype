package com.example.demo.Recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "recipes")
public class Recipe{

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "recipeID")
    private int id;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "timetomake")
    private String time;

    @Column(name = "picturelink")
    private String pictureLink;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getTime() {
        return time;
    }

    public String getPictureLink(){
        return pictureLink;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", instructions='" + instructions + '\'' +
                ", time='" + time + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                '}';
    }
}