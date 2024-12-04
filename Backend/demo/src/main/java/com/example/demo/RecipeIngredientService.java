package com.example.demo;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public List<RecipeIngredient> getRecipeIngredients(){
        return recipeIngredientRepository.findAll();
    }
    public List<RecipeIngredient> getRecipeIngredientsById(int id){
        return recipeIngredientRepository.findByRecipeId(id);
    }
}
