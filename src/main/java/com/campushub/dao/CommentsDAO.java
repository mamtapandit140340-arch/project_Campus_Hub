package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Comments;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addComment(Comments comment)
            throws DatabaseException {

        String sql = "INSERT INTO comments " +
                "(project_id, user_id, comment_text) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, comment.getProjectId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getCommentText());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add comment to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public Comments getCommentById(int commentId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM comments WHERE comment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, commentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractComment(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find comment with ID: "
                            + commentId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<Comments> getAllComments()
            throws DatabaseException {

        List<Comments> comments = new ArrayList<>();

        String sql =
                "SELECT * FROM comments ORDER BY comment_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comments.add(extractComment(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve comments from the database.",
                    e
            );
        }

        return comments;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateComment(Comments comment)
            throws DatabaseException {

        String sql =
                "UPDATE comments SET comment_text = ? " +
                        "WHERE comment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, comment.getCommentText());
            ps.setInt(2, comment.getCommentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update comment with ID: "
                            + comment.getCommentId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteComment(int commentId)
            throws DatabaseException {

        String sql =
                "DELETE FROM comments WHERE comment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, commentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete comment with ID: "
                            + commentId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private Comments extractComment(ResultSet rs)
            throws SQLException {

        Comments comment = new Comments();

        comment.setCommentId(
                rs.getInt("comment_id")
        );

        comment.setProjectId(
                rs.getInt("project_id")
        );

        comment.setUserId(
                rs.getInt("user_id")
        );

        comment.setCommentText(
                rs.getString("comment_text")
        );

        comment.setCreatedAt(
                rs.getTimestamp("created_at")
        );

        return comment;
    }
}