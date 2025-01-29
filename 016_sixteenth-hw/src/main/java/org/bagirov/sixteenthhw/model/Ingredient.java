package org.bagirov.sixteenthhw.model;

public class Ingredient {
    private int id;
    private String name;
    private String amount;

    public Ingredient(int id, String name, String amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAmount() { return amount; }
}

