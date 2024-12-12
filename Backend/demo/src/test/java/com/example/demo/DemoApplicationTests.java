//package com.example.demo;
//
//import com.example.demo.FridgeItem.FridgeItem;
//import com.example.demo.FridgeItem.FridgeItemService;
//import com.example.demo.Item.Item;
//import com.example.demo.Item.ItemService;
//import com.example.demo.Recipe.Recipe;
//import com.example.demo.Recipe.RecipeService;
//import com.example.demo.RecipeIngredient.RecipeIngredient;
//import com.example.demo.RecipeIngredient.RecipeIngredientService;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//class DemoApplicationTests {
//
//	private MockMvc mockMvc;
//
//	@Mock
//	private RecipeService recipeService;
//
//	@Mock
//	private RecipeIngredientService recipeIngredientService;
//
//	@Mock
//	private FridgeItemService fridgeItemService;
//
//	@Mock
//	private ItemService itemService;
//
//	@InjectMocks
//	private DemoApplication demoApplication;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(demoApplication).build();
//	}
//
//	@Test
//	void testFindFilteredRecipes() throws Exception {
//		// Mock data
//		Request request = new Request();
//		request.setUserId(1);
//		request.setSearch("Pasta");
//		request.setTime(30);
//		request.setRecipeType(2);
//
//		Recipe recipe = new Recipe(1, "Pasta Recipe", 20);
//		List<Recipe> recipes = Arrays.asList(recipe);
//
//		RecipeIngredient recipeIngredient = new RecipeIngredient(1, 1, 2);
//		List<RecipeIngredient> recipeIngredients = Arrays.asList(recipeIngredient);
//
//		Item item = new Item(1, "Tomato", 2);
//		List<Item> items = Arrays.asList(item);
//
//		FridgeItem fridgeItem = new FridgeItem(1, 1, 5);
//		List<FridgeItem> fridgeItems = Arrays.asList(fridgeItem);
//
//		// Mocking services
//		when(recipeService.getRecipes()).thenReturn(recipes);
//		when(recipeIngredientService.getRecipeIngredientsById(1)).thenReturn(recipeIngredients);
//		when(itemService.getItems()).thenReturn(items);
//		when(fridgeItemService.getFridgeItemsByUserId(1)).thenReturn(fridgeItems);
//
//		Gson gson = new Gson();
//		String requestJson = gson.toJson(request);
//
//		// Perform POST request
//		mockMvc.perform(post("/findFilteredRecipes")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(requestJson))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("Pasta Recipe")));
//
//		// Verify interactions
//		verify(recipeService, times(1)).getRecipes();
//		verify(recipeIngredientService, times(1)).getRecipeIngredientsById(1);
//		verify(itemService, times(1)).getItems();
//		verify(fridgeItemService, times(1)).getFridgeItemsByUserId(1);
//	}
//
//	@Test
//	void testGetRandomRecipe() throws Exception {
//		// Mock data
//		Recipe recipe = new Recipe(1, "Random Recipe", 15);
//		when(recipeService.getRecipes()).thenReturn(Collections.singletonList(recipe));
//
//		// Perform GET request
//		mockMvc.perform(get("/randomRecipe"))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("Random Recipe")));
//
//		// Verify interactions
//		verify(recipeService, times(1)).getRecipes();
//	}
//
//	@Test
//	void testGetRandomRecipes() throws Exception {
//		// Mock data
//		Recipe recipe1 = new Recipe(1, "Recipe 1", 10);
//		Recipe recipe2 = new Recipe(2, "Recipe 2", 20);
//		Recipe recipe3 = new Recipe(3, "Recipe 3", 30);
//		List<Recipe> recipes = Arrays.asList(recipe1, recipe2, recipe3);
//		when(recipeService.getRecipes()).thenReturn(recipes);
//
//		// Perform GET request
//		mockMvc.perform(get("/randomRecipes"))
//				.andExpect(status().isOk())
//				.andExpect(content().string(containsString("Recipe")))
//				.andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(10))));
//
//		// Verify interactions
//		verify(recipeService, times(1)).getRecipes();
//	}
//
//	@Test
//	void contextLoads() {
//	}
//}
