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
import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/project-form")
public class ProjectFormServlet extends HttpServlet {

    // Display project creation form
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = getLoggedInUserId(request, response);

        if (userId == null) {
            return;
        }

        loadFormData(request, userId);

        request.getRequestDispatcher("/project-form.jsp")
                .forward(request, response);
    }


    // Save new project
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer userId = getLoggedInUserId(request, response);

        if (userId == null) {
            return;
        }

        // Load dropdown values again in case validation fails
        loadFormData(request, userId);

        // Get form values
        String projectName = trim(request.getParameter("projectName"));
        String ideaValue = trim(request.getParameter("ideaId"));
        String mentorValue = trim(request.getParameter("mentorId"));
        String description = trim(request.getParameter("description"));
        String startDateValue = trim(request.getParameter("startDate"));
        String endDateValue = trim(request.getParameter("expectedEndDate"));


        // Validate project name
        if (projectName.isEmpty() || projectName.length() > 200) {

            showError(request, response,
                    "Enter a project name within 200 characters.");

            return;
        }


        // Validate idea ID
        int ideaId;

        try {

            ideaId = Integer.parseInt(ideaValue);

            if (ideaId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            showError(request, response,
                    "Please select a valid approved idea.");

            return;
        }


        // Validate mentor ID
        int mentorId;

        try {

            mentorId = Integer.parseInt(mentorValue);

            if (mentorId <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            showError(request, response,
                    "Please select a valid mentor.");

            return;
        }


        // Read optional dates
        Date startDate;
        Date expectedEndDate;

        try {

            startDate = startDateValue.isEmpty()
                    ? null : Date.valueOf(startDateValue);

            expectedEndDate = endDateValue.isEmpty()
                    ? null : Date.valueOf(endDateValue);

        } catch (IllegalArgumentException e) {

            showError(request, response,
                    "Please enter valid project dates.");

            return;
        }


        // Validate project dates
        if (startDate != null &&
                expectedEndDate != null &&
                expectedEndDate.before(startDate)) {

            showError(request, response,
                    "Expected end date cannot be before the start date.");

            return;
        }


        try (Connection connection = DBConnection.getConnection()) {

            // Check logged-in student
            if (!isActiveStudent(connection, userId)) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Only active student accounts can create projects."
                );

                return;
            }


            // Check that the idea belongs to this student,
            // is approved and has not already been converted into a project.
            if (!isAvailableIdea(connection, ideaId, userId)) {

                showError(request, response,
                        "Select one of your approved ideas that has not already been used for a project.");

                return;
            }


            // Check selected mentor
            if (!isActiveMentor(connection, mentorId)) {

                showError(request, response,
                        "Please select an active mentor.");

                return;
            }


            boolean originalAutoCommit = connection.getAutoCommit();

            connection.setAutoCommit(false);

            try {

                int projectId;

                // Insert project
                String projectSql =
                        "INSERT INTO projects " +
                                "(idea_id, mentor_id, project_name, description, " +
                                "start_date, expected_end_date) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = connection.prepareStatement(
                        projectSql,
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, ideaId);
                    ps.setInt(2, mentorId);
                    ps.setString(3, projectName);

                    if (description.isEmpty()) {
                        ps.setNull(4, java.sql.Types.VARCHAR);
                    } else {
                        ps.setString(4, description);
                    }

                    if (startDate == null) {
                        ps.setNull(5, java.sql.Types.DATE);
                    } else {
                        ps.setDate(5, startDate);
                    }

                    if (expectedEndDate == null) {
                        ps.setNull(6, java.sql.Types.DATE);
                    } else {
                        ps.setDate(6, expectedEndDate);
                    }

                    ps.executeUpdate();

                    // Get generated project ID
                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "Unable to get project ID."
                            );
                        }

                        projectId = rs.getInt(1);
                    }
                }


                // Add project creator as team leader
                String memberSql =
                        "INSERT INTO project_members " +
                                "(project_id, user_id, member_role) " +
                                "VALUES (?, ?, 'LEADER')";

                try (PreparedStatement ps =
                             connection.prepareStatement(memberSql)) {

                    ps.setInt(1, projectId);
                    ps.setInt(2, userId);

                    ps.executeUpdate();
                }


                // Save both project and membership
                connection.commit();

            } catch (SQLException e) {

                // Undo changes if any insertion fails
                connection.rollback();

                throw e;

            } finally {

                connection.setAutoCommit(originalAutoCommit);
            }

        } catch (SQLException e) {

            getServletContext().log(
                    "Error creating project for student ID: " + userId,
                    e
            );

            throw new ServletException(
                    "Unable to create the project. Please try again.",
                    e
            );
        }


        // Redirect after successful creation
        response.sendRedirect(
                request.getContextPath() + "/projects?created=1"
        );
    }


    // Get logged-in user ID
    private Integer getLoggedInUserId(HttpServletRequest request,
                                      HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return null;
        }

        try {

            return Integer.parseInt(
                    String.valueOf(session.getAttribute("userId"))
            );

        } catch (NumberFormatException e) {

            session.invalidate();

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return null;
        }
    }


    // Check active student
    private boolean isActiveStudent(Connection connection, int userId)
            throws SQLException {

        String sql =
                "SELECT user_id FROM users " +
                        "WHERE user_id = ? " +
                        "AND role = 'STUDENT' " +
                        "AND status = 'ACTIVE'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    // Check whether an idea is available for project creation
    private boolean isAvailableIdea(Connection connection,
                                    int ideaId,
                                    int userId)
            throws SQLException {

        String sql =
                "SELECT i.idea_id " +
                        "FROM ideas i " +
                        "LEFT JOIN projects p ON i.idea_id = p.idea_id " +
                        "WHERE i.idea_id = ? " +
                        "AND i.student_id = ? " +
                        "AND i.status = 'APPROVED' " +
                        "AND p.project_id IS NULL";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, ideaId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    // Check selected mentor
    private boolean isActiveMentor(Connection connection, int mentorId)
            throws SQLException {

        String sql =
                "SELECT user_id FROM users " +
                        "WHERE user_id = ? " +
                        "AND role = 'MENTOR' " +
                        "AND status = 'ACTIVE'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    // Load approved ideas and available mentors
    private void loadFormData(HttpServletRequest request, int userId)
            throws ServletException {

        List<Map<String, Object>> availableIdeas = new ArrayList<>();

        List<Map<String, Object>> mentors = new ArrayList<>();

        String ideaSql =
                "SELECT i.idea_id, i.title " +
                        "FROM ideas i " +
                        "LEFT JOIN projects p ON i.idea_id = p.idea_id " +
                        "WHERE i.student_id = ? " +
                        "AND i.status = 'APPROVED' " +
                        "AND p.project_id IS NULL " +
                        "ORDER BY i.submitted_at DESC";

        String mentorSql =
                "SELECT u.user_id, u.name, mp.specialization " +
                        "FROM users u " +
                        "LEFT JOIN mentor_profiles mp " +
                        "ON u.user_id = mp.mentor_id " +
                        "WHERE u.role = 'MENTOR' " +
                        "AND u.status = 'ACTIVE' " +
                        "ORDER BY u.name";


        try (Connection connection = DBConnection.getConnection()) {
            // Load student's available approved ideas
            try (PreparedStatement ps =
                         connection.prepareStatement(ideaSql)) {
                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Map<String, Object> idea = new LinkedHashMap<>();
                        idea.put("idea_id", rs.getInt("idea_id"));
                        idea.put("title", rs.getString("title"));
                        availableIdeas.add(idea);
                    }
                }
            }

            // Load active mentors
            try (PreparedStatement ps =
                         connection.prepareStatement(mentorSql)) {

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        Map<String, Object> mentor = new LinkedHashMap<>();

                        mentor.put("user_id",
                                rs.getInt("user_id"));

                        mentor.put("name",
                                rs.getString("name"));

                        mentor.put("specialization",
                                rs.getString("specialization"));

                        mentors.add(mentor);
                    }
                }
            }

        } catch (SQLException e) {

            getServletContext().log(
                    "Unable to load project creation form data.", e
            );

            throw new ServletException(
                    "Unable to load approved ideas and mentors.", e
            );
        }

        request.setAttribute("availableIdeas", availableIdeas);
        request.setAttribute("mentors", mentors);
    }

    // Display form error
    private void showError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message)
            throws ServletException, IOException {

        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/project-form.jsp")
                .forward(request, response);
    }
    // Handle null form values
    private String trim(String value) {

        return value == null ? "" : value.trim();
    }
}
