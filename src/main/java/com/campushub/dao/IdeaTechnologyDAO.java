package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.IdeaTechnology;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdeaTechnologyDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addTechnology(IdeaTechnology technology)
            throws DatabaseException {

        String sql = """
                INSERT INTO idea_technologies
                (idea_id, technology)
                VALUES (?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, technology.getIdeaId());
            ps.setString(2, technology.getTechnology());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add technology to the database.",
                    e
            );
        }
    }


    // =========================
    // READ one
    // =========================

    public IdeaTechnology getTechnology(
            int ideaId,
            String technologyName)
            throws DatabaseException {

        String sql = """
                SELECT * FROM idea_technologies
                WHERE idea_id = ? AND technology = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ideaId);
            ps.setString(2, technologyName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    IdeaTechnology technology =
                            new IdeaTechnology();

                    technology.setIdeaId(
                            rs.getInt("idea_id")
                    );

                    technology.setTechnology(
                            rs.getString("technology")
                    );

                    return technology;
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find technology for idea ID: "
                            + ideaId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<IdeaTechnology> getAllTechnologies()
            throws DatabaseException {

        List<IdeaTechnology> technologies =
                new ArrayList<>();

        String sql =
                "SELECT * FROM idea_technologies ORDER BY idea_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                IdeaTechnology technology =
                        new IdeaTechnology();

                technology.setIdeaId(
                        rs.getInt("idea_id")
                );

                technology.setTechnology(
                        rs.getString("technology")
                );

                technologies.add(technology);
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve idea technologies.",
                    e
            );
        }

        return technologies;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateTechnology(
            IdeaTechnology technology,
            String oldTechnology)
            throws DatabaseException {

        String sql = """
                UPDATE idea_technologies
                SET technology = ?
                WHERE idea_id = ?
                AND technology = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, technology.getTechnology());
            ps.setInt(2, technology.getIdeaId());
            ps.setString(3, oldTechnology);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update technology for idea ID: "
                            + technology.getIdeaId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteTechnology(
            int ideaId,
            String technology)
            throws DatabaseException {

        String sql = """
                DELETE FROM idea_technologies
                WHERE idea_id = ? AND technology = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ideaId);
            ps.setString(2, technology);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete technology from idea ID: "
                            + ideaId,
                    e
            );
        }
    }
}