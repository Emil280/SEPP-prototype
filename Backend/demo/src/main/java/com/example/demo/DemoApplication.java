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
	@RequestMapping("/findFilteredRecipes")
	public String findFilteredRecipes(@RequestBody Request request){
		currentRequest = request;
		System.out.println(currentRequest.toString());
		RecipeService myRecipeService = applicationContextProvider.getApplicationContext().getBean(RecipeService.class);
		RecipeIngredientService myRecipeIngredientService = applicationContextProvider.getApplicationContext().getBean(RecipeIngredientService.class);
		FridgeItemService myFridgeItemService = applicationContextProvider.getApplicationContext().getBean(FridgeItemService.class);
		ItemService myItemService = applicationContextProvider.getApplicationContext().getBean(ItemService.class);
		List<Item> itemList = myItemService.getItems();
		List<Recipe> myValidRecipes = new ArrayList<>();
		System.out.println("Created context");
		for (Recipe recipe : myRecipeService.getRecipes()){
			//If the recipe name contains the search
			System.out.println("in for loop");
			if (recipe.getName().toUpperCase().contains(currentRequest.getSearch().toUpperCase())){
				System.out.println("recipe is in search");
				List<RecipeIngredient> ingredientList = myRecipeIngredientService.getRecipeIngredientsById(recipe.getId());
				System.out.println("obtained ingredients");
				for (RecipeIngredient recipeIngredient : ingredientList) {
					//first if statement checks if the ingredient is in the items table (it should be) if not dont allow recipe
					//second if statement checks if the recipe adheres to the users request of vegan veg or meat
					Optional<Item> item = getItemFromList(itemList, recipeIngredient.getItemId());
					System.out.println("got optional item");
					if (item.isPresent()){
						System.out.println("item is present in database");
						if (item.get().getType() < currentRequest.getRecipeType()){
                            break;
						}
					}else {
                        break;
					}
					//checks if the ingredient is in the users fridge, if not dont allow the recipe
					System.out.println("Attempting to get fridge item");
					Optional<FridgeItem> myFridgeItem = myFridgeItemService.getFridgeItemsByIds(currentRequest.getUserId(), recipeIngredient.getItemId());
					System.out.println("Got fridge item");
					if (myFridgeItem.isPresent()) {
						if (!(myFridgeItem.get().getQuantity() >= recipeIngredient.getQuantity() && myFridgeItem.get().getUserId() == currentRequest.getUserId())) {
							break;
						}
					} else {
						break;
					}
				}
				myValidRecipes.add(recipe);


			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(myValidRecipes);
		System.out.println(json);
		return json;

	}


}
