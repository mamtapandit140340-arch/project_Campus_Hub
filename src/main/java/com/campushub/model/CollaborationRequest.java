package com.campushub.model;

import java.sql.Timestamp;

public class CollaborationRequest {

    private int requestId;
    private int projectId;
    private int senderId;
    private int receiverId;
    private String message;
    private String status;
    private Timestamp createdAt;

    // Empty constructor
    public CollaborationRequest() {
    }

    // Constructor for CREATE
    public CollaborationRequest(int projectId, int senderId,
                                int receiverId, String message,
                                String status) {
        this.projectId = projectId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CollaborationRequest{" +
                "requestId=" + requestId +
                ", projectId=" + projectId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}