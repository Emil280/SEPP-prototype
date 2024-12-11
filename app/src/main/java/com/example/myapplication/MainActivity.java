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

        fetchRecipes("",0,0,0); // Fetch all recipes initially

        Button filterButton = findViewById(R.id.buttonFilter);
        filterButton.setOnClickListener(v -> showFilterPopup());
    }

    private void fetchRecipes(String search, int recipeType, int prepTime, int userId) {
        // Create a new Thread to perform network operations
        new Thread(() -> {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Construct the request URL
                URL url = new URL("http://localhost:8080/findFilteredRecipes");
                // Create the Request JSON object
                JSONObject requestJson = new JSONObject();
                requestJson.put("search", search);
                requestJson.put("recipeType", recipeType);
                requestJson.put("prepTime", prepTime);
                requestJson.put("userId", userId);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestJson.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Read the response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    JSONArray jsonResponse = new JSONArray(responseBuilder.toString());
                    List<String> recipes = new ArrayList<>();
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        recipes.add(jsonResponse.getString(i));
                    }
                    runOnUiThread(() -> {
                        allRecipes.clear();
                        filteredRecipes.clear();

                        for (String recipe : recipes) {
                            allRecipes.add(new Recipe(""));
                        }
                        filteredRecipes.addAll(allRecipes);

                        if (recipeAdapter == null) {
                            recipeAdapter = new RecipeAdapter(filteredRecipes);
                            recyclerView.setAdapter(recipeAdapter);
                        } else {
                            recipeAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(MainActivity.this, "Recipes loaded", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load recipes: HTTP " + responseCode, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (Exception ignored) {}
                if (connection != null) connection.disconnect();
            }
        }).start();
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

