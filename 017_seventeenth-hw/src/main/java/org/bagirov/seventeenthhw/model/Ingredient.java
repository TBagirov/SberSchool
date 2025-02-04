package org.bagirov.seventeenthhw.model;


import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}

