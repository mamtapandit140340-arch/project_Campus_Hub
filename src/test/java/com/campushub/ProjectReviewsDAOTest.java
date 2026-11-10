
        package com.campushub;

import com.campushub.dao.ProjectReviewsDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.ProjectReviews;

import java.util.List;

public class ProjectReviewsDAOTest {

    public static void main(String[] args) {

        ProjectReviewsDAO dao = new ProjectReviewsDAO();

        // =====================================================
        // USE EXISTING IDs FROM YOUR DATABASE
        // =====================================================

        int projectId = 1;
        int mentorId = 3;

        try {

            // =====================================================
            // CREATE
            // =====================================================

            System.out.println("===== CHECK EXISTING REVIEW =====");

            ProjectReviews existing =
                    findExistingReview(dao, projectId, mentorId);

            if (existing != null) {

                System.out.println("Review already exists.");
                System.out.println(existing);

                System.out.println();
                System.out.println(
                        "Delete the existing review or use another project/mentor."
                );

                return;
            }

            System.out.println("No existing review found.");
            System.out.println();

            System.out.println("===== CREATE =====");

            ProjectReviews review = new ProjectReviews(
                    projectId,
                    mentorId,
                    5,
                    "Excellent project progress and implementation.",
                    "REVIEWED"
            );

            /*
             * Database constraint requires:
             *
             * REVIEWED =
             * rating NOT NULL
             * reviewed_at NOT NULL
             *
             * Therefore we must set reviewedAt.
             */

            review.setReviewedAt(
                    new java.sql.Timestamp(System.currentTimeMillis())
            );

            boolean added = dao.addProjectReview(review);

            System.out.println("Project review added: " + added);

            if (!added) {
                System.out.println("Project review was not added.");
                return;
            }

            System.out.println("Generated Review ID: "
                    + review.getReviewId());

            System.out.println();
            System.out.println("Created Review:");
            System.out.println(review);


            // =====================================================
            // READ BY ID
            // =====================================================

            System.out.println();
            System.out.println("===== READ BY ID =====");

            ProjectReviews found =
                    dao.getProjectReviewById(review.getReviewId());

            if (found != null) {
                System.out.println("Project review found:");
                System.out.println(found);
            } else {
                System.out.println("Project review not found.");
            }


            // =====================================================
            // READ ALL
            // =====================================================

            System.out.println();
            System.out.println("===== READ ALL =====");

            List<ProjectReviews> reviews =
                    dao.getAllProjectReviews();

            if (reviews.isEmpty()) {

                System.out.println("No project reviews found.");

            } else {

                for (ProjectReviews r : reviews) {
                    System.out.println(r);
                }
            }


            // =====================================================
            // UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== UPDATE =====");

            found.setRating(4);

            found.setFeedback(
                    "Good project. Some improvements are recommended."
            );

            found.setReviewStatus("REVIEWED");

            found.setReviewedAt(
                    new java.sql.Timestamp(
                            System.currentTimeMillis()
                    )
            );

            boolean updated =
                    dao.updateProjectReview(found);

            System.out.println("Project review updated: " + updated);

            if (updated) {

                ProjectReviews updatedReview =
                        dao.getProjectReviewById(
                                found.getReviewId()
                        );

                System.out.println("Updated Review:");
                System.out.println(updatedReview);
            }


            // =====================================================
            // DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== DELETE =====");

            boolean deleted =
                    dao.deleteProjectReview(
                            review.getReviewId()
                    );

            System.out.println("Project review deleted: " + deleted);


            // =====================================================
            // VERIFY DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== VERIFY DELETE =====");

            ProjectReviews deletedReview =
                    dao.getProjectReviewById(
                            review.getReviewId()
                    );

            if (deletedReview == null) {
                System.out.println("Project review successfully deleted.");
            } else {
                System.out.println("Project review still exists.");
            }


        } catch (DatabaseException e) {

            System.out.println();
            System.out.println("===== DATABASE ERROR =====");

            System.out.println("Message: " + e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Cause: " + e.getCause().getMessage()
                );
            }

            e.printStackTrace();
        }
    }


    // =========================================================
    // CHECK WHETHER PROJECT + MENTOR REVIEW ALREADY EXISTS
    // =========================================================

    private static ProjectReviews findExistingReview(
            ProjectReviewsDAO dao,
            int projectId,
            int mentorId)
            throws DatabaseException {

        List<ProjectReviews> reviews =
                dao.getAllProjectReviews();

        for (ProjectReviews review : reviews) {

            if (review.getProjectId() == projectId
                    && review.getMentorId() == mentorId) {

                return review;
            }
        }

        return null;
    }
}

