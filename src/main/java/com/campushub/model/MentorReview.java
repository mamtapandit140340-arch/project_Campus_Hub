package com.campushub.model;

import java.sql.Timestamp;

public class MentorReview {

    private int reviewId;
    private int ideaId;
    private int mentorId;
    private int rating;
    private String decision;
    private String feedback;
    private Timestamp reviewedAt;

    // Empty constructor
    public MentorReview() {
    }

    // Constructor for CREATE
    public MentorReview(int ideaId, int mentorId, int rating,
                        String decision, String feedback) {
        this.ideaId = ideaId;
        this.mentorId = mentorId;
        this.rating = rating;
        this.decision = decision;
        this.feedback = feedback;
    }

    // Getters and Setters

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Timestamp reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    @Override
    public String toString() {
        return "MentorReview{" +
                "reviewId=" + reviewId +
                ", ideaId=" + ideaId +
                ", mentorId=" + mentorId +
                ", rating=" + rating +
                ", decision='" + decision + '\'' +
                ", feedback='" + feedback + '\'' +
                ", reviewedAt=" + reviewedAt +
                '}';
    }
}
