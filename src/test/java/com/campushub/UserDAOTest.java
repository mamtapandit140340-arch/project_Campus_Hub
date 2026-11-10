
        package com.campushub;

import com.campushub.dao.UserDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        try {

             String email = "teststudent10@gmail.com";

           /* User existingUser = dao.getUserByEmail(email);

            if (existingUser != null) {
                System.out.println("A user with this email already exists.");
                System.out.println("Existing User ID: " + existingUser.getUserId());
                return;
            }*/

            User user = new User();

            user.setName("Test Student New");
            user.setEmail(email);
            user.setPassword("12345");
            user.setRole("STUDENT");
            user.setStatus("ACTIVE");

            boolean added = dao.addUser(user);

            System.out.println("User added: " + added);
            System.out.println("Generated User ID: " + user.getUserId());

            // =========================
            // READ ONE
            // =========================

            int userId = user.getUserId();

            User foundUser = dao.getUserById(userId);

            System.out.println("\n========== READ ONE ==========");

            if (foundUser != null) {

                System.out.println("User found:");
                System.out.println("ID: " + foundUser.getUserId());
                System.out.println("Name: " + foundUser.getName());
                System.out.println("Email: " + foundUser.getEmail());
                System.out.println("Role: " + foundUser.getRole());
                System.out.println("Status: " + foundUser.getStatus());

            } else {

                System.out.println("User not found.");
            }


            // =========================
            // UPDATE
            // =========================

            if (foundUser != null) {

                foundUser.setName("Updated Student");
                foundUser.setEmail("updatedstudent8@gmail.com");
                foundUser.setPassword("54321");
                foundUser.setRole("STUDENT");
                foundUser.setStatus("ACTIVE");

                boolean updated = dao.updateUser(foundUser);

                System.out.println("\n========== UPDATE ==========");
                System.out.println("User updated: " + updated);
            }


            // =========================
            // READ AFTER UPDATE
            // =========================

            User updatedUser = dao.getUserById(userId);

            System.out.println("\n========== READ AFTER UPDATE ==========");

            if (updatedUser != null) {

                System.out.println("ID: " + updatedUser.getUserId());
                System.out.println("Name: " + updatedUser.getName());
                System.out.println("Email: " + updatedUser.getEmail());
                System.out.println("Role: " + updatedUser.getRole());
                System.out.println("Status: " + updatedUser.getStatus());

            } else {

                System.out.println("User not found.");
            }


            // =========================
            // DELETE
            // =========================

            boolean deleted = dao.deleteUser(userId);

            System.out.println("\n========== DELETE ==========");
            System.out.println("User deleted: " + deleted);


            // =========================
            // READ AFTER DELETE
            // =========================

            User deletedUser = dao.getUserById(userId);

            System.out.println("\n========== READ AFTER DELETE ==========");

            if (deletedUser == null) {

                System.out.println("User successfully deleted.");

            } else {

                System.out.println("User still exists.");
            }


        } catch (DatabaseException e) {

            System.out.println("\n========== DATABASE ERROR ==========");
            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("\n========== UNEXPECTED ERROR ==========");
            e.printStackTrace();
        }
    }
}

