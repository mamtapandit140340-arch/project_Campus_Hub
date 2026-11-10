
        package com.campushub;

import com.campushub.dao.CollaborationRequestDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.CollaborationRequest;

import java.util.List;

public class CollaborationRequestDAOTest {

    public static void main(String[] args) {

        CollaborationRequestDAO dao =
                new CollaborationRequestDAO();

        // =====================================================
        // USE EXISTING IDs FROM YOUR DATABASE
        // =====================================================

        int projectId = 1;

        // Sender and receiver MUST be different
        int senderId = 2;
        int receiverId = 5;

        try {

            // =================================================
            // CHECK EXISTING REQUEST
            // =================================================

            System.out.println("===== CHECK EXISTING REQUEST =====");

            CollaborationRequest existing =
                    findExistingRequest(
                            dao,
                            projectId,
                            senderId,
                            receiverId
                    );

            if (existing != null) {

                System.out.println("Request already exists.");
                System.out.println(existing);

                System.out.println();
                System.out.println(
                        "Delete the existing request or choose another sender/receiver."
                );

                return;
            }

            System.out.println("No existing request found.");


            // =================================================
            // CREATE
            // =================================================

            System.out.println();
            System.out.println("===== CREATE =====");

            CollaborationRequest request =
                    new CollaborationRequest(
                            projectId,
                            senderId,
                            receiverId,
                            "I would like to join this project.",
                            "PENDING"
                    );

            boolean added =
                    dao.addRequest(request);

            System.out.println(
                    "Collaboration request added: " + added
            );

            if (!added) {
                System.out.println(
                        "Collaboration request was not added."
                );
                return;
            }

            System.out.println(
                    "Generated Request ID: "
                            + request.getRequestId()
            );

            System.out.println("Created Request:");
            System.out.println(request);


            // =================================================
            // READ ONE
            // =================================================

            System.out.println();
            System.out.println("===== READ BY ID =====");

            CollaborationRequest found =
                    dao.getRequestById(
                            request.getRequestId()
                    );

            if (found != null) {

                System.out.println(
                        "Collaboration request found:"
                );

                System.out.println(found);

            } else {

                System.out.println(
                        "Collaboration request not found."
                );
            }


            // =================================================
            // READ ALL
            // =================================================

            System.out.println();
            System.out.println("===== READ ALL =====");

            List<CollaborationRequest> requests =
                    dao.getAllRequests();

            if (requests.isEmpty()) {

                System.out.println(
                        "No collaboration requests found."
                );

            } else {

                for (CollaborationRequest r : requests) {
                    System.out.println(r);
                }
            }


            // =================================================
            // UPDATE
            // =================================================

            System.out.println();
            System.out.println("===== UPDATE =====");

            found.setMessage(
                    "I am interested in joining this project."
            );

            found.setStatus("ACCEPTED");

            boolean updated =
                    dao.updateRequest(found);

            System.out.println(
                    "Collaboration request updated: "
                            + updated
            );

            if (updated) {

                CollaborationRequest updatedRequest =
                        dao.getRequestById(
                                found.getRequestId()
                        );

                System.out.println(
                        "Updated Request:"
                );

                System.out.println(updatedRequest);
            }


            // =================================================
            // DELETE
            // =================================================

            System.out.println();
            System.out.println("===== DELETE =====");

            boolean deleted =
                    dao.deleteRequest(
                            request.getRequestId()
                    );

            System.out.println(
                    "Collaboration request deleted: "
                            + deleted
            );


            // =================================================
            // VERIFY DELETE
            // =================================================

            System.out.println();
            System.out.println("===== VERIFY DELETE =====");

            CollaborationRequest deletedRequest =
                    dao.getRequestById(
                            request.getRequestId()
                    );

            if (deletedRequest == null) {

                System.out.println(
                        "Collaboration request successfully deleted."
                );

            } else {

                System.out.println(
                        "Collaboration request still exists."
                );
            }


        } catch (DatabaseException e) {

            System.out.println();
            System.out.println("===== DATABASE ERROR =====");

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
        }
    }


    // =========================================================
    // FIND EXISTING REQUEST
    // =========================================================

    private static CollaborationRequest findExistingRequest(
            CollaborationRequestDAO dao,
            int projectId,
            int senderId,
            int receiverId)
            throws DatabaseException {

        List<CollaborationRequest> requests =
                dao.getAllRequests();

        for (CollaborationRequest request : requests) {

            if (request.getProjectId() == projectId
                    && request.getSenderId() == senderId
                    && request.getReceiverId() == receiverId) {

                return request;
            }
        }

        return null;
    }
}

