package org.bagirov.sixteenthhw.model;

import java.util.List;


public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;

    public Recipe(int id, String name, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
}
