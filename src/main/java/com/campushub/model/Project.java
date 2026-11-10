 package com.campushub.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Project {

    private int projectId;
    private int ideaId;
    private int mentorId;
    private String projectName;
    private String description;
    private Date startDate;
    private Date expectedEndDate;
    private String status;
    private int progressPercentage;
    private Timestamp createdAt;

    // Default constructor
    public Project() {
    }

    // Constructor for adding a project
    public Project(int ideaId, int mentorId, String projectName,
                   String description, Date startDate,
                   Date expectedEndDate, String status,
                   int progressPercentage) {

        this.ideaId = ideaId;
        this.mentorId = mentorId;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.status = status;
        this.progressPercentage = progressPercentage;
    }

    // Getters and Setters

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", ideaId=" + ideaId +
                ", mentorId=" + mentorId +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", expectedEndDate=" + expectedEndDate +
                ", status='" + status + '\'' +
                ", progressPercentage=" + progressPercentage +
                ", createdAt=" + createdAt +
                '}';
    }
}
