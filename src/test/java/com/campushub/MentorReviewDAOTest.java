
        package com.campushub;

import com.campushub.dao.MentorReviewDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.MentorReview;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MentorReviewDAOTest {

    public static void main(String[] args) {

        MentorReviewDAO dao = new MentorReviewDAO();

        int ideaId = 0;
        int mentorId = 0;

        try {

            // =====================================================
            // STEP 1: FIND IDEA + MENTOR WITHOUT EXISTING REVIEW
            // =====================================================

            System.out.println("===== FIND IDEA AND MENTOR =====");

            String findSql = """
                    SELECT i.idea_id,
                           mp.mentor_id,
                           u.name
                    FROM ideas i
                    CROSS JOIN mentor_profiles mp
                    INNER JOIN users u
                        ON mp.mentor_id = u.user_id
                    LEFT JOIN mentor_reviews mr
                        ON mr.idea_id = i.idea_id
                        AND mr.mentor_id = mp.mentor_id
                    WHERE mr.review_id IS NULL
                    ORDER BY i.idea_id, mp.mentor_id
                    LIMIT 1
                    """;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(findSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ideaId = rs.getInt("idea_id");
                    mentorId = rs.getInt("mentor_id");

                    System.out.println(
                            "Idea ID: " + ideaId
                    );

                    System.out.println(
                            "Mentor ID: " + mentorId
                    );

                    System.out.println(
                            "Mentor Name: " + rs.getString("name")
                    );

                } else {

                    System.out.println(
                            "No available IDEA + MENTOR combination found."
                    );

                    System.out.println(
                            "Every existing combination may already have a review."
                    );

                    return;
                }
            }


            // =====================================================
            // STEP 2: CREATE
            // =====================================================

            System.out.println();
            System.out.println("===== CREATE =====");

            MentorReview review =
                    new MentorReview(
                            ideaId,
                            mentorId,
                            5,
                            "APPROVED",
                            "The idea is innovative and technically feasible."
                    );

            boolean added =
                    dao.addMentorReview(review);

            System.out.println(
                    "Mentor Review Added: " + added
            );

            System.out.println(
                    "Generated Review ID: " +
                            review.getReviewId()
            );


            // =====================================================
            // STEP 3: READ BY ID
            // =====================================================

            System.out.println();
            System.out.println("===== READ BY ID =====");

            MentorReview found =
                    dao.getMentorReviewById(
                            review.getReviewId()
                    );

            if (found != null) {

                System.out.println(
                        "Mentor Review Found:"
                );

                System.out.println(found);

            } else {

                System.out.println(
                        "Mentor Review NOT FOUND"
                );
            }


            // =====================================================
            // STEP 4: READ ALL
            // =====================================================

            System.out.println();
            System.out.println("===== READ ALL =====");

            for (MentorReview mr :
                    dao.getAllMentorReviews()) {

                System.out.println(mr);
            }


            // =====================================================
            // STEP 5: UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== UPDATE =====");

            review.setRating(4);

            review.setDecision(
                    "NEEDS_IMPROVEMENT"
            );

            review.setFeedback(
                    "Good idea, but the proposed solution needs more technical details."
            );

            boolean updated =
                    dao.updateMentorReview(review);

            System.out.println(
                    "Mentor Review Updated: " + updated
            );


            // =====================================================
            // STEP 6: READ AFTER UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== READ AFTER UPDATE =====");

            MentorReview updatedReview =
                    dao.getMentorReviewById(
                            review.getReviewId()
                    );

            if (updatedReview != null) {

                System.out.println(
                        updatedReview
                );
            }


            // =====================================================
            // STEP 7: DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== DELETE =====");

            boolean deleted =
                    dao.deleteMentorReview(
                            review.getReviewId()
                    );

            System.out.println(
                    "Mentor Review Deleted: " + deleted
            );


            // =====================================================
            // STEP 8: VERIFY DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== VERIFY DELETE =====");

            MentorReview deletedReview =
                    dao.getMentorReviewById(
                            review.getReviewId()
                    );

            if (deletedReview == null) {

                System.out.println(
                        "Mentor Review successfully deleted."
                );

            } else {

                System.out.println(
                        "Mentor Review still exists."
                );
            }


            // =====================================================
            // TEST COMPLETE
            // =====================================================

            System.out.println();
            System.out.println(
                    "===== MENTOR REVIEW DAO TEST COMPLETED ====="
            );

        } catch (DatabaseException e) {

            System.out.println();
            System.out.println("===== DATABASE ERROR =====");

            System.out.println(
                    "Message: " + e.getMessage()
            );

            if (e.getCause() != null) {

                System.out.println(
                        "Cause: " +
                                e.getCause().getMessage()
                );
            }

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println();
            System.out.println("===== SQL ERROR =====");

            System.out.println(
                    "Message: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}

