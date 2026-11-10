package com.campushub.servlet;

import com.campushub.exception.DatabaseException;
import com.campushub.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.campushub.service.CollaborationService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/collaboration-requests")
public class CollaborationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(session.getAttribute("userId").toString());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Map<String, Object>> requests = new ArrayList<>();
        String sql =
                "SELECT cr.request_id, cr.project_id, cr.sender_id, " +
                        "cr.receiver_id, cr.message, cr.status, cr.created_at, " +
                        "p.project_name, p.description AS project_description, " +
                        "sender.name AS sender_name, sender.email AS sender_email " +
                        "FROM collaboration_requests cr " +
                        "JOIN projects p ON cr.project_id = p.project_id " +
                        "JOIN users sender ON cr.sender_id = sender.user_id " +
                        "WHERE cr.receiver_id = ? " +
                        "ORDER BY CASE cr.status " +
                        "WHEN 'PENDING' THEN 1 " +
                        "WHEN 'ACCEPTED' THEN 2 " +
                        "WHEN 'REJECTED' THEN 3 ELSE 4 END, " +
                        "cr.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("requestId", rs.getInt("request_id"));
                    item.put("projectId", rs.getInt("project_id"));
                    item.put("senderId", rs.getInt("sender_id"));
                    item.put("projectName", rs.getString("project_name"));
                    item.put("projectDescription",
                            rs.getString("project_description"));
                    item.put("senderName", rs.getString("sender_name"));
                    item.put("senderEmail", rs.getString("sender_email"));
                    item.put("message", rs.getString("message"));
                    item.put("status", rs.getString("status"));
                    item.put("createdAt", rs.getTimestamp("created_at"));
                    requests.add(item);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load collaboration requests.", e);
        }

        request.setAttribute("requests", requests);
        request.setAttribute("displayName", session.getAttribute("userName"));

        request.getRequestDispatcher("/collaboration.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }
        int receiverId;
        int requestId;
        String action = request.getParameter("action");

        try {

            receiverId = Integer.parseInt(
                    session.getAttribute("userId").toString()
            );

            requestId = Integer.parseInt(
                    request.getParameter("requestId")
            );

        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user or request ID."
            );
            return;
        }

        if (!"accept".equals(action)
                && !"reject".equals(action)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid action."
            );
            return;
        }

        WebApplicationContext context =
                WebApplicationContextUtils
                        .getWebApplicationContext(
                                getServletContext()
                        );

        if (context == null) {

            throw new ServletException(
                    "Spring application context not initialized."
            );
        }

        CollaborationService service =
                context.getBean(CollaborationService.class);

        try {

            service.processRequest(
                    requestId,
                    receiverId,
                    action
            );

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage()
            );
            return;

        } catch (IllegalStateException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/collaboration-requests?result=alreadyProcessed"
            );
            return;

        } catch (DatabaseException e) {

            throw new ServletException(
                    "Unable to process the collaboration request.",
                    e
            );
        }

        String result =
                "accept".equals(action)
                        ? "accepted"
                        : "rejected";

        response.sendRedirect(
                request.getContextPath()
                        + "/collaboration-requests?result="
                        + result
        );
    }
}