package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewIdeasDAO {

    public List<Map<String, Object>> getIdeas(
            String search,
            String category,
            String status) throws DatabaseException {

        List<Map<String, Object>> ideas = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT i.idea_id, i.title, i.problem_statement,
                       i.proposed_solution, i.expected_outcome,
                       i.status, i.submitted_at,
                       u.name AS student_name,
                       c.category_name
                FROM ideas i
                JOIN users u ON i.student_id = u.user_id
                JOIN categories c ON i.category_id = c.category_id
                WHERE 1 = 1
                """);

        List<Object> parameters = new ArrayList<>();

        if (search != null && !search.isBlank()) {
            sql.append("""
                    AND (i.title LIKE ? OR u.name LIKE ?)
                    """);

            String keyword = "%" + search.trim() + "%";
            parameters.add(keyword);
            parameters.add(keyword);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" AND c.category_name = ?");
            parameters.add(category);
        }

        if (status != null && !status.isBlank()) {
            sql.append(" AND i.status = ?");
            parameters.add(status);
        }

        sql.append("""
                ORDER BY
                    CASE i.status
                        WHEN 'PENDING' THEN 1
                        WHEN 'UNDER_REVIEW' THEN 2
                        WHEN 'NEEDS_IMPROVEMENT' THEN 3
                        WHEN 'APPROVED' THEN 4
                        WHEN 'REJECTED' THEN 5
                        ELSE 6
                    END,
                    i.submitted_at DESC
                """);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> idea = new LinkedHashMap<>();

                    idea.put("ideaId", rs.getInt("idea_id"));
                    idea.put("title", rs.getString("title"));
                    idea.put("problemStatement",
                            rs.getString("problem_statement"));
                    idea.put("proposedSolution",
                            rs.getString("proposed_solution"));
                    idea.put("expectedOutcome",
                            rs.getString("expected_outcome"));
                    idea.put("status", rs.getString("status"));
                    idea.put("submittedAt",
                            rs.getTimestamp("submitted_at"));
                    idea.put("studentName",
                            rs.getString("student_name"));
                    idea.put("categoryName",
                            rs.getString("category_name"));

                    ideas.add(idea);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to retrieve student ideas.", e);
        }

        return ideas;
    }

    public Map<String, Integer> getStatusCounts()
            throws DatabaseException {

        Map<String, Integer> counts = new LinkedHashMap<>();

        String sql = """
                SELECT status, COUNT(*) AS total
                FROM ideas
                GROUP BY status
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                counts.put(rs.getString("status"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to retrieve idea status counts.", e);
        }

        return counts;
    }

    public List<Map<String, Object>> getCategories()
            throws DatabaseException {

        List<Map<String, Object>> categories = new ArrayList<>();

        String sql = """
                SELECT category_id, category_name
                FROM categories
                ORDER BY category_name
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> category = new LinkedHashMap<>();

                category.put("categoryId", rs.getInt("category_id"));
                category.put("categoryName", rs.getString("category_name"));

                categories.add(category);
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to retrieve idea categories.", e);
        }

        return categories;
    }
}