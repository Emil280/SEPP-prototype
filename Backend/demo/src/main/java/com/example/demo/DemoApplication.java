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

import java.util.*;

@SpringBootApplication
@RestController
public class DemoApplication {
	Request currentRequest;
	@Autowired
	ApplicationContextProvider applicationContextProvider;

	public static void main(String[] args) {
		ApplicationContext myContext = SpringApplication.run(DemoApplication.class, args);
	}

	public Optional<Item> getItemFromList(List<Item> itemList, int id){
		for (Item item : itemList){
			if (item.getId() == id){
				return Optional.of(item);
			}
		}
		return Optional.empty();
	}
	public Optional<FridgeItem> getFridgeItemFromList(List<FridgeItem> fridgeItemList, int id){
		for (FridgeItem fridgeItem : fridgeItemList){
			if (fridgeItem.getItemId() == id){
				return Optional.of(fridgeItem);
			}
		}
		return Optional.empty();
	}
	@RequestMapping("/hello")
	public String hello(){
		System.out.println("hello");
		return "Hello World!";
	}
	@RequestMapping("/findFilteredRecipes")
	public String findFilteredRecipes(@RequestBody Request request){
		System.out.println("filtered");
		currentRequest = request;
		RecipeService myRecipeService = applicationContextProvider.getApplicationContext().getBean(RecipeService.class);
		RecipeIngredientService myRecipeIngredientService = applicationContextProvider.getApplicationContext().getBean(RecipeIngredientService.class);
		FridgeItemService myFridgeItemService = applicationContextProvider.getApplicationContext().getBean(FridgeItemService.class);
		ItemService myItemService = applicationContextProvider.getApplicationContext().getBean(ItemService.class);
		List<Item> itemList = myItemService.getItems();
		List<FridgeItem> frigdeItemList = myFridgeItemService.getFridgeItemsByUserId(currentRequest.getUserId());
		List<Recipe> myValidRecipes = new ArrayList<>();
		for (Recipe recipe : myRecipeService.getRecipes()){
			//If the recipe name contains the search
			boolean found = true;
			if (recipe.getName().toUpperCase().contains(currentRequest.getSearch().toUpperCase()) && recipe.getTime() <= currentRequest.getTime()){
				List<RecipeIngredient> ingredientList = myRecipeIngredientService.getRecipeIngredientsById(recipe.getId());
				List <String> ingredientStrings = new ArrayList<>();
				for (RecipeIngredient recipeIngredient : ingredientList) {
					//first if statement checks if the ingredient is in the items table (it should be) if not dont allow recipe
					//second if statement checks if the recipe adheres to the users request of vegan veg or meat
					Optional<Item> item = getItemFromList(itemList, recipeIngredient.getItemId());
					if (item.isPresent()){
						ingredientStrings.add(item.get().getName());
						if (item.get().getType() < currentRequest.getRecipeType()){
							found = false;
                            break;
						}
					}else {
						found = false;
                        break;
					}
					//checks if the ingredient is in the users fridge, if not dont allow the recipe
					Optional<FridgeItem> myFridgeItem = getFridgeItemFromList(frigdeItemList, recipeIngredient.getItemId());
					if (myFridgeItem.isPresent()) {
						if (!(myFridgeItem.get().getQuantity() >= recipeIngredient.getQuantity() && myFridgeItem.get().getUserId() == currentRequest.getUserId())) {
							found = false;
							break;
						}
					} else {
						found = false;
						break;
					}
				}
				if (found){
					recipe.setIngredients(ingredientStrings);
					myValidRecipes.add(recipe);
				}


			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(myValidRecipes);
		return json;

	}

	@GetMapping("/randomRecipe")
	public String getRandomRecipe() {
		RecipeService recipeService = applicationContextProvider.getApplicationContext().getBean(RecipeService.class);
		List<Recipe> recipes = recipeService.getRecipes();

		if (recipes.isEmpty()) {
			return "{}";
		}

		Recipe randomRecipe = recipes.get(new Random().nextInt(recipes.size()));
		return new Gson().toJson(randomRecipe);
	}

	@GetMapping("/randomRecipes")
	public String getRandomRecipes() {
		RecipeService recipeService = applicationContextProvider.getApplicationContext().getBean(RecipeService.class);
		List<Recipe> recipes = recipeService.getRecipes();

		if (recipes.isEmpty()) {
			return "[]";
		}

		Collections.shuffle(recipes);
		List<Recipe> randomRecipes = recipes.subList(0, Math.min(10, recipes.size()));
		return new Gson().toJson(randomRecipes);
	}

}
