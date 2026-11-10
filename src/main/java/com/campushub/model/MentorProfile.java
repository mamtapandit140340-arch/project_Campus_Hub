package com.campushub.model;

public class MentorProfile {

    private int mentorId;
    private String specialization;
    private String qualification;
    private int experienceYears;

    // Empty constructor
    public MentorProfile() {
    }

    // Constructor
    public MentorProfile(
            int mentorId,
            String specialization,
            String qualification,
            int experienceYears) {

        this.mentorId = mentorId;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experienceYears = experienceYears;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    @Override
    public String toString() {
        return "MentorProfile{" +
                "mentorId=" + mentorId +
                ", specialization='" + specialization + '\'' +
                ", qualification='" + qualification + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }
}