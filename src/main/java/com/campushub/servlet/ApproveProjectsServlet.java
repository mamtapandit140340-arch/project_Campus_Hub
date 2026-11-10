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

@WebServlet("/approve-projects")
public class ApproveProjectsServlet extends HttpServlet {

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
                    "Only mentors can access project approvals.");
            return;
        }

        int mentorId = Integer.parseInt(
                session.getAttribute("userId").toString());

        List<Map<String, Object>> projects = new ArrayList<>();

        String sql = """
            SELECT p.project_id, p.project_name, p.description,
                   p.status AS project_status, p.approval_status,
                   p.expected_end_date, p.progress_percentage,
                   i.idea_id, i.title AS idea_title,
                   u.name AS student_name,
                   c.category_name
            FROM projects p
            JOIN ideas i ON p.idea_id = i.idea_id
            JOIN users u ON i.student_id = u.user_id
            LEFT JOIN categories c ON p.category_id = c.category_id
            WHERE p.mentor_id = ?
              AND p.approval_status = 'PENDING'
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
                    item.put("expectedEndDate", rs.getDate("expected_end_date"));
                    item.put("progress", rs.getInt("progress_percentage"));
                    item.put("ideaId", rs.getInt("idea_id"));
                    item.put("ideaTitle", rs.getString("idea_title"));
                    item.put("studentName", rs.getString("student_name"));
                    item.put("categoryName", rs.getString("category_name"));

                    projects.add(item);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load project approvals.", e);
        }

        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/approve-projects.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"MENTOR".equals(session.getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Only mentors can approve projects.");
            return;
        }

        int mentorId;
        int projectId;

        try {
            mentorId = Integer.parseInt(
                    session.getAttribute("userId").toString());
            projectId = Integer.parseInt(request.getParameter("projectId"));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid project ID.");
            return;
        }

        String action = request.getParameter("action");
        String newApprovalStatus;

        if ("approve".equals(action)) {
            newApprovalStatus = "APPROVED";
        } else if ("reject".equals(action)) {
            newApprovalStatus = "REJECTED";
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid approval action.");
            return;
        }

        String sql = """
            UPDATE projects
            SET approval_status = ?
            WHERE project_id = ?
              AND mentor_id = ?
              AND approval_status = 'PENDING'
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newApprovalStatus);
            ps.setInt(2, projectId);
            ps.setInt(3, mentorId);

            if (ps.executeUpdate() != 1) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/approve-projects?result=notUpdated");
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Unable to update project approval.", e);
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/approve-projects?result=" + newApprovalStatus.toLowerCase());
    }
}
