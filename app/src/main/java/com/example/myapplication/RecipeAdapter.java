package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipePrepTime.setText("Prep Time: " + recipe.getPrepTime());

        // Combine ingredients into a single string and set it
        StringBuilder ingredientsList = new StringBuilder();
        for (String ingredient : recipe.getIngredients()) {
            ingredientsList.append(ingredient).append("\n");
        }
        holder.recipeIngredients.setText("Ingredients:\n" + ingredientsList.toString());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // ViewHolder to hold references to the TextViews in item_recipe.xml
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipePrepTime;
        TextView recipeIngredients;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipePrepTime = itemView.findViewById(R.id.recipePrepTime);
            recipeIngredients = itemView.findViewById(R.id.recipeIngredients);
        }
    }
}

