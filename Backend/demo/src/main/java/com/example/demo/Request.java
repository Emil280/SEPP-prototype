package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Request{
    private String search;
    private int vegan;
    private int vegetarian;
    private int prepTime;

    public Request(String search, int vegan, int vegetarian, int prepTime) {
        this.search = search;
        this.vegan = vegan;
        this.vegetarian = vegetarian;
        this.prepTime = prepTime;
    }

    public void extractJson(){

    }
}