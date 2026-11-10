package com.campushub.model;

public class StudentProfile {

    private int studentId;
    private String enrollmentNo;
    private String course;
    private String branch;
    private int year;
    private int semester;
    private String bio;
    private String githubUrl;
    private String linkedinUrl;

    // Default constructor
    public StudentProfile() {
    }

    // Constructor
    public StudentProfile(int studentId, String enrollmentNo,
                          String course, String branch,
                          int year, int semester,
                          String bio, String githubUrl,
                          String linkedinUrl) {

        this.studentId = studentId;
        this.enrollmentNo = enrollmentNo;
        this.course = course;
        this.branch = branch;
        this.year = year;
        this.semester = semester;
        this.bio = bio;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }
}