package org.bagirov.sixteenthhw.service;

import org.bagirov.sixteenthhw.model.Ingredient;
import org.bagirov.sixteenthhw.model.Recipe;
import org.bagirov.sixteenthhw.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public void addRecipe(String name, List<Ingredient> ingredients) {
        Recipe recipe = new Recipe(name, ingredients);
        repository.addRecipe(recipe);
        System.out.println("Рецепт добавлен!");
    }

    public void searchRecipes(String query) {
        List<Recipe> recipes = repository.searchRecipes(query);
        if (recipes.isEmpty()) {
            System.out.println("Рецепт не найден.");
        } else {
            for (Recipe recipe : recipes) {
                System.out.println("Рецепт: " + recipe.getName());
                for (Ingredient ingredient : recipe.getIngredients()) {
                    System.out.println(" - " + ingredient.getName() + ": " + ingredient.getAmount());
                }
            }
        }
    }

    public void deleteRecipe(String name) {
        repository.deleteRecipe(name);
        System.out.println("Рецепт удален.");
    }
}

