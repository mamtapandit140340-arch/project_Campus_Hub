package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.MentorProfile;
import com.campushub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorProfileDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addMentorProfile(MentorProfile mentor)
            throws DatabaseException {

        String sql = """
                INSERT INTO mentor_profiles
                (mentor_id, specialization, qualification, experience_years)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mentor.getMentorId());
            ps.setString(2, mentor.getSpecialization());
            ps.setString(3, mentor.getQualification());
            ps.setInt(4, mentor.getExperienceYears());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add mentor profile to the database.",
                    e
            );
        }
    }


    // =========================
    // READ BY ID
    // =========================

    public MentorProfile getMentorProfileById(int mentorId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM mentor_profiles WHERE mentor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    MentorProfile mentor = new MentorProfile();

                    mentor.setMentorId(
                            rs.getInt("mentor_id")
                    );

                    mentor.setSpecialization(
                            rs.getString("specialization")
                    );

                    mentor.setQualification(
                            rs.getString("qualification")
                    );

                    mentor.setExperienceYears(
                            rs.getInt("experience_years")
                    );

                    return mentor;
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find mentor profile with ID: "
                            + mentorId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<MentorProfile> getAllMentorProfiles()
            throws DatabaseException {

        List<MentorProfile> mentors = new ArrayList<>();

        String sql =
                "SELECT * FROM mentor_profiles ORDER BY mentor_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                MentorProfile mentor = new MentorProfile();

                mentor.setMentorId(
                        rs.getInt("mentor_id")
                );

                mentor.setSpecialization(
                        rs.getString("specialization")
                );

                mentor.setQualification(
                        rs.getString("qualification")
                );

                mentor.setExperienceYears(
                        rs.getInt("experience_years")
                );

                mentors.add(mentor);
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve mentor profiles from the database.",
                    e
            );
        }

        return mentors;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateMentorProfile(MentorProfile mentor)
            throws DatabaseException {

        String sql = """
                UPDATE mentor_profiles
                SET specialization = ?,
                    qualification = ?,
                    experience_years = ?
                WHERE mentor_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mentor.getSpecialization());
            ps.setString(2, mentor.getQualification());
            ps.setInt(3, mentor.getExperienceYears());
            ps.setInt(4, mentor.getMentorId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update mentor profile with ID: "
                            + mentor.getMentorId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteMentorProfile(int mentorId)
            throws DatabaseException {

        String sql =
                "DELETE FROM mentor_profiles WHERE mentor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mentorId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete mentor profile with ID: "
                            + mentorId,
                    e
            );
        }
    }
}