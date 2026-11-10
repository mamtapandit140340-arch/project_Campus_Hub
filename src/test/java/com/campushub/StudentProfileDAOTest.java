
        package com.campushub;

import com.campushub.dao.StudentProfileDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.StudentProfile;

public class StudentProfileDAOTest {

    public static void main(String[] args) {

        StudentProfileDAO dao = new StudentProfileDAO();

        try {

            // =========================
            // CREATE
            // =========================

            StudentProfile student = new StudentProfile();

            // User ID 6 exists in users table
            // and student_profiles does not have ID 6
            student.setStudentId(6);

            student.setEnrollmentNo("MU20260006");
            student.setCourse("B.Tech");
            student.setBranch("Computer Engineering");
            student.setYear(2);
            student.setSemester(3);
            student.setBio("Computer Engineering student");
            student.setGithubUrl("https://github.com/teststudent6");
            student.setLinkedinUrl("https://linkedin.com/in/teststudent6");

            boolean added = dao.addStudentProfile(student);

            System.out.println("========== CREATE ==========");
            System.out.println("Student Profile added: " + added);


            // =========================
            // READ ONE
            // =========================

            StudentProfile foundStudent =
                    dao.getStudentProfile(6);

            System.out.println("\n========== READ ONE ==========");

            if (foundStudent != null) {

                System.out.println("Student Profile found:");
                System.out.println("Student ID: "
                        + foundStudent.getStudentId());
                System.out.println("Enrollment No: "
                        + foundStudent.getEnrollmentNo());
                System.out.println("Course: "
                        + foundStudent.getCourse());
                System.out.println("Branch: "
                        + foundStudent.getBranch());
                System.out.println("Year: "
                        + foundStudent.getYear());
                System.out.println("Semester: "
                        + foundStudent.getSemester());
                System.out.println("Bio: "
                        + foundStudent.getBio());
                System.out.println("GitHub: "
                        + foundStudent.getGithubUrl());
                System.out.println("LinkedIn: "
                        + foundStudent.getLinkedinUrl());

            } else {

                System.out.println("Student Profile not found.");
            }


            // =========================
            // READ ALL
            // =========================

            System.out.println("\n========== READ ALL ==========");

            for (StudentProfile s : dao.getAllStudentProfiles()) {

                System.out.println(
                        "ID: " + s.getStudentId()
                                + " | Enrollment: " + s.getEnrollmentNo()
                                + " | Course: " + s.getCourse()
                                + " | Branch: " + s.getBranch()
                );
            }


            // =========================
            // UPDATE
            // =========================

            if (foundStudent != null) {

                foundStudent.setEnrollmentNo("MU20260006");
                foundStudent.setCourse("B.Tech");
                foundStudent.setBranch("Computer Engineering");
                foundStudent.setYear(2);
                foundStudent.setSemester(4);
                foundStudent.setBio(
                        "Updated Computer Engineering student"
                );
                foundStudent.setGithubUrl(
                        "https://github.com/updatedstudent6"
                );
                foundStudent.setLinkedinUrl(
                        "https://linkedin.com/in/updatedstudent6"
                );

                boolean updated =
                        dao.updateStudentProfile(foundStudent);

                System.out.println("\n========== UPDATE ==========");
                System.out.println(
                        "Student Profile updated: " + updated
                );
            }


            // =========================
            // READ AFTER UPDATE
            // =========================

            StudentProfile updatedStudent =
                    dao.getStudentProfile(6);

            System.out.println(
                    "\n========== READ AFTER UPDATE =========="
            );

            if (updatedStudent != null) {

                System.out.println("Student ID: "
                        + updatedStudent.getStudentId());
                System.out.println("Enrollment No: "
                        + updatedStudent.getEnrollmentNo());
                System.out.println("Course: "
                        + updatedStudent.getCourse());
                System.out.println("Branch: "
                        + updatedStudent.getBranch());
                System.out.println("Year: "
                        + updatedStudent.getYear());
                System.out.println("Semester: "
                        + updatedStudent.getSemester());
                System.out.println("Bio: "
                        + updatedStudent.getBio());
                System.out.println("GitHub: "
                        + updatedStudent.getGithubUrl());
                System.out.println("LinkedIn: "
                        + updatedStudent.getLinkedinUrl());

            } else {

                System.out.println("Student Profile not found.");
            }


            // =========================
            // DELETE
            // =========================

            boolean deleted =
                    dao.deleteStudentProfile(6);

            System.out.println("\n========== DELETE ==========");
            System.out.println(
                    "Student Profile deleted: " + deleted
            );


            // =========================
            // READ AFTER DELETE
            // =========================

            StudentProfile deletedStudent =
                    dao.getStudentProfile(6);

            System.out.println(
                    "\n========== READ AFTER DELETE =========="
            );

            if (deletedStudent == null) {

                System.out.println(
                        "Student Profile no longer exists."
                );

            } else {

                System.out.println(
                        "Student Profile still exists."
                );
            }


        } catch (DatabaseException e) {

            System.out.println(
                    "\n========== DATABASE ERROR =========="
            );

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                    "\n========== UNEXPECTED ERROR =========="
            );

            e.printStackTrace();
        }
    }
}

