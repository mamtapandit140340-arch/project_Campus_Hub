
        package com.campushub;

import com.campushub.dao.MentorProfileDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.MentorProfile;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MentorProfileDAOTest {

    public static void main(String[] args) {

        MentorProfileDAO dao = new MentorProfileDAO();

        int mentorId = 0;

        try {

            // =====================================================
            // STEP 1: FIND ACTIVE MENTOR WITHOUT PROFILE
            // =====================================================

            System.out.println("===== FIND ACTIVE MENTOR =====");

            String mentorSql = """
                    SELECT u.user_id, u.name
                    FROM users u
                    LEFT JOIN mentor_profiles mp
                        ON u.user_id = mp.mentor_id
                    WHERE u.role = 'MENTOR'
                      AND u.status = 'ACTIVE'
                      AND mp.mentor_id IS NULL
                    ORDER BY u.user_id
                    LIMIT 1
                    """;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(mentorSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    mentorId = rs.getInt("user_id");

                    System.out.println(
                            "Mentor ID: " + mentorId
                    );

                    System.out.println(
                            "Mentor Name: " + rs.getString("name")
                    );

                } else {

                    System.out.println(
                            "No ACTIVE MENTOR without a profile found."
                    );

                    System.out.println(
                            "All active mentors may already have profiles."
                    );

                    return;
                }
            }


            // =====================================================
            // STEP 2: CREATE
            // =====================================================

            System.out.println();
            System.out.println("===== CREATE =====");

            MentorProfile mentor =
                    new MentorProfile(
                            mentorId,
                            "Artificial Intelligence",
                            "M.Tech Computer Engineering",
                            5
                    );

            boolean added =
                    dao.addMentorProfile(mentor);

            System.out.println(
                    "Mentor Profile Added: " + added
            );


            // =====================================================
            // STEP 3: READ BY ID
            // =====================================================

            System.out.println();
            System.out.println("===== READ BY ID =====");

            MentorProfile found =
                    dao.getMentorProfileById(mentorId);

            if (found != null) {

                System.out.println(
                        "Mentor Profile Found:"
                );

                System.out.println(found);

            } else {

                System.out.println(
                        "Mentor Profile NOT FOUND"
                );
            }


            // =====================================================
            // STEP 4: READ ALL
            // =====================================================

            System.out.println();
            System.out.println("===== READ ALL =====");

            for (MentorProfile mp :
                    dao.getAllMentorProfiles()) {

                System.out.println(mp);
            }


            // =====================================================
            // STEP 5: UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== UPDATE =====");

            mentor.setSpecialization(
                    "Artificial Intelligence and Machine Learning"
            );

            mentor.setQualification(
                    "Ph.D. Computer Science"
            );

            mentor.setExperienceYears(7);

            boolean updated =
                    dao.updateMentorProfile(mentor);

            System.out.println(
                    "Mentor Profile Updated: " + updated
            );


            // =====================================================
            // STEP 6: READ AFTER UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== READ AFTER UPDATE =====");

            MentorProfile updatedProfile =
                    dao.getMentorProfileById(mentorId);

            if (updatedProfile != null) {

                System.out.println(
                        updatedProfile
                );
            }


            // =====================================================
            // STEP 7: DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== DELETE =====");

            boolean deleted =
                    dao.deleteMentorProfile(mentorId);

            System.out.println(
                    "Mentor Profile Deleted: " + deleted
            );


            // =====================================================
            // STEP 8: VERIFY DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== VERIFY DELETE =====");

            MentorProfile deletedProfile =
                    dao.getMentorProfileById(mentorId);

            if (deletedProfile == null) {

                System.out.println(
                        "Mentor Profile successfully deleted."
                );

            } else {

                System.out.println(
                        "Mentor Profile still exists."
                );
            }


            // =====================================================
            // TEST COMPLETE
            // =====================================================

            System.out.println();
            System.out.println(
                    "===== MENTOR PROFILE DAO TEST COMPLETED ====="
            );

        } catch (DatabaseException e) {

            System.out.println();
            System.out.println("===== DATABASE ERROR =====");

            System.out.println(
                    "Message: " + e.getMessage()
            );

            if (e.getCause() != null) {

                System.out.println(
                        "Cause: " + e.getCause().getMessage()
                );
            }

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println();
            System.out.println("===== SQL ERROR =====");

            System.out.println(
                    "Message: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}

