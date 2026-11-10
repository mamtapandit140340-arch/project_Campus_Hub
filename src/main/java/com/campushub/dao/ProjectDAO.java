package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Project;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addProject(Project project) throws DatabaseException {

        String sql = """
                INSERT INTO projects
                (idea_id, mentor_id, project_name, description,
                 start_date, expected_end_date, status,
                 progress_percentage)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, project.getIdeaId());
            ps.setInt(2, project.getMentorId());
            ps.setString(3, project.getProjectName());
            ps.setString(4, project.getDescription());
            ps.setDate(5, project.getStartDate());
            ps.setDate(6, project.getExpectedEndDate());
            ps.setString(7, project.getStatus());
            ps.setInt(8, project.getProgressPercentage());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    project.setProjectId(rs.getInt(1));
                }

                return true;
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add project to the database.",
                    e
            );
        }

        return false;
    }


    // =========================
    // READ BY ID
    // =========================

    public Project getProjectById(int projectId)
            throws DatabaseException {

        String sql = "SELECT * FROM projects WHERE project_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractProject(rs);
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find project with ID: " + projectId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<Project> getAllProjects()
            throws DatabaseException {

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects ORDER BY project_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                projects.add(extractProject(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve projects from the database.",
                    e
            );
        }

        return projects;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateProject(Project project)
            throws DatabaseException {

        String sql = """
                UPDATE projects
                SET idea_id = ?,
                    mentor_id = ?,
                    project_name = ?,
                    description = ?,
                    start_date = ?,
                    expected_end_date = ?,
                    status = ?,
                    progress_percentage = ?
                WHERE project_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, project.getIdeaId());
            ps.setInt(2, project.getMentorId());
            ps.setString(3, project.getProjectName());
            ps.setString(4, project.getDescription());
            ps.setDate(5, project.getStartDate());
            ps.setDate(6, project.getExpectedEndDate());
            ps.setString(7, project.getStatus());
            ps.setInt(8, project.getProgressPercentage());
            ps.setInt(9, project.getProjectId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update project with ID: "
                            + project.getProjectId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteProject(int projectId)
            throws DatabaseException {

        String sql = "DELETE FROM projects WHERE project_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete project with ID: "
                            + projectId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private Project extractProject(ResultSet rs)
            throws SQLException {

        Project project = new Project();

        project.setProjectId(
                rs.getInt("project_id")
        );

        project.setIdeaId(
                rs.getInt("idea_id")
        );

        project.setMentorId(
                rs.getInt("mentor_id")
        );

        project.setProjectName(
                rs.getString("project_name")
        );

        project.setDescription(
                rs.getString("description")
        );

        project.setStartDate(
                rs.getDate("start_date")
        );

        project.setExpectedEndDate(
                rs.getDate("expected_end_date")
        );

        project.setStatus(
                rs.getString("status")
        );

        project.setProgressPercentage(
                rs.getInt("progress_percentage")
        );

        project.setCreatedAt(
                rs.getTimestamp("created_at")
        );

        return project;
    }
}

