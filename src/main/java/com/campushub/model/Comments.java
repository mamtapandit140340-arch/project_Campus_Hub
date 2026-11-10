package com.campushub.model;

import java.sql.Timestamp;

public class Comments {

    private int commentId;
    private int projectId;
    private int userId;
    private String commentText;
    private Timestamp createdAt;

    // Empty constructor
    public Comments() {
    }

    // Constructor for CREATE
    public Comments(int projectId, int userId, String commentText) {
        this.projectId = projectId;
        this.userId = userId;
        this.commentText = commentText;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", commentText='" + commentText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}