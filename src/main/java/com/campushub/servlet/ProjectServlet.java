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
import java.util.*;     //map,hashmap,linked arraylist...

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        List<Map<String, Object>> projects = new ArrayList<>();
        String sql =
                "SELECT " +
                        "p.project_id, " +
                        "p.project_name, " +
                        "p.description, " +
                        "p.status, " +
                        "p.progress_percentage, " +
                        "p.start_date, " +
                        "p.expected_end_date, " +
                        "p.category_id, " +
                        "c.category_name " +
                        "FROM projects p " +
                        "LEFT JOIN categories c " +
                        "ON p.category_id = c.category_id " +
                        "ORDER BY p.project_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Map<String, Object> project = new HashMap<>();

                project.put("project_id", resultSet.getInt("project_id"));
                project.put("project_name", resultSet.getString("project_name"));
                project.put("description", resultSet.getString("description"));
                project.put("status", resultSet.getString("status"));
                project.put("progress_percentage", resultSet.getInt("progress_percentage"));
                project.put("start_date", resultSet.getDate("start_date"));
                project.put("expected_end_date", resultSet.getDate("expected_end_date"));
                project.put("category_id", resultSet.getInt("category_id"));
                project.put("category_name",resultSet.getString("category_name"));
                projects.add(project);
            }

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to load projects from database.",e
            );
        }

        request.setAttribute("projects", projects);
        request.setAttribute(
                "projectCount",
                projects.size()
        );

        request.setAttribute(
                "displayName",
                session.getAttribute("userName")
        );

        request.getRequestDispatcher(
                "/projects.jsp"
        ).forward(request, response);
    }
}