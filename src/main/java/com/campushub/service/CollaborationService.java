package com.campushub.service;

import com.campushub.dao.CollaborationRequestDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.CollaborationRequest;
import com.campushub.util.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CollaborationService {

    private final CollaborationRequestDAO collaborationRequestDAO;

    @Autowired
    public CollaborationService(
            CollaborationRequestDAO collaborationRequestDAO) {

        this.collaborationRequestDAO = collaborationRequestDAO;
    }

    public String getServiceStatus() {
        return "Spring is managing CollaborationService";
    }

    public void processRequest(
            int requestId,
            int receiverId,
            String action)
            throws DatabaseException {

        if (!"accept".equals(action)
                && !"reject".equals(action)) {

            throw new IllegalArgumentException(
                    "Invalid collaboration request action."
            );
        }

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {

                CollaborationRequest request =
                        collaborationRequestDAO
                                .getPendingRequestForReceiver(
                                        requestId,
                                        receiverId,
                                        con
                                );

                if (request == null) {
                    throw new IllegalArgumentException(
                            "Request not found or cannot be managed."
                    );
                }

                if (!"PENDING".equals(request.getStatus())) {
                    throw new IllegalStateException(
                            "This request has already been processed."
                    );
                }

                String newStatus =
                        "accept".equals(action)
                                ? "ACCEPTED"
                                : "REJECTED";

                boolean updated =
                        collaborationRequestDAO.updateStatus(
                                requestId,
                                receiverId,
                                newStatus,
                                con
                        );

                if (!updated) {
                    throw new IllegalStateException(
                            "Request was already processed."
                    );
                }

                if ("accept".equals(action)) {

                    collaborationRequestDAO.addProjectMember(
                            request.getProjectId(),
                            request.getSenderId(),
                            con
                    );
                }

                con.commit();

            } catch (Exception e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                throw e;
            } finally {

                try {
                    con.setAutoCommit(true);
                } catch (SQLException ignored) {
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to process collaboration request.",
                    e
            );

        } catch (RuntimeException e) {

            throw e;

        } catch (Exception e) {

            throw new DatabaseException(
                    "Unable to process collaboration request.",
                    e
            );
        }
    }
}