package com.campushub.model;

public class Category {

    private int id;
    private String catName;
    private String description;

    // Empty constructor
    public Category() {
    }

    // Constructor for CREATE
    public Category(String catName, String description) {
        this.catName = catName;
        this.description = description;
    }

    // Constructor for READ / UPDATE
    public Category(int id, String catName, String description) {
        this.id = id;
        this.catName = catName;
        this.description = description;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for catName
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}