package com.campushub.model;

public class IdeaTechnology {

    private int ideaId;
    private String technology;

    // Empty constructor
    public IdeaTechnology() {
    }

    // Constructor for CREATE
    public IdeaTechnology(int ideaId, String technology) {
        this.ideaId = ideaId;
        this.technology = technology;
    }

    // Getters and Setters

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    @Override
    public String toString() {
        return "IdeaTechnology{" +
                "ideaId=" + ideaId +
                ", technology='" + technology + '\'' +
                '}';
    }
}