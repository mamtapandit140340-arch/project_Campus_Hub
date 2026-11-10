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

@WebServlet("/idea-form")
public class IdeaFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Map<String, Object>> categories = new ArrayList<>();

        String sql =
                "SELECT category_id, category_name " +
                        "FROM categories ORDER BY category_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> category = new HashMap<>();
                category.put("categoryId", rs.getInt("category_id"));
                category.put("categoryName", rs.getString("category_name"));
                categories.add(category);
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load idea categories.", e);
        }

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/idea-form.jsp").forward(request, response);
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

        int studentId;

        try {
            studentId = Integer.parseInt(
                    session.getAttribute("userId").toString()
            );
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String categoryValue = request.getParameter("categoryId");
        String problem = request.getParameter("problemStatement");
        String solution = request.getParameter("proposedSolution");
        String outcome = request.getParameter("expectedOutcome");

        if (title == null || title.isBlank()
                || categoryValue == null || problem == null || problem.isBlank()
                || solution == null || solution.isBlank()
                || outcome == null || outcome.isBlank()) {

            response.sendRedirect(
                    request.getContextPath() + "/idea-form?error=required"
            );
            return;
        }

        int categoryId;

        try {
            categoryId = Integer.parseInt(categoryValue);
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/idea-form?error=category"
            );
            return;
        }

        String sql =
                "INSERT INTO ideas " +
                        "(student_id, category_id, title, problem_statement, " +
                        "proposed_solution, expected_outcome, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, categoryId);
            ps.setString(3, title.trim());
            ps.setString(4, problem.trim());
            ps.setString(5, solution.trim());
            ps.setString(6, outcome.trim());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Unable to save the idea.", e);
        }
        response.sendRedirect(
                request.getContextPath() + "/ideas?submitted=1"
        );
    }
}
