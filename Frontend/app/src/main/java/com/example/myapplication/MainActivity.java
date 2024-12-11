package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> allRecipes = new ArrayList<>();
    private List<Recipe> filteredRecipes = new ArrayList<>();
    private final String API_URL = "http://localhost:8080/findFilteredRecipes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchRecipes(); // Load recipes from assets

        Button filterButton = findViewById(R.id.buttonFilter);
        filterButton.setOnClickListener(v -> showFilterPopup());
    }

    private void fetchRecipes() {
        // Create a new Thread to perform the file reading and parsing operations
        new Thread(() -> {
            BufferedReader reader = null;
            try {
                // Open the recipes.json file from assets
                AssetManager assetManager = getAssets();
                reader = new BufferedReader(new InputStreamReader(assetManager.open("recipes.json")));

                // Read the entire file content into a StringBuilder
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                // Parse the JSON response
                JSONArray jsonResponse = new JSONArray(responseBuilder.toString());
                List<Recipe> recipes = new ArrayList<>();

                // Parse the JSON array into Recipe objects
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject recipeJson = jsonResponse.getJSONObject(i);
                    String name = recipeJson.getString("name");
                    String prepTime = recipeJson.getString("prepTime");
                    List<String> ingredients = new ArrayList<>();
                    JSONArray ingredientsJson = recipeJson.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientsJson.length(); j++) {
                        ingredients.add(ingredientsJson.getString(j));
                    }
                    // Create a Recipe object and add it to the list
                    recipes.add(new Recipe(name, prepTime, ingredients));
                }

                // Update the UI with the recipes list
                runOnUiThread(() -> {
                    allRecipes.clear();
                    filteredRecipes.clear();

                    // Add the recipes to the list
                    allRecipes.addAll(recipes);  // Ensure you're adding the parsed recipes here
                    filteredRecipes.addAll(allRecipes);  // Update filtered recipes list

                    // Update the RecyclerView and notify the adapter
                    if (recipeAdapter == null) {
                        recipeAdapter = new RecipeAdapter(filteredRecipes);
                        recyclerView.setAdapter(recipeAdapter);
                    } else {
                        recipeAdapter.notifyDataSetChanged();
                    }

                    // Update the recipe count text
                    updateRecipeCount();

                    Toast.makeText(MainActivity.this, "Recipes loaded", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (Exception ignored) {}
            }
        }).start();
    }

    private void updateRecipeCount() {
        // Assuming you have a TextView to show the recipe count, update it
        TextView recipeCountText = findViewById(R.id.recipeCountText);  // Replace with your actual TextView ID
        recipeCountText.setText("Recipes: " + filteredRecipes.size());
    }



    private void showFilterPopup() {
        // Inflate the filter popup layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_filter, null);

        // Create checkboxes
        CheckBox veganCheckBox = popupView.findViewById(R.id.checkboxVegan);
        CheckBox vegetarianCheckBox = popupView.findViewById(R.id.checkboxVegetarian);
        CheckBox prep0_20 = popupView.findViewById(R.id.checkbox0_20);
        CheckBox prep20_40 = popupView.findViewById(R.id.checkbox20_40);
        CheckBox prep40_60 = popupView.findViewById(R.id.checkbox40_60);
        CheckBox prep60plus = popupView.findViewById(R.id.checkbox60plus);

        // Create and show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Filters")
                .setView(popupView)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private String createFilterJson(boolean isVegan, boolean isVegetarian, boolean is0_20, boolean is20_40, boolean is40_60, boolean is60plus) {
        StringBuilder filterBuilder = new StringBuilder("{");

        if (isVegan) filterBuilder.append("\"vegan\":true,");
        if (isVegetarian) filterBuilder.append("\"vegetarian\":true,");
        if (is0_20) filterBuilder.append("\"prepTimeRange\":\"0-20\",");
        if (is20_40) filterBuilder.append("\"prepTimeRange\":\"20-40\",");
        if (is40_60) filterBuilder.append("\"prepTimeRange\":\"40-60\",");
        if (is60plus) filterBuilder.append("\"prepTimeRange\":\"60+\",");


        if (filterBuilder.length() == 1) {
            return "{\"default\":true}";
        }

        if (filterBuilder.charAt(filterBuilder.length() - 1) == ',') {
            filterBuilder.deleteCharAt(filterBuilder.length() - 1);
        }

        filterBuilder.append("}");
        return filterBuilder.toString();
    }
}

