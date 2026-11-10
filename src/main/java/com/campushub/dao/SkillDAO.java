package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.Skills;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addSkill(Skills skill)
            throws DatabaseException {

        String sql = "INSERT INTO skills " +
                "(user_id, skill_name, proficiency) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, skill.getUserId());
            ps.setString(2, skill.getSkillName());
            ps.setString(3, skill.getProficiency());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add skill to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public Skills getSkillById(int skillId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM skills WHERE skill_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, skillId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractSkill(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find skill with ID: "
                            + skillId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<Skills> getAllSkills()
            throws DatabaseException {

        List<Skills> skills = new ArrayList<>();

        String sql =
                "SELECT * FROM skills ORDER BY skill_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                skills.add(extractSkill(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve skills from the database.",
                    e
            );
        }

        return skills;
    }
    public List<Skills> getSkillsByUserId(int userId)
            throws DatabaseException {

        List<Skills> skills = new ArrayList<>();

        String sql =
                "SELECT * FROM skills " +
                        "WHERE user_id = ? " +
                        "ORDER BY skill_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    skills.add(extractSkill(rs));
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve skills for user ID: "
                            + userId,e);
        }
        return skills;
    }

    // =========================
    // UPDATE
    // =========================

    public boolean updateSkill(Skills skill)
            throws DatabaseException {

        String sql = "UPDATE skills SET " +
                "user_id = ?, skill_name = ?, proficiency = ? " +
                "WHERE skill_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, skill.getUserId());
            ps.setString(2, skill.getSkillName());
            ps.setString(3, skill.getProficiency());
            ps.setInt(4, skill.getSkillId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update skill with ID: "
                            + skill.getSkillId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteSkill(int skillId)
            throws DatabaseException {

        String sql =
                "DELETE FROM skills WHERE skill_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, skillId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete skill with ID: "
                            + skillId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private Skills extractSkill(ResultSet rs)
            throws SQLException {

        Skills skill = new Skills();

        skill.setSkillId(
                rs.getInt("skill_id")
        );

        skill.setUserId(
                rs.getInt("user_id")
        );

        skill.setSkillName(
                rs.getString("skill_name")
        );

        skill.setProficiency(
                rs.getString("proficiency")
        );

        return skill;
    }
}