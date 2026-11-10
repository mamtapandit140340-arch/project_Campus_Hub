package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Idea;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdeaDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addIdea(Idea idea)
            throws DatabaseException {

        String sql = """
                INSERT INTO ideas
                (student_id, category_id, title, problem_statement,
                 proposed_solution, expected_outcome, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idea.getStudentId());
            ps.setInt(2, idea.getCategoryId());
            ps.setString(3, idea.getTitle());
            ps.setString(4, idea.getProblemStatement());
            ps.setString(5, idea.getProposedSolution());
            ps.setString(6, idea.getExpectedOutcome());
            ps.setString(7, idea.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add idea to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public Idea getIdeaById(int ideaId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM ideas WHERE idea_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractIdea(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find idea with ID: "
                            + ideaId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<Idea> getAllIdeas()
            throws DatabaseException {

        List<Idea> ideas = new ArrayList<>();

        String sql =
                "SELECT * FROM ideas ORDER BY idea_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ideas.add(extractIdea(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve ideas from the database.",
                    e
            );
        }

        return ideas;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateIdea(Idea idea)
            throws DatabaseException {

        String sql = """
                UPDATE ideas SET
                student_id = ?,
                category_id = ?,
                title = ?,
                problem_statement = ?,
                proposed_solution = ?,
                expected_outcome = ?,
                status = ?
                WHERE idea_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idea.getStudentId());
            ps.setInt(2, idea.getCategoryId());
            ps.setString(3, idea.getTitle());
            ps.setString(4, idea.getProblemStatement());
            ps.setString(5, idea.getProposedSolution());
            ps.setString(6, idea.getExpectedOutcome());
            ps.setString(7, idea.getStatus());
            ps.setInt(8, idea.getIdeaId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update idea with ID: "
                            + idea.getIdeaId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteIdea(int ideaId)
            throws DatabaseException {

        String sql =
                "DELETE FROM ideas WHERE idea_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ideaId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete idea with ID: "
                            + ideaId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private Idea extractIdea(ResultSet rs)
            throws SQLException {

        Idea idea = new Idea();

        idea.setIdeaId(
                rs.getInt("idea_id")
        );

        idea.setStudentId(
                rs.getInt("student_id")
        );

        idea.setCategoryId(
                rs.getInt("category_id")
        );

        idea.setTitle(
                rs.getString("title")
        );

        idea.setProblemStatement(
                rs.getString("problem_statement")
        );

        idea.setProposedSolution(
                rs.getString("proposed_solution")
        );

        idea.setExpectedOutcome(
                rs.getString("expected_outcome")
        );

        idea.setStatus(
                rs.getString("status")
        );

        return idea;
    }
}