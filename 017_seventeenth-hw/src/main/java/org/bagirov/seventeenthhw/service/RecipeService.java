package org.bagirov.seventeenthhw.service;

import org.springframework.transaction.annotation.Transactional;
import org.bagirov.seventeenthhw.model.Ingredient;
import org.bagirov.seventeenthhw.model.Recipe;
import org.bagirov.seventeenthhw.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public Recipe addRecipe(String name, List<Ingredient> ingredients) {
        Recipe recipe = new Recipe(name);
        for (Ingredient ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        return recipeRepository.save(recipe);
    }

    public List<Recipe> searchRecipes(String namePart) {
        return recipeRepository.searchByName(namePart);
    }

    @Transactional
    public boolean deleteRecipe(String name) {
        Optional<Recipe> recipe = recipeRepository.findByName(name);


        if(recipe.isPresent()) {
            recipeRepository.delete(recipe.get());
            return true;
        } else {
            return false;
        }
    }
}
