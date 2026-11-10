package com.campushub.servlet;

import com.campushub.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/mentor-dashboard")
public class MentorDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        // Check mentor role
        Object roleObject = session.getAttribute("role");

        if (roleObject == null) {
            roleObject = session.getAttribute("userRole");
        }

        String role = roleObject == null
                ? ""
                : roleObject.toString();

        if (!"MENTOR".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Only mentors can access this dashboard.");
            return;
        }

        Object idObject = session.getAttribute("userId");

        if (!(idObject instanceof Number)) {
            session.invalidate();
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        int mentorId = ((Number) idObject).intValue();

        try (Connection con = DBConnection.getConnection()) {

            // Verify active mentor account
            String mentorSql = """
                    SELECT name
                    FROM users
                    WHERE user_id = ?
                    AND role = 'MENTOR'
                    AND status = 'ACTIVE'
                    """;

            try (PreparedStatement ps = con.prepareStatement(mentorSql)) {

                ps.setInt(1, mentorId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        session.invalidate();

                        response.sendRedirect(
                                request.getContextPath() + "/login.jsp");
                        return;
                    }

                    request.setAttribute("mentorName",
                            rs.getString("name"));
                }
            }


            // 1. Ideas waiting for this mentor's review
            request.setAttribute("ideasToReview",
                    getCount(con, """
                            SELECT COUNT(*)
                            FROM ideas i
                            WHERE i.status IN ('PENDING', 'UNDER_REVIEW')
                            AND NOT EXISTS (
                                SELECT 1
                                FROM mentor_reviews mr
                                WHERE mr.idea_id = i.idea_id
                                AND mr.mentor_id = ?
                            )
                            """, mentorId));


            // 2. Ideas reviewed by this mentor
            request.setAttribute("ideasReviewed",
                    getCount(con, """
                            SELECT COUNT(*)
                            FROM mentor_reviews
                            WHERE mentor_id = ?
                            """, mentorId));


            // 3. Assigned projects
            request.setAttribute("assignedProjects",
                    getCount(con, """
                            SELECT COUNT(*)
                            FROM projects
                            WHERE mentor_id = ?
                            """, mentorId));


            // 4. Project reviews still due
            request.setAttribute("projectReviewsDue",
                    getCount(con, """
                            SELECT COUNT(*)
                            FROM projects p
                            WHERE p.mentor_id = ?
                            AND NOT EXISTS (
                                SELECT 1
                                FROM project_reviews pr
                                WHERE pr.project_id = p.project_id
                                AND pr.mentor_id = ?
                                AND pr.review_status = 'REVIEWED'
                            )
                            """, mentorId, mentorId));


            // Recent ideas: only short summary information
            request.setAttribute(
                    "recentIdeas",
                    getRecentIdeas(con, mentorId));


            // Recent projects: only short summary information
            request.setAttribute(
                    "recentProjects",
                    getRecentProjects(con, mentorId));


            // Show dashboard
            request.getRequestDispatcher("/mentor-dashboard.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Unable to load mentor dashboard.", e);
        }
    }

    // Get dashboard count
    private int getCount(Connection con,
                         String sql,
                         int... parameters)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {
                ps.setInt(i + 1, parameters[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Get recent ideas
    private List<Map<String, Object>> getRecentIdeas(
            Connection con, int mentorId)
            throws SQLException {

        List<Map<String, Object>> ideas = new ArrayList<>();

        String sql = """
                SELECT
                    i.idea_id,
                    i.title,
                    i.status,
                    i.submitted_at,
                    u.name AS student_name,
                    c.category_name

                FROM ideas i

                JOIN users u
                    ON i.student_id = u.user_id

                LEFT JOIN categories c
                    ON i.category_id = c.category_id

                WHERE i.status IN ('PENDING', 'UNDER_REVIEW')

                AND NOT EXISTS (
                    SELECT 1
                    FROM mentor_reviews mr
                    WHERE mr.idea_id = i.idea_id
                    AND mr.mentor_id = ?
                )

                ORDER BY i.submitted_at DESC, i.idea_id DESC
                LIMIT 3
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Map<String, Object> idea = new HashMap<>();

                    idea.put("ideaId", rs.getInt("idea_id"));
                    idea.put("title", rs.getString("title"));
                    idea.put("status", rs.getString("status"));
                    idea.put("studentName",
                            rs.getString("student_name"));
                    idea.put("category",
                            rs.getString("category_name"));
                    idea.put("submittedAt",
                            rs.getTimestamp("submitted_at"));

                    ideas.add(idea);
                }
            }
        }

        return ideas;
    }


    // Get recent assigned projects
    private List<Map<String, Object>> getRecentProjects(
            Connection con, int mentorId)
            throws SQLException {

        List<Map<String, Object>> projects = new ArrayList<>();

        String sql = """
                SELECT
                    project_id,
                    project_name,
                    status,
                    progress_percentage

                FROM projects

                WHERE mentor_id = ?

                ORDER BY created_at DESC, project_id DESC
                LIMIT 3
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> project = new HashMap<>();

                    project.put("projectId",
                            rs.getInt("project_id"));

                    project.put("projectName",
                            rs.getString("project_name"));

                    project.put("status",
                            rs.getString("status"));

                    int progress = rs.getInt("progress_percentage");
                    progress = Math.max(0, Math.min(100, progress));
                    project.put("progress", progress);
                    projects.add(project);
                }
            }
        }
        return projects;
    }
}