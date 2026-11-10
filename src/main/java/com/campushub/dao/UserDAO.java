 package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.User;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // =========================
    // CREATE
    // =========================

    public static boolean addUser(User user)
            throws DatabaseException {

        String sql = "INSERT INTO users " +
                "(name, email, password, role, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }

                return true;
            }

            return false;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add user to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public User getUserById(int userId)
            throws DatabaseException {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractUser(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find user with ID: "
                            + userId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<User> getAllUsers()
            throws DatabaseException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve users from the database.",
                    e
            );
        }

        return users;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateUser(User user)
            throws DatabaseException {

        String sql = "UPDATE users SET " +
                "name = ?, email = ?, password = ?, role = ?, status = ? " +
                "WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update user with ID: "
                            + user.getUserId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteUser(int userId)
            throws DatabaseException {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete user with ID: "
                            + userId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================


    //used to find user by email

    public User getUserByEmail(String email) throws DatabaseException {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Unable to find user by email.", e
            );
        }

        return null;
    }


    private User extractUser(ResultSet rs)
            throws SQLException {

        User user = new User();

        user.setUserId(
                rs.getInt("user_id")
        );

        user.setName(
                rs.getString("name")
        );

        user.setEmail(
                rs.getString("email")
        );

        user.setPassword(
                rs.getString("password")
        );

        user.setRole(
                rs.getString("role")
        );

        user.setStatus(
                rs.getString("status")
        );

        return user;
    }
}

