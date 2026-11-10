package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.MentorReview;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorReviewDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addMentorReview(MentorReview review)
            throws DatabaseException {

        String sql = """
                INSERT INTO mentor_reviews
                (idea_id, mentor_id, rating, decision, feedback)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getIdeaId());
            ps.setInt(2, review.getMentorId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getDecision());
            ps.setString(5, review.getFeedback());

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
                    "Unable to add mentor review to the database.",
                    e
            );
        }

        return false;
    }


    // =========================
    // READ BY ID
    // =========================

    public MentorReview getMentorReviewById(int reviewId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM mentor_reviews WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reviewId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractMentorReview(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find mentor review with ID: "
                            + reviewId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<MentorReview> getAllMentorReviews()
            throws DatabaseException {

        List<MentorReview> reviews = new ArrayList<>();

        String sql =
                "SELECT * FROM mentor_reviews ORDER BY review_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reviews.add(extractMentorReview(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve mentor reviews from the database.",
                    e
            );
        }

        return reviews;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateMentorReview(MentorReview review)
            throws DatabaseException {

        String sql = """
                UPDATE mentor_reviews
                SET idea_id = ?,
                    mentor_id = ?,
                    rating = ?,
                    decision = ?,
                    feedback = ?
                WHERE review_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, review.getIdeaId());
            ps.setInt(2, review.getMentorId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getDecision());
            ps.setString(5, review.getFeedback());
            ps.setInt(6, review.getReviewId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update mentor review with ID: "
                            + review.getReviewId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteMentorReview(int reviewId)
            throws DatabaseException {

        String sql =
                "DELETE FROM mentor_reviews WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reviewId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete mentor review with ID: "
                            + reviewId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private MentorReview extractMentorReview(ResultSet rs)
            throws SQLException {

        MentorReview review = new MentorReview();

        review.setReviewId(
                rs.getInt("review_id")
        );

        review.setIdeaId(
                rs.getInt("idea_id")
        );

        review.setMentorId(
                rs.getInt("mentor_id")
        );

        review.setRating(
                rs.getInt("rating")
        );

        review.setDecision(
                rs.getString("decision")
        );

        review.setFeedback(
                rs.getString("feedback")
        );

        review.setReviewedAt(
                rs.getTimestamp("reviewed_at")
        );

        return review;
    }
}