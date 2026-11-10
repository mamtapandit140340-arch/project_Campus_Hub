package com.campushub.servlet;

import com.campushub.dao.SkillDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Skills;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/skills")
public class SkillServlet extends HttpServlet {

    private SkillDAO skillDAO;

    @Override
    public void init() {
        skillDAO = new SkillDAO();
    }

    // =====================================================
    // GET
    // Display logged-in user's skills
    // =====================================================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        int userId;

        try {

            userId = Integer.parseInt(
                    session.getAttribute("userId").toString()
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {

            List<Skills> skills =
                    skillDAO.getSkillsByUserId(userId);

            request.setAttribute("skills", skills);

            request.getRequestDispatcher("/skills.jsp")
                    .forward(request, response);

        } catch (DatabaseException e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "Unable to load your skills."
            );

            request.setAttribute(
                    "skills",
                    List.of()
            );

            request.getRequestDispatcher("/skills.jsp")
                    .forward(request, response);
        }
    }


    // =====================================================
    // POST
    // Add / Update / Delete
    // =====================================================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        int userId;

        try {

            userId = Integer.parseInt(
                    session.getAttribute("userId").toString()
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/skills"
            );
            return;
        }


        // =================================================
        // ADD SKILL
        // =================================================

        if ("add".equals(action)) {

            String skillName =
                    request.getParameter("skillName");

            String proficiency =
                    request.getParameter("proficiency");

            if (skillName == null ||
                    skillName.trim().isEmpty() ||
                    proficiency == null ||
                    proficiency.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );
                return;
            }

            skillName = skillName.trim();

            Skills skill = new Skills();

            skill.setUserId(userId);
            skill.setSkillName(skillName);
            skill.setProficiency(proficiency);

            try {

                boolean added =
                        skillDAO.addSkill(skill);

                if (added) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=added"
                    );

                } else {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=invalid"
                    );
                }

            } catch (DatabaseException e) {

                e.printStackTrace();

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );
            }

            return;
        }


        // =================================================
        // UPDATE SKILL
        // =================================================

        if ("update".equals(action)) {

            String skillIdParameter =
                    request.getParameter("skillId");

            String skillName =
                    request.getParameter("skillName");

            String proficiency =
                    request.getParameter("proficiency");

            if (skillIdParameter == null ||
                    skillName == null ||
                    skillName.trim().isEmpty() ||
                    proficiency == null ||
                    proficiency.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );
                return;
            }

            try {

                int skillId =
                        Integer.parseInt(skillIdParameter);

                Skills skill = new Skills();

                skill.setSkillId(skillId);
                skill.setUserId(userId);
                skill.setSkillName(skillName.trim());
                skill.setProficiency(proficiency);

                boolean updated =
                        skillDAO.updateSkill(skill);

                if (updated) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=updated"
                    );

                } else {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=notfound"
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );

            } catch (DatabaseException e) {

                e.printStackTrace();

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );
            }

            return;
        }


        // =================================================
        // DELETE SKILL
        // =================================================

        if ("delete".equals(action)) {

            String skillIdParameter =
                    request.getParameter("skillId");

            if (skillIdParameter == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=notfound"
                );
                return;
            }

            try {

                int skillId =
                        Integer.parseInt(skillIdParameter);

                boolean deleted =
                        skillDAO.deleteSkill(skillId);

                if (deleted) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=deleted"
                    );

                } else {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/skills?result=notfound"
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=notfound"
                );

            } catch (DatabaseException e) {

                e.printStackTrace();

                response.sendRedirect(
                        request.getContextPath()
                                + "/skills?result=invalid"
                );
            }

            return;
        }


        // =================================================
        // UNKNOWN ACTION
        // =================================================

        response.sendRedirect(
                request.getContextPath() + "/skills"
        );
    }
}
