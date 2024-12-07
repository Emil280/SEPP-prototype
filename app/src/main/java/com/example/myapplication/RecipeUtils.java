package com.example.myapplication;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.util.List;

public class RecipeUtils {

    // Method to parse the JSON file into a list of Recipe objects
    public static List<Recipe> loadRecipes(Context context) {
        try {
            // Open the JSON file in assets folder
            InputStreamReader reader = new InputStreamReader(context.getAssets().open("recipes.json"));

            // Parse the JSON using Gson
            Gson gson = new Gson();
            TypeToken<List<Recipe>> token = new TypeToken<List<Recipe>>() {};
            List<Recipe> recipes = gson.fromJson(reader, token.getType());

            reader.close();
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

