package com.campushub.model;

public class Idea {

    private int ideaId;
    private int studentId;
    private int categoryId;
    private String title;
    private String problemStatement;
    private String proposedSolution;
    private String expectedOutcome;
    private String status;

    // Empty constructor
    public Idea() {
    }

    // Constructor for CREATE
    public Idea(int studentId, int categoryId, String title,
                String problemStatement, String proposedSolution,
                String expectedOutcome, String status) {

        this.studentId = studentId;
        this.categoryId = categoryId;
        this.title = title;
        this.problemStatement = problemStatement;
        this.proposedSolution = proposedSolution;
        this.expectedOutcome = expectedOutcome;
        this.status = status;
    }

    // Constructor for READ / UPDATE
    public Idea(int ideaId, int studentId, int categoryId, String title,
                String problemStatement, String proposedSolution,
                String expectedOutcome, String status) {

        this.ideaId = ideaId;
        this.studentId = studentId;
        this.categoryId = categoryId;
        this.title = title;
        this.problemStatement = problemStatement;
        this.proposedSolution = proposedSolution;
        this.expectedOutcome = expectedOutcome;
        this.status = status;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getProposedSolution() {
        return proposedSolution;
    }

    public void setProposedSolution(String proposedSolution) {
        this.proposedSolution = proposedSolution;
    }

    public String getExpectedOutcome() {
        return expectedOutcome;
    }

    public void setExpectedOutcome(String expectedOutcome) {
        this.expectedOutcome = expectedOutcome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}