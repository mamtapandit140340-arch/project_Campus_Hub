package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.ProjectMember;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addProjectMember(ProjectMember member)
            throws DatabaseException {

        String sql = """
                INSERT INTO project_members
                (project_id, user_id, member_role)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, member.getProjectId());
            ps.setInt(2, member.getUserId());
            ps.setString(3, member.getMemberRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add project member to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public ProjectMember getProjectMember(
            int projectId,
            int userId)
            throws DatabaseException {

        String sql = """
                SELECT * FROM project_members
                WHERE project_id = ? AND user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractProjectMember(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find project member.",
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<ProjectMember> getAllProjectMembers()
            throws DatabaseException {

        List<ProjectMember> members = new ArrayList<>();

        String sql =
                "SELECT * FROM project_members ORDER BY project_id, user_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(extractProjectMember(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve project members.",
                    e
            );
        }

        return members;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateProjectMember(
            ProjectMember member)
            throws DatabaseException {

        String sql = """
                UPDATE project_members
                SET member_role = ?
                WHERE project_id = ? AND user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, member.getMemberRole());
            ps.setInt(2, member.getProjectId());
            ps.setInt(3, member.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update project member.",
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteProjectMember(
            int projectId,
            int userId)
            throws DatabaseException {

        String sql = """
                DELETE FROM project_members
                WHERE project_id = ? AND user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete project member.",
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private ProjectMember extractProjectMember(
            ResultSet rs)
            throws SQLException {

        ProjectMember member = new ProjectMember();

        member.setProjectId(
                rs.getInt("project_id")
        );

        member.setUserId(
                rs.getInt("user_id")
        );

        member.setMemberRole(
                rs.getString("member_role")
        );

        member.setJoinedAt(
                rs.getTimestamp("joined_at")
        );

        return member;
    }
}