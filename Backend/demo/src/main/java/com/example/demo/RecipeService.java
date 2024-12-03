package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getRecipes(){
        return recipeRepository.findAll();

    }
}