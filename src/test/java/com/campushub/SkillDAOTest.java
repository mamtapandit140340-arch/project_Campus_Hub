
        package com.campushub;

import com.campushub.dao.SkillDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Skills;

public class SkillDAOTest {

    public static void main(String[] args) {

        SkillDAO dao = new SkillDAO();

        try {

            // =========================
            // CREATE
            // =========================

            Skills skill = new Skills();

            skill.setUserId(6);
            skill.setSkillName("Python");
            skill.setProficiency("ADVANCED");

            boolean added = dao.addSkill(skill);

            System.out.println("========== CREATE ==========");
            System.out.println("Skill added: " + added);


            // =========================
            // READ ALL
            // =========================

            System.out.println("\n========== READ ALL ==========");

            for (Skills s : dao.getAllSkills()) {

                System.out.println(
                        "Skill ID: " + s.getSkillId()
                                + " | User ID: " + s.getUserId()
                                + " | Skill: " + s.getSkillName()
                                + " | Proficiency: " + s.getProficiency()
                );
            }


            // =========================
            // FIND CREATED SKILL
            // =========================

            int skillId = -1;

            for (Skills s : dao.getAllSkills()) {

                if (s.getUserId() == 6
                        && s.getSkillName().equals("Python")) {

                    skillId = s.getSkillId();
                    break;
                }
            }


            // =========================
            // READ ONE
            // =========================

            Skills foundSkill = dao.getSkillById(skillId);

            System.out.println("\n========== READ ONE ==========");

            if (foundSkill != null) {

                System.out.println("Skill found:");
                System.out.println("Skill ID: "
                        + foundSkill.getSkillId());
                System.out.println("User ID: "
                        + foundSkill.getUserId());
                System.out.println("Skill Name: "
                        + foundSkill.getSkillName());
                System.out.println("Proficiency: "
                        + foundSkill.getProficiency());

            } else {

                System.out.println("Skill not found.");
            }


            // =========================
            // UPDATE
            // =========================

            if (foundSkill != null) {

                foundSkill.setSkillName("Python");
                foundSkill.setProficiency("ADVANCED");

                boolean updated =
                        dao.updateSkill(foundSkill);

                System.out.println("\n========== UPDATE ==========");
                System.out.println(
                        "Skill updated: " + updated
                );
            }


            // =========================
            // READ AFTER UPDATE
            // =========================

            Skills updatedSkill =
                    dao.getSkillById(skillId);

            System.out.println(
                    "\n========== READ AFTER UPDATE =========="
            );

            if (updatedSkill != null) {

                System.out.println("Skill ID: "
                        + updatedSkill.getSkillId());
                System.out.println("User ID: "
                        + updatedSkill.getUserId());
                System.out.println("Skill Name: "
                        + updatedSkill.getSkillName());
                System.out.println("Proficiency: "
                        + updatedSkill.getProficiency());

            } else {

                System.out.println("Skill not found.");
            }


            // =========================
            // DELETE
            // =========================

            boolean deleted =
                    dao.deleteSkill(skillId);

            System.out.println("\n========== DELETE ==========");
            System.out.println(
                    "Skill deleted: " + deleted
            );


            // =========================
            // READ AFTER DELETE
            // =========================

            Skills deletedSkill =
                    dao.getSkillById(skillId);

            System.out.println(
                    "\n========== READ AFTER DELETE =========="
            );

            if (deletedSkill == null) {

                System.out.println(
                        "Skill successfully deleted."
                );

            } else {

                System.out.println(
                        "Skill still exists."
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

