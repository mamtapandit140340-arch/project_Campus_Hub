package com.campushub.servlet;

import com.campushub.dao.UserDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String selectedRole = request.getParameter("role");

        // Check input fields
        if (email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()
                || selectedRole == null || selectedRole.trim().isEmpty()) {

            request.setAttribute("errorMessage",
                    "Please enter email, password and account type.");
            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        email = email.trim();
        selectedRole = selectedRole.trim().toUpperCase();

        // Allow only Student and Mentor
        if (!selectedRole.equals("STUDENT")
                && !selectedRole.equals("MENTOR")) {

            request.setAttribute("errorMessage",
                    "Please select a valid account type.");

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        try {

            // Find user using email
            User user = userDAO.getUserByEmail(email);

            // Check email, password and account status
            if (user != null
                    && user.getPassword() != null
                    && user.getPassword().equals(password)
                    && "ACTIVE".equalsIgnoreCase(user.getStatus())
                    && selectedRole.equalsIgnoreCase(user.getRole())) {

                // Invalidate old session if it exists
                HttpSession oldSession = request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                // Create new session
                HttpSession session = request.getSession(true);

                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("role", selectedRole);
                session.setAttribute("userRole", selectedRole);

                // Redirect according to selected role
                if (selectedRole.equals("STUDENT")) {
                    response.sendRedirect(
                            request.getContextPath() + "/student-dashboard");

                } else if (selectedRole.equals("MENTOR")) {

                    response.sendRedirect(
                            request.getContextPath() + "/mentor-dashboard");
                }

            } else {
                request.setAttribute("errorMessage",
                        "Invalid email, password, account type, or inactive account.");

                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
            }
        } catch (DatabaseException e) {
            throw new ServletException(
                    "Login failed due to a database error.", e);
        }
    }
}