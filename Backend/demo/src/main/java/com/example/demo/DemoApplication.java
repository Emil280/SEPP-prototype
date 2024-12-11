package com.example.demo;

import com.example.demo.ApplicationContext.ApplicationContextProvider;
import com.example.demo.FridgeItem.FridgeItem;
import com.example.demo.FridgeItem.FridgeItemService;
import com.example.demo.Item.Item;
import com.example.demo.Item.ItemService;
import com.example.demo.Recipe.Recipe;
import com.example.demo.Recipe.RecipeService;
import com.example.demo.RecipeIngredient.RecipeIngredient;
import com.example.demo.RecipeIngredient.RecipeIngredientService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	private ApplicationContextProvider applicationContextProvider;

	private Request currentRequest;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private Optional<Item> getItemFromList(List<Item> itemList, int id) {
		return itemList.stream().filter(item -> item.getId() == id).findFirst();
	}

	private Optional<FridgeItem> getFridgeItemFromList(List<FridgeItem> fridgeItemList, int id) {
		return fridgeItemList.stream().filter(fridgeItem -> fridgeItem.getItemId() == id).findFirst();
	}

	@RequestMapping("/hello")
	public String hello(){
		System.out.println("hello");
		return "Hello World!";
	}
	@RequestMapping("/findFilteredRecipes")
	public String findFilteredRecipes(@RequestBody Request request) {
		currentRequest = request;
		RecipeService recipeService = applicationContextProvider.getApplicationContext().getBean(RecipeService.class);
		RecipeIngredientService recipeIngredientService = applicationContextProvider.getApplicationContext().getBean(RecipeIngredientService.class);
		FridgeItemService fridgeItemService = applicationContextProvider.getApplicationContext().getBean(FridgeItemService.class);
		ItemService itemService = applicationContextProvider.getApplicationContext().getBean(ItemService.class);

		List<Item> itemList = itemService.getItems();
		List<FridgeItem> fridgeItemList = fridgeItemService.getFridgeItemsByUserId(request.getUserId());
		List<Recipe> validRecipes = new ArrayList<>();

		for (Recipe recipe : recipeService.getRecipes()) {
			if (isValidRecipe(recipe, itemList, fridgeItemList)) {
				validRecipes.add(recipe);
			}
		}

		return new Gson().toJson(validRecipes);
	}

	private boolean isValidRecipe(Recipe recipe, List<Item> itemList, List<FridgeItem> fridgeItemList) {
		if (!recipe.getName().toUpperCase().contains(currentRequest.getSearch().toUpperCase()) || recipe.getTime() > currentRequest.getTime()) {
			return false;
		}

		RecipeIngredientService recipeIngredientService = applicationContextProvider.getApplicationContext().getBean(RecipeIngredientService.class);
		List<RecipeIngredient> ingredients = recipeIngredientService.getRecipeIngredientsById(recipe.getId());

		for (RecipeIngredient ingredient : ingredients) {
			Optional<Item> item = getItemFromList(itemList, ingredient.getItemId());
			if (item.isEmpty() || item.get().getType() < currentRequest.getRecipeType()) {
				return false;
			}

			Optional<FridgeItem> fridgeItem = getFridgeItemFromList(fridgeItemList, ingredient.getItemId());
			if (fridgeItem.isEmpty() || fridgeItem.get().getQuantity() < ingredient.getQuantity() || fridgeItem.get().getUserId() != currentRequest.getUserId()) {
				return false;
			}
		}

		return true;
	}
}
