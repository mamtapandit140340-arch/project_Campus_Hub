
        package com.campushub;

import com.campushub.dao.IdeaDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Idea;

import java.util.List;

public class IdeaDAOTest {

    public static void main(String[] args) {

        IdeaDAO dao = new IdeaDAO();

        try {

            // =====================================================
            // CREATE
            // =====================================================

            System.out.println("===== CREATE =====");

            Idea idea = new Idea(
                    5,                                      // Student ID
                    1,                                      // Category ID
                    "Smart Campus App",                     // Title
                    "Students face difficulty finding campus resources.",
                    "Create a centralized smart campus application.",
                    "Students can easily access campus resources.",
                    "PENDING"
            );

            System.out.println("Student ID: "
                    + idea.getStudentId());

            System.out.println("Category ID: "
                    + idea.getCategoryId());

            System.out.println("Title: "
                    + idea.getTitle());

            boolean added = dao.addIdea(idea);

            System.out.println("Idea added: " + added);


            // =====================================================
            // READ ALL
            // =====================================================

            System.out.println("\n===== READ ALL =====");

            List<Idea> ideas = dao.getAllIdeas();

            if (ideas.isEmpty()) {

                System.out.println("No ideas found.");

            } else {

                for (Idea i : ideas) {

                    System.out.println(
                            "ID: " + i.getIdeaId()
                                    + " | Student ID: " + i.getStudentId()
                                    + " | Category ID: " + i.getCategoryId()
                                    + " | Title: " + i.getTitle()
                                    + " | Status: " + i.getStatus()
                    );
                }
            }


            // =====================================================
            // GET CREATED IDEA ID
            // =====================================================

            if (ideas.isEmpty()) {
                System.out.println("No idea available for further testing.");
                return;
            }

            Idea lastIdea =
                    ideas.get(ideas.size() - 1);

            int ideaId =
                    lastIdea.getIdeaId();

            System.out.println(
                    "\nCreated Idea ID: " + ideaId
            );


            // =====================================================
            // READ ONE
            // =====================================================

            System.out.println("\n===== READ ONE =====");

            Idea foundIdea =
                    dao.getIdeaById(ideaId);

            if (foundIdea != null) {

                System.out.println(
                        "Idea ID: "
                                + foundIdea.getIdeaId()
                );

                System.out.println(
                        "Student ID: "
                                + foundIdea.getStudentId()
                );

                System.out.println(
                        "Category ID: "
                                + foundIdea.getCategoryId()
                );

                System.out.println(
                        "Title: "
                                + foundIdea.getTitle()
                );

                System.out.println(
                        "Problem Statement: "
                                + foundIdea.getProblemStatement()
                );

                System.out.println(
                        "Proposed Solution: "
                                + foundIdea.getProposedSolution()
                );

                System.out.println(
                        "Expected Outcome: "
                                + foundIdea.getExpectedOutcome()
                );

                System.out.println(
                        "Status: "
                                + foundIdea.getStatus()
                );

            } else {

                System.out.println("Idea not found.");
            }


            // =====================================================
            // UPDATE
            // =====================================================

            System.out.println("\n===== UPDATE =====");

            Idea updatedIdea = new Idea(
                    ideaId,
                    5,
                    1,
                    "Smart Campus App Updated",
                    "Students need an easier way to find campus resources.",
                    "Develop a centralized smart campus application.",
                    "Students can quickly access campus resources.",
                    "APPROVED"
            );

            boolean updated =
                    dao.updateIdea(updatedIdea);

            System.out.println(
                    "Idea updated: " + updated
            );


            // =====================================================
            // READ AFTER UPDATE
            // =====================================================

            System.out.println("\n===== READ AFTER UPDATE =====");

            Idea checkUpdate =
                    dao.getIdeaById(ideaId);

            if (checkUpdate != null) {

                System.out.println(
                        "Idea ID: "
                                + checkUpdate.getIdeaId()
                );

                System.out.println(
                        "Title: "
                                + checkUpdate.getTitle()
                );

                System.out.println(
                        "Status: "
                                + checkUpdate.getStatus()
                );
            }


            // =====================================================
            // DELETE
            // =====================================================

            System.out.println("\n===== DELETE =====");

            boolean deleted =
                    dao.deleteIdea(ideaId);

            System.out.println(
                    "Idea deleted: " + deleted
            );


            // =====================================================
            // VERIFY DELETE
            // =====================================================

            System.out.println("\n===== VERIFY DELETE =====");

            Idea deletedIdea =
                    dao.getIdeaById(ideaId);

            if (deletedIdea == null) {

                System.out.println(
                        "Idea successfully deleted."
                );

            } else {

                System.out.println(
                        "Idea still exists."
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
                        "Cause: " + e.getCause().getMessage()
                );
            }

            e.printStackTrace();
        }
    }
}

