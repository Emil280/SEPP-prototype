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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@RequestMapping("/findFilteredRecipes")
	public String findFilteredRecipes(@RequestBody Request request){
		currentRequest = request;
		System.out.println(currentRequest.toString());
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
				for (RecipeIngredient recipeIngredient : ingredientList) {
					//first if statement checks if the ingredient is in the items table (it should be) if not dont allow recipe
					//second if statement checks if the recipe adheres to the users request of vegan veg or meat
					Optional<Item> item = getItemFromList(itemList, recipeIngredient.getItemId());
					if (item.isPresent()){
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
					myValidRecipes.add(recipe);
				}


			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(myValidRecipes);
		System.out.println(json);
		return json;

	}


}
