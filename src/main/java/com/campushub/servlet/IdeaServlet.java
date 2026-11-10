package com.campushub.servlet;

import com.campushub.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ideas")
public class IdeaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        int userId;

        try {
            userId = Integer.parseInt(
                    session.getAttribute("userId").toString()
            );
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        List<Map<String, Object>> ideas = new ArrayList<>();

        String sql =
                "SELECT idea_id, title, problem_statement, " +
                        "proposed_solution, expected_outcome, status, " +
                        "category_id, submitted_at " +
                        "FROM ideas " +
                        "WHERE student_id = ? " +
                        "ORDER BY submitted_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Map<String, Object> idea = new HashMap<>();

                    idea.put(
                            "ideaId",
                            resultSet.getInt("idea_id")
                    );

                    idea.put(
                            "title",
                            resultSet.getString("title")
                    );

                    idea.put(
                            "problemStatement",
                            resultSet.getString("problem_statement")
                    );

                    idea.put(
                            "proposedSolution",
                            resultSet.getString("proposed_solution")
                    );

                    idea.put(
                            "expectedOutcome",
                            resultSet.getString("expected_outcome")
                    );

                    idea.put(
                            "status",
                            resultSet.getString("status")
                    );

                    idea.put(
                            "categoryId",
                            resultSet.getInt("category_id")
                    );

                    idea.put(
                            "submittedAt",
                            resultSet.getTimestamp("submitted_at")
                    );

                    ideas.add(idea);
                }
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Unable to load ideas from database.",
                    e
            );
        }

        // Send data to JSP
        request.setAttribute("ideas", ideas);

        request.setAttribute(
                "ideaCount",
                ideas.size()
        );

        request.setAttribute(
                "displayName",
                session.getAttribute("userName")
        );

        // Forward to JSP
        request.getRequestDispatcher(
                "/ideas.jsp"
        ).forward(request, response);
    }
}
