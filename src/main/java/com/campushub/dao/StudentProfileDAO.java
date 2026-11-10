package com.campushub.dao;

import com.campushub.exception.DatabaseException;
import com.campushub.model.StudentProfile;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentProfileDAO {

    // =========================
    // CREATE
    // =========================

    public boolean addStudentProfile(StudentProfile student)
            throws DatabaseException {

        String sql = "INSERT INTO student_profiles " +
                "(student_id, enrollment_no, course, branch, year, semester, bio, github_url, linkedin_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getStudentId());
            ps.setString(2, student.getEnrollmentNo());
            ps.setString(3, student.getCourse());
            ps.setString(4, student.getBranch());
            ps.setInt(5, student.getYear());
            ps.setInt(6, student.getSemester());
            ps.setString(7, student.getBio());
            ps.setString(8, student.getGithubUrl());
            ps.setString(9, student.getLinkedinUrl());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to add student profile to the database.",
                    e
            );
        }
    }


    // =========================
    // READ ONE
    // =========================

    public StudentProfile getStudentProfile(int studentId)
            throws DatabaseException {

        String sql =
                "SELECT * FROM student_profiles WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractStudentProfile(rs);
                }
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to find student profile with ID: "
                            + studentId,
                    e
            );
        }

        return null;
    }


    // =========================
    // READ ALL
    // =========================

    public List<StudentProfile> getAllStudentProfiles()
            throws DatabaseException {

        List<StudentProfile> students = new ArrayList<>();

        String sql =
                "SELECT * FROM student_profiles ORDER BY student_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(extractStudentProfile(rs));
            }

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to retrieve student profiles from the database.",
                    e
            );
        }

        return students;
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateStudentProfile(StudentProfile student)
            throws DatabaseException {

        String sql = "UPDATE student_profiles SET " +
                "enrollment_no = ?, course = ?, branch = ?, year = ?, " +
                "semester = ?, bio = ?, github_url = ?, linkedin_url = ? " +
                "WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getEnrollmentNo());
            ps.setString(2, student.getCourse());
            ps.setString(3, student.getBranch());
            ps.setInt(4, student.getYear());
            ps.setInt(5, student.getSemester());
            ps.setString(6, student.getBio());
            ps.setString(7, student.getGithubUrl());
            ps.setString(8, student.getLinkedinUrl());
            ps.setInt(9, student.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to update student profile with ID: "
                            + student.getStudentId(),
                    e
            );
        }
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteStudentProfile(int studentId)
            throws DatabaseException {

        String sql =
                "DELETE FROM student_profiles WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new DatabaseException(
                    "Unable to delete student profile with ID: "
                            + studentId,
                    e
            );
        }
    }


    // =========================
    // HELPER METHOD
    // =========================

    private StudentProfile extractStudentProfile(ResultSet rs)
            throws SQLException {

        StudentProfile student = new StudentProfile();

        student.setStudentId(
                rs.getInt("student_id")
        );

        student.setEnrollmentNo(
                rs.getString("enrollment_no")
        );

        student.setCourse(
                rs.getString("course")
        );

        student.setBranch(
                rs.getString("branch")
        );

        student.setYear(
                rs.getInt("year")
        );

        student.setSemester(
                rs.getInt("semester")
        );

        student.setBio(
                rs.getString("bio")
        );

        student.setGithubUrl(
                rs.getString("github_url")
        );

        student.setLinkedinUrl(
                rs.getString("linkedin_url")
        );

        return student;
    }
}