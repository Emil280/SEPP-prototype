package com.example.myapplication;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RecipeUtils {

    // Method to fetch and parse recipes from the server
    public static List<Recipe> loadRecipes(String apiUrl, String filterJson) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Create URL object
            URL url = new URL(apiUrl);

            // Open connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set headers
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Write the filter JSON to the request body
            byte[] postData = filterJson.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(postData.length);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
            }

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to fetch recipes: HTTP " + responseCode);
            }

            // Read the response
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            // Parse the JSON response into a list of Recipe objects
            Gson gson = new Gson();
            TypeToken<List<Recipe>> token = new TypeToken<List<Recipe>>() {};
            return gson.fromJson(responseBuilder.toString(), token.getType());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close resources
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            if (connection != null) connection.disconnect();
        }
    }

}

