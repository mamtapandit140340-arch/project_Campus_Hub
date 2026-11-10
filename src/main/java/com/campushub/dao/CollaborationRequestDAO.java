package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.CollaborationRequest;
import com.campushub.util.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollaborationRequestDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addRequest(CollaborationRequest request)
            throws DatabaseException {

        String sql = """
                INSERT INTO collaboration_requests
                (project_id, sender_id, receiver_id, message, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, request.getProjectId());
            ps.setInt(2, request.getSenderId());
            ps.setInt(3, request.getReceiverId());
            ps.setString(4, request.getMessage());
            ps.setString(5, request.getStatus());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        request.setRequestId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to add collaboration request to the database.",
                    e
            );
        }

        return false;
    }

    // =========================
    // READ ONE
    // =========================

    public CollaborationRequest getRequestById(int requestId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM collaboration_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractRequest(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to find collaboration request with ID: "
                            + requestId,
                    e
            );
        }

        return null;
    }

    // =========================
    // READ ALL
    // =========================

    public List<CollaborationRequest> getAllRequests()
            throws DatabaseException {

        List<CollaborationRequest> requests = new ArrayList<>();

        String sql =
                "SELECT * FROM collaboration_requests ORDER BY request_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                requests.add(extractRequest(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to retrieve collaboration requests.",
                    e
            );
        }

        return requests;
    }

    // =========================
    // UPDATE
    // =========================

    public boolean updateRequest(CollaborationRequest request)
            throws DatabaseException {

        String sql = """
                UPDATE collaboration_requests
                SET project_id = ?,
                    sender_id = ?,
                    receiver_id = ?,
                    message = ?,
                    status = ?
                WHERE request_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getProjectId());
            ps.setInt(2, request.getSenderId());
            ps.setInt(3, request.getReceiverId());
            ps.setString(4, request.getMessage());
            ps.setString(5, request.getStatus());
            ps.setInt(6, request.getRequestId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to update collaboration request with ID: "
                            + request.getRequestId(),
                    e
            );
        }
    }

    // =========================
    // DELETE
    // =========================

    public boolean deleteRequest(int requestId)
            throws DatabaseException {

        String sql =
                "DELETE FROM collaboration_requests WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to delete collaboration request with ID: "
                            + requestId,
                    e
            );
        }
    }

    // ==================================================
    // SPRING SERVICE SUPPORT
    // These methods use the Connection supplied by the
    // service so the operations share one transaction.
    // ==================================================

    // Read and lock a request belonging to this receiver.

    public CollaborationRequest getPendingRequestForReceiver(
            int requestId,
            int receiverId,
            Connection con) throws SQLException {

        String sql = """
                SELECT request_id, project_id, sender_id, status
                FROM collaboration_requests
                WHERE request_id = ?
                  AND receiver_id = ?
                FOR UPDATE
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ps.setInt(2, receiverId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    CollaborationRequest request =
                            new CollaborationRequest();

                    request.setRequestId(rs.getInt("request_id"));
                    request.setProjectId(rs.getInt("project_id"));
                    request.setSenderId(rs.getInt("sender_id"));
                    request.setStatus(rs.getString("status"));

                    return request;
                }
            }
        }

        return null;
    }

    // Change the status only if the request is still pending.

    public boolean updateStatus(
            int requestId,
            int receiverId,
            String newStatus,
            Connection con) throws SQLException {

        String sql = """
                UPDATE collaboration_requests
                SET status = ?
                WHERE request_id = ?
                  AND receiver_id = ?
                  AND status = 'PENDING'
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, requestId);
            ps.setInt(3, receiverId);

            return ps.executeUpdate() == 1;
        }
    }

    // Add the request sender to the project when accepted.

    public void addProjectMember(
            int projectId,
            int userId,
            Connection con) throws SQLException {

        String sql = """
                INSERT INTO project_members
                (project_id, user_id, member_role)
                VALUES (?, ?, 'MEMBER')
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }

    // =========================
    // HELPER METHOD
    // =========================

    private CollaborationRequest extractRequest(ResultSet rs)
            throws SQLException {

        CollaborationRequest request = new CollaborationRequest();

        request.setRequestId(rs.getInt("request_id"));
        request.setProjectId(rs.getInt("project_id"));
        request.setSenderId(rs.getInt("sender_id"));
        request.setReceiverId(rs.getInt("receiver_id"));
        request.setMessage(rs.getString("message"));
        request.setStatus(rs.getString("status"));
        request.setCreatedAt(rs.getTimestamp("created_at"));

        return request;
    }
}