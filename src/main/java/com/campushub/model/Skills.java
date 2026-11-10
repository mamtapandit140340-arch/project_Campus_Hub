package com.campushub.model;

public class Skills {

    private int skillId;
    private int userId;
    private String skillName;
    private String proficiency;

    public Skills() {
    }

    public Skills(int skillId, int userId, String skillName, String proficiency) {
        this.skillId = skillId;
        this.userId = userId;
        this.skillName = skillName;
        this.proficiency = proficiency;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}