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
    public static List<Recipe> loadRecipes(String apiUrl, String filterJson) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            byte[] postData = filterJson.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(postData.length);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to fetch recipes: HTTP " + responseCode);
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            Gson gson = new Gson();
            TypeToken<List<Recipe>> token = new TypeToken<List<Recipe>>() {};
            return gson.fromJson(responseBuilder.toString(), token.getType());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
            if (connection != null) connection.disconnect();
        }
    }

}

