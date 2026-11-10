package com.campushub.servlet;

import com.campushub.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/my-projects")
public class MyProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"MENTOR".equals(session.getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Only mentors can access this page.");
            return;
        }

        int mentorId;

        try {
            mentorId = Integer.parseInt(
                    session.getAttribute("userId").toString());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Map<String, Object>> projects = new ArrayList<>();

        String sql = """
            SELECT p.project_id, p.project_name, p.description,
                   p.status AS project_status, p.approval_status,
                   p.start_date, p.expected_end_date,
                   p.progress_percentage, p.created_at,
                   i.title AS idea_title,
                   c.category_name
            FROM projects p
            JOIN ideas i ON p.idea_id = i.idea_id
            LEFT JOIN categories c ON p.category_id = c.category_id
            WHERE p.mentor_id = ?
            ORDER BY p.created_at DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("projectId", rs.getInt("project_id"));
                    item.put("projectName", rs.getString("project_name"));
                    item.put("description", rs.getString("description"));
                    item.put("projectStatus", rs.getString("project_status"));
                    item.put("approvalStatus", rs.getString("approval_status"));
                    item.put("startDate", rs.getDate("start_date"));
                    item.put("expectedEndDate",
                            rs.getDate("expected_end_date"));
                    item.put("progress", rs.getInt("progress_percentage"));
                    item.put("createdAt", rs.getTimestamp("created_at"));
                    item.put("ideaTitle", rs.getString("idea_title"));
                    item.put("categoryName", rs.getString("category_name"));

                    projects.add(item);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load your projects.", e);
        }
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/my-projects.jsp")
                .forward(request, response);
    }
}