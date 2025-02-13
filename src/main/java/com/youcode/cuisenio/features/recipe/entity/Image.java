package com.youcode.cuisenio.features.recipe.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String title;
    private String contentType;
    private Date uploadDate;

    @ManyToMany(mappedBy = "images")
    private List<Recipe> recipes;

    public Image() {}

    public Image(Long id, String url, String title, String contentType, Date uploadDate, List<Recipe> recipes) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.contentType = contentType;
        this.uploadDate = uploadDate;
        this.recipes = recipes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
