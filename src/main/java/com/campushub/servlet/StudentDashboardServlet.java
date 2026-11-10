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
import java.util.*;  //ArrayList,hashmap,list,map

@WebServlet("/student-dashboard")
public class StudentDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);


        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() +
                            "/login.jsp"
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
                    request.getContextPath() +
                            "/login.jsp"
            );

            return;
        }


        try (Connection connection =
                     DBConnection.getConnection()) {


            /*
             * ==============================
             * 1. MY PROJECTS
             * ==============================
             *
             * project_members tells us which
             * projects the student belongs to.
             */

            int myProjectsCount = 0;

            String projectCountSQL =
                    "SELECT COUNT(DISTINCT project_id) " +
                            "FROM project_members " +
                            "WHERE user_id = ?";


            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 projectCountSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        myProjectsCount =
                                resultSet.getInt(1);

                    }
                }
            }


            /*
             * ==============================
             * 2. MY IDEAS
             * ==============================
             */

            int myIdeasCount = 0;

            String ideaCountSQL =
                    "SELECT COUNT(*) " +
                            "FROM ideas " +
                            "WHERE student_id = ?";


            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 ideaCountSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        myIdeasCount =
                                resultSet.getInt(1);

                    }
                }
            }


            /*
             * ==============================
             * 3. RECENT PROJECTS
             * ==============================
             */

            List<Map<String, Object>> recentProjects =
                    new ArrayList<>();


            String recentProjectsSQL =
                    "SELECT " +
                            "p.project_id, " +
                            "p.project_name, " +
                            "p.status, " +
                            "c.category_name " +
                            "FROM projects p " +
                            "INNER JOIN project_members pm " +
                            "ON p.project_id = pm.project_id " +
                            "LEFT JOIN categories c " +
                            "ON p.category_id = c.category_id " +
                            "WHERE pm.user_id = ? " +
                            "ORDER BY p.project_id DESC " +
                            "LIMIT 5";


            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 recentProjectsSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    while (resultSet.next()) {

                        Map<String, Object> project =
                                new HashMap<>();


                        project.put(
                                "project_id",
                                resultSet.getInt(
                                        "project_id"
                                )
                        );


                        project.put(
                                "project_name",
                                resultSet.getString(
                                        "project_name"
                                )
                        );


                        project.put(
                                "status",
                                resultSet.getString(
                                        "status"
                                )
                        );


                        project.put(
                                "category_name",
                                resultSet.getString(
                                        "category_name"
                                )
                        );


                        recentProjects.add(project);
                    }
                }
            }


            /*
             * ==============================
             * 4. RECENT IDEAS
             * ==============================
             */

            List<Map<String, Object>> recentIdeas =
                    new ArrayList<>();


            String recentIdeasSQL =
                    "SELECT " +
                            "i.idea_id, " +
                            "i.title, " +
                            "i.status, " +
                            "i.submitted_at, " +
                            "c.category_name " +
                            "FROM ideas i " +
                            "LEFT JOIN categories c " +
                            "ON i.category_id = c.category_id " +
                            "WHERE i.student_id = ? " +
                            "ORDER BY i.submitted_at DESC " +
                            "LIMIT 5";


            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 recentIdeasSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    while (resultSet.next()) {

                        Map<String, Object> idea =
                                new HashMap<>();


                        idea.put(
                                "idea_id",
                                resultSet.getInt(
                                        "idea_id"
                                )
                        );


                        idea.put(
                                "title",
                                resultSet.getString(
                                        "title"
                                )
                        );


                        idea.put(
                                "status",
                                resultSet.getString(
                                        "status"
                                )
                        );


                        idea.put(
                                "submitted_at",
                                resultSet.getTimestamp(
                                        "submitted_at"
                                )
                        );


                        idea.put(
                                "category_name",
                                resultSet.getString(
                                        "category_name"
                                )
                        );


                        recentIdeas.add(idea);
                    }
                }
            }


            /*
             * ==============================
             * 5. SEND DATA TO JSP
             * ==============================
             */

            request.setAttribute(
                    "myProjectsCount",
                    myProjectsCount
            );


            request.setAttribute(
                    "myIdeasCount",
                    myIdeasCount
            );


            request.setAttribute(
                    "recentProjects",
                    recentProjects
            );


            request.setAttribute(
                    "recentIdeas",
                    recentIdeas
            );


            request.setAttribute(
                    "displayName",
                    session.getAttribute("userName")
            );


            /*
             * Collaboration counts are added
             * after confirming the exact
             * collaboration_requests columns.
             */

            request.setAttribute(
                    "acceptedCollaborationsCount",
                    0
            );


            request.setAttribute(
                    "pendingRequestsCount",
                    0
            );


            request.getRequestDispatcher(
                    "/student-dashboard.jsp"
            ).forward(request, response);


        } catch (SQLException e) {

            throw new ServletException(
                    "Unable to load dashboard data.",
                    e
            );
        }
    }
}