
        package com.campushub;

import com.campushub.dao.IdeaTechnologyDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.IdeaTechnology;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class IdeaTechnologyDAOTest {

    public static void main(String[] args) {

        IdeaTechnologyDAO dao = new IdeaTechnologyDAO();

        int ideaId;

        try {

            // =====================================================
            // FIND AN EXISTING IDEA ID
            // =====================================================

            System.out.println("===== FIND EXISTING IDEA =====");

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("""
                         SELECT idea_id, title
                         FROM ideas
                         ORDER BY idea_id
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
                            "ERROR: No ideas exist in the database."
                    );

                    System.out.println(
                            "Create an idea first using IdeaDAOTest."
                    );

                    return;
                }
            }


            // =====================================================
            // CREATE
            // =====================================================

            System.out.println("\n===== CREATE =====");

            IdeaTechnology technology =
                    new IdeaTechnology(
                            ideaId,
                            "Java"
                    );

            boolean added =
                    dao.addTechnology(technology);

            System.out.println(
                    "Technology added: " + added
            );


            // =====================================================
            // READ ONE
            // =====================================================

            System.out.println("\n===== READ ONE =====");

            IdeaTechnology foundTechnology =
                    dao.getTechnology(
                            ideaId,
                            "Java"
                    );

            if (foundTechnology != null) {

                System.out.println(
                        "Idea ID: "
                                + foundTechnology.getIdeaId()
                );

                System.out.println(
                        "Technology: "
                                + foundTechnology.getTechnology()
                );

            } else {

                System.out.println(
                        "Technology not found."
                );
            }


            // =====================================================
            // READ ALL
            // =====================================================

            System.out.println("\n===== READ ALL =====");

            List<IdeaTechnology> technologies =
                    dao.getAllTechnologies();

            if (technologies.isEmpty()) {

                System.out.println(
                        "No technologies found."
                );

            } else {

                for (IdeaTechnology t : technologies) {

                    System.out.println(
                            "Idea ID: "
                                    + t.getIdeaId()
                                    + " | Technology: "
                                    + t.getTechnology()
                    );
                }
            }


            // =====================================================
            // UPDATE
            // =====================================================

            System.out.println("\n===== UPDATE =====");

            IdeaTechnology technologyToUpdate =
                    new IdeaTechnology(
                            ideaId,
                            "Spring Boot"
                    );

            boolean updateResult =
                    dao.updateTechnology(
                            technologyToUpdate,
                            "Java"
                    );

            System.out.println(
                    "Technology updated: "
                            + updateResult
            );


            // =====================================================
            // READ AFTER UPDATE
            // =====================================================

            System.out.println(
                    "\n===== READ AFTER UPDATE ====="
            );

            IdeaTechnology updatedTechnologyResult =
                    dao.getTechnology(
                            ideaId,
                            "Spring Boot"
                    );

            if (updatedTechnologyResult != null) {

                System.out.println(
                        "Idea ID: "
                                + updatedTechnologyResult.getIdeaId()
                );

                System.out.println(
                        "Technology: "
                                + updatedTechnologyResult.getTechnology()
                );

            } else {

                System.out.println(
                        "Updated technology not found."
                );
            }


            // =====================================================
            // DELETE
            // =====================================================

            System.out.println("\n===== DELETE =====");

            boolean deleted =
                    dao.deleteTechnology(
                            ideaId,
                            "Spring Boot"
                    );

            System.out.println(
                    "Technology deleted: " + deleted
            );


            // =====================================================
            // VERIFY DELETE
            // =====================================================

            System.out.println(
                    "\n===== VERIFY DELETE ====="
            );

            IdeaTechnology deletedTechnology =
                    dao.getTechnology(
                            ideaId,
                            "Spring Boot"
                    );

            if (deletedTechnology == null) {

                System.out.println(
                        "Technology successfully deleted."
                );

            } else {

                System.out.println(
                        "Technology still exists."
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

