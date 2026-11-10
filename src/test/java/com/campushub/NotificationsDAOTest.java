package com.campushub;

import com.campushub.dao.NotificationsDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Notifications;

public class NotificationsDAOTest {

    public static void main(String[] args) {

        NotificationsDAO dao = new NotificationsDAO();

        try {

            // =========================
            // CREATE
            // =========================

            Notifications notification =
                    new Notifications(
                            5,
                            "Your idea has been approved by the mentor.",
                            false
                    );

            boolean added = dao.addNotification(notification);

            System.out.println("CREATE: " + added);


            // =========================
            // READ ALL
            // =========================

            System.out.println("\nREAD ALL:");

            for (Notifications n : dao.getAllNotifications()) {

                System.out.println(
                        n.getNotificationId()
                                + " - User ID: "
                                + n.getUserId()
                                + " - "
                                + n.getMessage()
                                + " - Read: "
                                + n.isRead()
                );
            }


            // =========================
            // READ ONE
            // =========================

            // Use the ID of an existing notification.
            // We will first get the last notification from the list.

            Notifications createdNotification = null;

            for (Notifications n : dao.getAllNotifications()) {
                createdNotification = n;
            }

            if (createdNotification != null) {

                int notificationId =
                        createdNotification.getNotificationId();

                Notifications found =
                        dao.getNotificationById(notificationId);

                if (found != null) {

                    System.out.println(
                            "\nREAD ONE: "
                                    + found.getNotificationId()
                                    + " - "
                                    + found.getMessage()
                    );


                    // =========================
                    // UPDATE
                    // =========================

                    found.setMessage(
                            "Your idea has been approved successfully!"
                    );

                    found.setRead(true);

                    boolean updated =
                            dao.updateNotification(found);

                    System.out.println(
                            "UPDATE: " + updated
                    );


                    // =========================
                    // DELETE
                    // =========================

                    // Uncomment only if you want to test DELETE.
                    /*
                    boolean deleted =
                            dao.deleteNotification(notificationId);

                    System.out.println(
                            "DELETE: " + deleted
                    );
                    */
                }
            }

        } catch (DatabaseException e) {

            System.out.println(
                    "Database Error: " + e.getMessage()
            );
        }
    }
}