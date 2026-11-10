package com.campushub;

import com.campushub.dao.CommentsDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Comments;

import java.util.List;

public class CommentsDAOTest {

    public static void main(String[] args) {

        CommentsDAO dao = new CommentsDAO();

        // IMPORTANT:
        // Use IDs that already exist in your database.
        int projectId = 1;
        int userId = 2;

        try {

            // =========================
            // CREATE
            // =========================

            System.out.println("===== CREATE =====");

            Comments comment = new Comments(
                    projectId,
                    userId,
                    "This project has a very good implementation."
            );

            boolean added = dao.addComment(comment);

            System.out.println("Comment added: " + added);

            // Since comment_id is auto-increment,
            // find the newly created comment from the list.
            List<Comments> comments = dao.getAllComments();

            if (comments.isEmpty()) {
                System.out.println("No comments found.");
                return;
            }

            Comments createdComment =
                    comments.get(comments.size() - 1);

            int commentId =
                    createdComment.getCommentId();

            System.out.println(
                    "Created Comment ID: " + commentId
            );


            // =========================
            // READ ONE
            // =========================

            System.out.println("\n===== READ ONE =====");

            Comments found =
                    dao.getCommentById(commentId);

            if (found != null) {
                System.out.println("Comment found:");
                System.out.println(found);
            } else {
                System.out.println("Comment not found.");
            }


            // =========================
            // READ ALL
            // =========================

            System.out.println("\n===== READ ALL =====");

            List<Comments> allComments =
                    dao.getAllComments();

            if (allComments.isEmpty()) {

                System.out.println("No comments found.");

            } else {

                for (Comments c : allComments) {
                    System.out.println(c);
                }
            }


            // =========================
            // UPDATE
            // =========================

            System.out.println("\n===== UPDATE =====");

            found.setCommentText(
                    "Updated comment: Excellent project and useful idea."
            );

            boolean updated =
                    dao.updateComment(found);

            System.out.println(
                    "Comment updated: " + updated
            );


            // Check updated record
            Comments updatedComment =
                    dao.getCommentById(commentId);

            System.out.println(
                    "Updated Comment: " + updatedComment
            );


            // =========================
            // DELETE
            // =========================

            System.out.println("\n===== DELETE =====");

            boolean deleted =
                    dao.deleteComment(commentId);

            System.out.println(
                    "Comment deleted: " + deleted
            );


            // =========================
            // VERIFY DELETE
            // =========================

            System.out.println("\n===== VERIFY DELETE =====");

            Comments deletedComment =
                    dao.getCommentById(commentId);

            if (deletedComment == null) {
                System.out.println(
                        "Comment successfully deleted."
                );
            } else {
                System.out.println(
                        "Comment still exists: "
                                + deletedComment
                );
            }

            System.out.println("\n===== TEST COMPLETED =====");

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