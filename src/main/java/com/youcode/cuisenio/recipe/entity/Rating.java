package com.youcode.cuisenio.recipe.entity;

import com.youcode.cuisenio.auth.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stars;
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    public Rating(Long id, String review, int stars, User user, Recipe recipe) {
        this.id = id;
        this.review = review;
        this.stars = stars;
        this.user = user;
        this.recipe = recipe;
    }

    public Rating() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
