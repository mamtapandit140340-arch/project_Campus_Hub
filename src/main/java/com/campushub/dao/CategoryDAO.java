
        package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Category;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addCategory(Category category)
            throws DatabaseException {

        String sql = """
                INSERT INTO categories (category_name, description)
                VALUES (?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, category.getCatName());
            statement.setString(2, category.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add category to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ALL
    // =========================

    public List<Category> getAllCategories()
            throws DatabaseException {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Category category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("description")
                );

                categories.add(category);
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve categories from the database.",
                    e
            );
        }

        return categories;
    }


    // =========================
    // READ ONE
    // =========================

    public Category getCategoryById(int id)
            throws DatabaseException {

        String sql =
                "SELECT * FROM categories WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name"),
                            resultSet.getString("description")
                    );
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find category with ID: " + id,
                    e
            );
        }

        return null;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateCategory(Category category)
            throws DatabaseException {

        String sql = """
                UPDATE categories
                SET category_name = ?, description = ?
                WHERE category_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, category.getCatName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update category with ID: "
                            + category.getId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteCategory(int id)
            throws DatabaseException {

        String sql =
                "DELETE FROM categories WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete category with ID: " + id,
                    e
            );
        }
    }
}

