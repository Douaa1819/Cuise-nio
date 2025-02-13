package com.youcode.cuisenio.recipe.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "recipeIngredients")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    private String unit ;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    public RecipeIngredient() {}
    public RecipeIngredient(Long id, Double quantity, String unit) {
        this.id = id;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
