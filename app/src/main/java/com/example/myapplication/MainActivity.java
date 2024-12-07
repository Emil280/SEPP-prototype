package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> allRecipes;
    private List<Recipe> filteredRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load recipes from the JSON file
        allRecipes = RecipeUtils.loadRecipes(this);
        filteredRecipes = new ArrayList<>(allRecipes); // Initially show all recipes

        if (filteredRecipes != null) {
            recipeAdapter = new RecipeAdapter(filteredRecipes);
            recyclerView.setAdapter(recipeAdapter);
        } else {
            Toast.makeText(this, "Error loading recipes", Toast.LENGTH_SHORT).show();
        }

        // Set up filter button
        Button filterButton = findViewById(R.id.buttonFilter);
        filterButton.setOnClickListener(v -> showFilterPopup());
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
                .setPositiveButton("Apply Filters", (dialog, which) -> {
                    // Apply selected filters
                    applyFilters(veganCheckBox.isChecked(), vegetarianCheckBox.isChecked(),
                            prep0_20.isChecked(), prep20_40.isChecked(), prep40_60.isChecked(), prep60plus.isChecked());
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void applyFilters(boolean isVegan, boolean isVegetarian, boolean is0_20, boolean is20_40, boolean is40_60, boolean is60plus) {
        // Filter the recipes based on the selected filters
        filteredRecipes.clear();

        for (Recipe recipe : allRecipes) {
            boolean matches = true;

            // Check Vegan/Vegetarian filters
//            if (isVegan && !recipe.isVegan()) {
//                matches = false;
//            }
//            if (isVegetarian && !recipe.isVegetarian()) {
//                matches = false;
//            }

            // Check prep time filters
            int prepTime = Integer.parseInt(recipe.getPrepTime().replaceAll("[^0-9]", "")); // Extract number from string
            if (is0_20 && (prepTime < 0 || prepTime > 20)) {
                matches = false;
            }
            if (is20_40 && (prepTime < 20 || prepTime > 40)) {
                matches = false;
            }
            if (is40_60 && (prepTime < 40 || prepTime > 60)) {
                matches = false;
            }
            if (is60plus && prepTime <= 60) {
                matches = false;
            }

            // If recipe matches all selected filters, add it to the filtered list
            if (matches) {
                filteredRecipes.add(recipe);
            }
        }

        // Update RecyclerView with filtered recipes
        recipeAdapter.notifyDataSetChanged();
        Toast.makeText(this, filteredRecipes.size() + " recipes found", Toast.LENGTH_SHORT).show();
    }
}

