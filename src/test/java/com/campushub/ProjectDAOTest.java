package com.campushub;

import com.campushub.dao.ProjectDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Project;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.List;

public class ProjectDAOTest {

    public static void main(String[] args) {

        ProjectDAO dao = new ProjectDAO();

        int ideaId;
        int mentorId;

        try {

            // =====================================================
            // FIND IDEA WITHOUT AN EXISTING PROJECT
            // =====================================================

            System.out.println("===== FIND AVAILABLE IDEA =====");

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("""
                         SELECT i.idea_id, i.title
                         FROM ideas i
                         LEFT JOIN projects p
                             ON i.idea_id = p.idea_id
                         WHERE p.idea_id IS NULL
                         ORDER BY i.idea_id
                         LIMIT 1
                         """);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ideaId = rs.getInt("idea_id");

                    System.out.println(
                            "Idea ID: " + ideaId
                    );

                    System.out.println(
                            "Idea Title: "
                                    + rs.getString("title")
                    );

                } else {

                    System.out.println(
                            "NO AVAILABLE IDEA FOUND."
                    );

                    System.out.println(
                            "Every existing idea already has a project."
                    );

                    return;
                }
            }


            // =====================================================
            // FIND ACTIVE MENTOR WITH MENTOR PROFILE
            // =====================================================

            System.out.println(
                    "\n===== FIND AVAILABLE MENTOR ====="
            );

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("""
                         SELECT mp.mentor_id, u.name
                         FROM mentor_profiles mp
                         INNER JOIN users u
                             ON mp.mentor_id = u.user_id
                         WHERE u.role = 'MENTOR'
                           AND u.status = 'ACTIVE'
                         ORDER BY mp.mentor_id
                         LIMIT 1
                         """);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    mentorId = rs.getInt("mentor_id");

                    System.out.println(
                            "Mentor ID: " + mentorId
                    );

                    System.out.println(
                            "Mentor Name: "
                                    + rs.getString("name")
                    );

                } else {

                    System.out.println(
                            "NO ACTIVE MENTOR PROFILE FOUND."
                    );

                    System.out.println(
                            "Create a mentor profile first."
                    );

                    return;
                }
            }


            // =====================================================
            // CREATE
            // =====================================================

            System.out.println("\n===== CREATE =====");

            Project project =
                    new Project(
                            ideaId,
                            mentorId,
                            "Smart Campus Project",
                            "Centralized campus resource management system.",
                            Date.valueOf("2026-09-01"),
                            Date.valueOf("2026-12-01"),
                            "PLANNING",
                            0
                    );

            boolean added =
                    dao.addProject(project);

            System.out.println(
                    "Project added: " + added
            );

            System.out.println(
                    "Generated Project ID: "
                            + project.getProjectId()
            );


            // =====================================================
            // READ ONE
            // =====================================================

            System.out.println("\n===== READ ONE =====");

            Project foundProject =
                    dao.getProjectById(
                            project.getProjectId()
                    );

            if (foundProject != null) {

                System.out.println(
                        "Project found:"
                );

                System.out.println(
                        foundProject
                );

            } else {

                System.out.println(
                        "Project NOT found."
                );
            }


            // =====================================================
            // READ ALL
            // =====================================================

            System.out.println("\n===== READ ALL =====");

            List<Project> projects =
                    dao.getAllProjects();

            if (projects.isEmpty()) {

                System.out.println(
                        "No projects found."
                );

            } else {

                for (Project p : projects) {
                    System.out.println(p);
                }
            }


            // =====================================================
            // UPDATE
            // =====================================================

            System.out.println("\n===== UPDATE =====");

            project.setProjectName(
                    "Smart Campus Project Updated"
            );

            project.setDescription(
                    "Updated centralized campus management system."
            );

            project.setStatus(
                    "IN_PROGRESS"
            );

            project.setProgressPercentage(
                    25
            );

            boolean updated =
                    dao.updateProject(project);

            System.out.println(
                    "Project updated: " + updated
            );


            // =====================================================
            // READ AFTER UPDATE
            // =====================================================

            System.out.println(
                    "\n===== READ AFTER UPDATE ====="
            );

            Project updatedProject =
                    dao.getProjectById(
                            project.getProjectId()
                    );

            if (updatedProject != null) {

                System.out.println(
                        updatedProject
                );

            } else {

                System.out.println(
                        "Updated project NOT found."
                );
            }


            // =====================================================
            // DELETE
            // =====================================================

            System.out.println("\n===== DELETE =====");

            boolean deleted =
                    dao.deleteProject(
                            project.getProjectId()
                    );

            System.out.println(
                    "Project deleted: " + deleted
            );


            // =====================================================
            // VERIFY DELETE
            // =====================================================

            System.out.println(
                    "\n===== VERIFY DELETE ====="
            );

            Project deletedProject =
                    dao.getProjectById(
                            project.getProjectId()
                    );

            if (deletedProject == null) {

                System.out.println(
                        "Project successfully deleted."
                );

            } else {

                System.out.println(
                        "Project still exists."
                );
            }


        } catch (DatabaseException e) {

            System.out.println(
                    "\n===== DATABASE ERROR ====="
            );

            System.out.println(
                    "Message: " + e.getMessage()
            );

            if (e.getCause() != null) {

                System.out.println(
                        "Cause: "
                                + e.getCause().getMessage()
                );
            }

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                    "\n===== ERROR ====="
            );

            System.out.println(
                    "Message: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}