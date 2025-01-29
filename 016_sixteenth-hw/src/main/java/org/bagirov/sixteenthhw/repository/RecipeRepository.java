package org.bagirov.sixteenthhw.repository;

import org.bagirov.sixteenthhw.model.Ingredient;
import org.bagirov.sixteenthhw.model.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Recipe> recipeMapper;

    public RecipeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.recipeMapper = (rs, rowNum) -> {
            int recipeId = rs.getInt("id");
            String recipeName = rs.getString("name");

            List<Ingredient> ingredients = jdbcTemplate.query(
                    "SELECT * FROM ingredients WHERE recipe_id = ?",
                    (rs1, rowNum1) -> new Ingredient(
                            rs1.getInt("id"),
                            rs1.getString("name"),
                            rs1.getString("amount")
                    ),
                    recipeId
            );

            return new Recipe(recipeId, recipeName, ingredients);
        };
    }

    public void addRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes (name) VALUES (?) RETURNING id";
        int recipeId = jdbcTemplate.queryForObject(sql, Integer.class, recipe.getName());

        for (Ingredient ingredient : recipe.getIngredients()) {
            jdbcTemplate.update(
                    "INSERT INTO ingredients (recipe_id, name, amount) VALUES (?, ?, ?)",
                    recipeId, ingredient.getName(), ingredient.getAmount()
            );
        }
    }

    public List<Recipe> searchRecipes(String query) {
        String sql = "SELECT * FROM recipes WHERE name ILIKE ?";
        return jdbcTemplate.query(sql, recipeMapper, "%" + query + "%");
    }

    public void deleteRecipe(String name) {
        jdbcTemplate.update("DELETE FROM recipes WHERE name = ?", name);
    }
}
