package com.example.demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Integer>{


    //if this dont work use @Query(value = sql, nativeQuery = true)  instead
    List<RecipeIngredient> findByRecipeId(int recipeId);
}