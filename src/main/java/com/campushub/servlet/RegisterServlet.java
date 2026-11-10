package com.campushub.servlet;

import com.campushub.dao.UserDAO;
import com.campushub.model.User;
import com.campushub.exception.DatabaseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.length() < 8) {

            request.setAttribute("errorMessage",
                    "Please enter valid details. Password must be at least 8 characters.");

            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
            return;
        }
        name = name.trim();
        email = email.trim();
        try {
            // Check whether the email already exists
            if (userDAO.getUserByEmail(email) != null) {
                request.setAttribute("errorMessage",
                        "This email is already registered.");

                request.getRequestDispatcher("/register.jsp")
                        .forward(request, response);
                return;
            }
            // Create the new user
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("STUDENT");
            user.setStatus("ACTIVE");

            userDAO.addUser(user);
            // Redirect to login after successful registration
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp?registered=success"
            );

        } catch (DatabaseException e) {
            throw new ServletException(
                    "Unable to register user.", e
            );
        }
    }
}