
        package com.campushub;

import com.campushub.dao.ProjectMemberDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.ProjectMember;
import com.campushub.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMemberDAOTest {

    public static void main(String[] args) {

        ProjectMemberDAO dao = new ProjectMemberDAO();

        int projectId = 0;
        int studentId = 0;

        try {

            // =====================================================
            // STEP 1: FIND EXISTING PROJECT
            // =====================================================

            System.out.println("===== FIND EXISTING PROJECT =====");

            String projectSql =
                    "SELECT project_id FROM projects ORDER BY project_id LIMIT 1";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(projectSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    projectId = rs.getInt("project_id");

                    System.out.println(
                            "Project ID: " + projectId
                    );

                } else {

                    System.out.println(
                            "No project found."
                    );

                    System.out.println(
                            "Create a project first, then run this test."
                    );

                    return;
                }
            }


            // =====================================================
            // STEP 2: FIND ACTIVE STUDENT
            // =====================================================

            System.out.println();
            System.out.println("===== FIND ACTIVE STUDENT =====");

            String studentSql = """
        SELECT user_id, name
        FROM users
        WHERE user_id = 5
        AND role = 'STUDENT'
        AND status = 'ACTIVE'
        """;

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(studentSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    studentId = rs.getInt("user_id");

                    System.out.println(
                            "Student ID: " + studentId
                    );

                    System.out.println(
                            "Student Name: " + rs.getString("name")
                    );

                } else {

                    System.out.println(
                            "No ACTIVE STUDENT found."
                    );

                    return;
                }
            }


            // =====================================================
            // STEP 3: CHECK WHETHER STUDENT IS ALREADY MEMBER
            // =====================================================

            System.out.println();
            System.out.println("===== CHECK EXISTING MEMBER =====");

            ProjectMember existing =
                    dao.getProjectMember(projectId, studentId);

            if (existing != null) {

                System.out.println(
                        "Student is already a member."
                );

                System.out.println(
                        existing
                );

                System.out.println();
                System.out.println(
                        "Delete the existing membership from the database"
                );
                System.out.println(
                        "or choose another ACTIVE STUDENT."
                );

                return;
            }

            System.out.println(
                    "Student is not currently a member."
            );


            // =====================================================
            // STEP 4: CREATE
            // =====================================================

            System.out.println();
            System.out.println("===== CREATE =====");

            ProjectMember member =
                    new ProjectMember(
                            projectId,
                            studentId,
                            "MEMBER"
                    );

            boolean added =
                    dao.addProjectMember(member);

            System.out.println(
                    "Project Member Added: " + added
            );


            // =====================================================
            // STEP 5: READ ONE
            // =====================================================

            System.out.println();
            System.out.println("===== READ ONE =====");

            ProjectMember found =
                    dao.getProjectMember(
                            projectId,
                            studentId
                    );

            if (found != null) {

                System.out.println(
                        "Project Member Found:"
                );

                System.out.println(
                        found
                );

            } else {

                System.out.println(
                        "Project Member NOT FOUND"
                );
            }


            // =====================================================
            // STEP 6: READ ALL
            // =====================================================

            System.out.println();
            System.out.println("===== READ ALL =====");

            for (ProjectMember pm :
                    dao.getAllProjectMembers()) {

                System.out.println(pm);
            }


            // =====================================================
            // STEP 7: UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== UPDATE =====");

            member.setMemberRole("LEADER");

            boolean updated =
                    dao.updateProjectMember(member);

            System.out.println(
                    "Project Member Updated: " + updated
            );


            // =====================================================
            // STEP 8: READ AFTER UPDATE
            // =====================================================

            System.out.println();
            System.out.println("===== READ AFTER UPDATE =====");

            ProjectMember updatedMember =
                    dao.getProjectMember(
                            projectId,
                            studentId
                    );

            if (updatedMember != null) {

                System.out.println(
                        updatedMember
                );
            }


            // =====================================================
            // STEP 9: DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== DELETE =====");

            boolean deleted =
                    dao.deleteProjectMember(
                            projectId,
                            studentId
                    );

            System.out.println(
                    "Project Member Deleted: " + deleted
            );


            // =====================================================
            // STEP 10: VERIFY DELETE
            // =====================================================

            System.out.println();
            System.out.println("===== VERIFY DELETE =====");

            ProjectMember deletedMember =
                    dao.getProjectMember(
                            projectId,
                            studentId
                    );

            if (deletedMember == null) {

                System.out.println(
                        "Project Member successfully deleted."
                );

            } else {

                System.out.println(
                        "Project Member still exists."
                );
            }


            // =====================================================
            // TEST COMPLETE
            // =====================================================

            System.out.println();
            System.out.println(
                    "===== PROJECT MEMBER DAO TEST COMPLETED ====="
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

