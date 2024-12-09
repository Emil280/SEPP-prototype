package com.example.demo;

import com.example.demo.FridgeItem.FridgeItem;
import com.example.demo.FridgeItem.FridgeItemService;
import com.example.demo.Recipe.Recipe;
import com.example.demo.Recipe.RecipeService;
import com.example.demo.RecipeIngredient.RecipeIngredient;
import com.example.demo.RecipeIngredient.RecipeIngredientService;
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
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@RequestMapping("/request")
	public void dealWithRequest(@RequestBody Request request){
		currentRequest = request;
	}
	@RequestMapping("/findFilteredRecipes")
	public List<String> findFilteredRecipes(){
		String[] args = {};
		ApplicationContext myContext = SpringApplication.run(DemoApplication.class, args);
		RecipeService myRecipeService = myContext.getBean(RecipeService.class);
		RecipeIngredientService myRecipeIngredientService = myContext.getBean(RecipeIngredientService.class);
		FridgeItemService myFridgeItemService = myContext.getBean((FridgeItemService.class));
		ArrayList<String> myRecipes = new ArrayList<>();
		for (Recipe recipe : myRecipeService.getRecipes()){
			if (recipe.getName().toUpperCase().contains(currentRequest.getSearch().toUpperCase())){
				List<RecipeIngredient> ingredientList = myRecipeIngredientService.getRecipeIngredientsById(recipe.getId());
				boolean ingredientsRequired = true;
				for (RecipeIngredient recipeIngredient : ingredientList){
					Optional<FridgeItem> myFridgeItem = myFridgeItemService.getFridgeItemsByIds(currentRequest.getUserId(), recipeIngredient.getItemId());
					if (myFridgeItem.isPresent()){
						if (!(myFridgeItem.get().getQuantity() >= recipeIngredient.getQuantity() && myFridgeItem.get().getUserId() == currentRequest.getUserId())){
							ingredientsRequired = false;
							break;
						}
					}else {
						ingredientsRequired = false;
						break;
					}
				}
				if (ingredientsRequired){
					myRecipes.add(recipe.toString());
				}
			}
		}
		return myRecipes;

	}
	@RequestMapping("/testString")
	public List<Recipe> testString(){
		String[] args = {};
		ApplicationContext myContext = SpringApplication.run(DemoApplication.class, args);
		RecipeService myRecipeService = myContext.getBean(RecipeService.class);
		RecipeIngredientService myRecipeIngredientService = myContext.getBean(RecipeIngredientService.class);
		FridgeItemService myFridgeItemService = myContext.getBean((FridgeItemService.class));
		return myRecipeService.getRecipes();
	}
}
