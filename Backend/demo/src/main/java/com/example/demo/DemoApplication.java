package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@RequestMapping("/request")
	public void dealWithRequest(@RequestBody Request request){
		Recipe[] listOfRecipes = findFilteredRecipes(request);

	}
	public void findFilteredRecipes(Request request){
		String[] args = {};
		ApplicationContext myContext = SpringApplication.run(DemoApplication.class, args);
		RecipeService myRecipeService = myContext.getBean(RecipeService.class);

	}
}
