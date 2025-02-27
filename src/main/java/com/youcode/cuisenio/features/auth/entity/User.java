package com.youcode.cuisenio.features.auth.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.youcode.cuisenio.features.mealplan.entity.MealPlanner;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeComment;
import com.youcode.cuisenio.features.recipe.entity.RecipeRating;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String lastName;
    @Column(nullable = false)
    LocalDateTime registrationDate;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecipeRating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<RecipeComment> comments = new ArrayList<>();

        private boolean isBlocked;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MealPlanner> mealPlans = new ArrayList<>();


    public User() {}

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setActive(boolean isBlocked) {
        isBlocked = true;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User(Long id, boolean isActive, List<MealPlanner> mealPlanners, List<RecipeRating> ratings, List<RecipeComment> comments, List<Recipe> recipes, Role role, String email, String password, LocalDateTime registrationDate, String lastName, String username) {
        this.id = id;
        this.isBlocked = true;
        this.mealPlans = mealPlanners;
        this.ratings = ratings;
        this.comments = comments;
        this.recipes = recipes;
        this.role = role;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.lastName = lastName;
        this.username = username;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<MealPlanner> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(List<MealPlanner> mealPlanners) {
        this.mealPlans = mealPlanners;
    }

    public List<RecipeComment> getComments() {
        return comments;
    }

    public void setComments(List<RecipeComment> comments) {
        this.comments = comments;
    }


    public List<RecipeRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<RecipeRating> ratings) {
        this.ratings = ratings;
    }
}
