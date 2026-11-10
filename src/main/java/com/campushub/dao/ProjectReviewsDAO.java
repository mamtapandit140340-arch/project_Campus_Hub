package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.ProjectReviews;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectReviewsDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addProjectReview(ProjectReviews review)
            throws DatabaseException {

        String sql = """
                INSERT INTO project_reviews
                (project_id, mentor_id, rating, feedback,
                 review_status, reviewed_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getProjectId());
            ps.setInt(2, review.getMentorId());

            if (review.getRating() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, review.getRating());
            }

            ps.setString(4, review.getFeedback());
            ps.setString(5, review.getReviewStatus());

            if (review.getReviewedAt() == null) {
                ps.setNull(6, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(6, review.getReviewedAt());
            }

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        review.setReviewId(rs.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add project review to the database.",
                    e
            );
        }

        return false;
    }


    // =========================
    // READ ONE
    // =========================

    public ProjectReviews getProjectReviewById(int reviewId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM project_reviews WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reviewId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractProjectReview(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find project review with ID: "
                            + reviewId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<ProjectReviews> getAllProjectReviews()
            throws DatabaseException {

        List<ProjectReviews> reviews =
                new ArrayList<>();

        String sql =
                "SELECT * FROM project_reviews ORDER BY review_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reviews.add(extractProjectReview(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve project reviews.",
                    e
            );
        }

        return reviews;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateProjectReview(ProjectReviews review)
            throws DatabaseException {

        String sql = """
                UPDATE project_reviews
                SET project_id = ?,
                    mentor_id = ?,
                    rating = ?,
                    feedback = ?,
                    review_status = ?,
                    reviewed_at = ?
                WHERE review_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, review.getProjectId());
            ps.setInt(2, review.getMentorId());

            if (review.getRating() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, review.getRating());
            }

            ps.setString(4, review.getFeedback());
            ps.setString(5, review.getReviewStatus());

            if (review.getReviewedAt() == null) {
                ps.setNull(6, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(6, review.getReviewedAt());
            }

            ps.setInt(7, review.getReviewId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update project review with ID: "
                            + review.getReviewId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteProjectReview(int reviewId)
            throws DatabaseException {

        String sql =
                "DELETE FROM project_reviews WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reviewId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete project review with ID: "
                            + reviewId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private ProjectReviews extractProjectReview(ResultSet rs)
            throws SQLException {

        ProjectReviews review = new ProjectReviews();

        review.setReviewId(
                rs.getInt("review_id")
        );

        review.setProjectId(
                rs.getInt("project_id")
        );

        review.setMentorId(
                rs.getInt("mentor_id")
        );

        int rating = rs.getInt("rating");

        if (rs.wasNull()) {
            review.setRating(null);
        } else {
            review.setRating(rating);
        }

        review.setFeedback(
                rs.getString("feedback")
        );

        review.setReviewStatus(
                rs.getString("review_status")
        );

        review.setReviewedAt(
                rs.getTimestamp("reviewed_at")
        );

        return review;
    }
}