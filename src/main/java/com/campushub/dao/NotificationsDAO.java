package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Notifications;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addNotification(Notifications notification)
            throws DatabaseException {

        String sql = "INSERT INTO notifications " +
                "(user_id, message, is_read) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setBoolean(3, notification.isRead());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add notification to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public Notifications getNotificationById(int notificationId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM notifications WHERE notification_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractNotification(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find notification with ID: "
                            + notificationId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<Notifications> getAllNotifications()
            throws DatabaseException {

        List<Notifications> notifications =
                new ArrayList<>();

        String sql =
                "SELECT * FROM notifications ORDER BY notification_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                notifications.add(extractNotification(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve notifications from the database.",
                    e
            );
        }

        return notifications;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateNotification(Notifications notification)
            throws DatabaseException {

        String sql =
                "UPDATE notifications SET " +
                        "message = ?, is_read = ? " +
                        "WHERE notification_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, notification.getMessage());
            ps.setBoolean(2, notification.isRead());
            ps.setInt(3, notification.getNotificationId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update notification with ID: "
                            + notification.getNotificationId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteNotification(int notificationId)
            throws DatabaseException {

        String sql =
                "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete notification with ID: "
                            + notificationId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private Notifications extractNotification(ResultSet rs)
            throws SQLException {

        Notifications notification = new Notifications();

        notification.setNotificationId(
                rs.getInt("notification_id")
        );

        notification.setUserId(
                rs.getInt("user_id")
        );

        notification.setMessage(
                rs.getString("message")
        );

        notification.setRead(
                rs.getBoolean("is_read")
        );

        notification.setCreatedAt(
                rs.getTimestamp("created_at")
        );

        return notification;
    }
}