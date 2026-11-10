<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 31-10-2026
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>My Skills | Campus Hub</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f7f8fc;
            color: #202938;
        }

        a {
            text-decoration: none;
        }

        /* Sidebar */

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 238px;
            height: 100vh;
            background: #191526;
            padding: 24px 15px;
            display: flex;
            flex-direction: column;
            color: white;
            overflow-y: auto;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 0 9px 28px;
            color: white;
        }

        .brand-icon {
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #6558e8;
            border-radius: 12px;
            font-size: 22px;
        }

        .brand h2 {
            margin: 0;
            font-size: 18px;
        }

        .brand p {
            color: #aaa5c2;
            font-size: 11px;
            margin: 5px 0 0;
        }

        .nav-title {
            color: #858097;
            font-size: 10px;
            font-weight: bold;
            letter-spacing: 1.2px;
            padding: 12px;
            text-transform: uppercase;
        }

        .nav-link {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 13px;
            margin: 3px 0;
            border-radius: 8px;
            color: #c5c0d4;
            font-size: 13px;
            transition: 0.2s;
        }

        .nav-link:hover {
            background: #302a45;
            color: white;
        }

        .nav-link.active {
            background: #6558e8;
            color: white;
        }

        .nav-icon {
            width: 19px;
            text-align: center;
        }

        .nav-divider {
            border: none;
            border-top: 1px solid #363044;
            margin: 14px 8px;
        }

        .sidebar-bottom {
            margin-top: auto;
        }

        .logout-link {
            color: #ffb4b4;
        }

        /* Main content */

        .main-content {
            margin-left: 238px;
            padding: 35px;
            min-height: 100vh;
        }

        .page-header {
            margin-bottom: 25px;
        }

        .page-header h1 {
            font-size: 30px;
            margin: 0 0 10px;
        }

        .page-header p {
            color: #737b91;
            margin: 0;
            line-height: 1.6;
        }

        /* Messages */

        .notice {
            padding: 13px 16px;
            margin-bottom: 18px;
            border-radius: 8px;
            background: #dcfce7;
            color: #166534;
            font-size: 14px;
        }

        .notice.error {
            background: #fee2e2;
            color: #991b1b;
        }

        /* Add skill section */

        .panel {
            background: white;
            border: 1px solid #e7e8ef;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 30px;
        }

        .panel h2 {
            margin: 0 0 8px;
            font-size: 20px;
        }

        .panel-description {
            color: #777f91;
            font-size: 13px;
            margin: 0 0 22px;
        }

        .form-row {
            display: flex;
            gap: 18px;
            align-items: end;
            flex-wrap: wrap;
        }

        .form-group {
            flex: 1;
            min-width: 180px;
        }

        label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        input,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #dfe1e8;
            border-radius: 7px;
            font-size: 14px;
            background: white;
            color: #202938;
            outline: none;
        }

        input:focus,
        select:focus {
            border-color: #6558e8;
        }

        button {
            font-family: inherit;
        }

        .btn {
            display: inline-flex;
            justify-content: center;
            align-items: center;
            padding: 12px 19px;
            border: none;
            border-radius: 7px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
            transition: 0.2s;
        }

        .btn-primary {
            background: #6558e8;
            color: white;
        }

        .btn-primary:hover {
            background: #5144cf;
        }

        .btn-edit {
            background: #eeeaff;
            color: #594bd5;
        }

        .btn-edit:hover {
            background: #e0dbff;
        }

        .btn-delete {
            background: #fee2e2;
            color: #b91c1c;
        }

        .btn-delete:hover {
            background: #fecaca;
        }

        /* Skills list */

        .skills-heading {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            margin-bottom: 17px;
        }

        .skills-heading h2 {
            margin: 0;
            font-size: 21px;
        }

        .skill-count {
            background: #eeeaff;
            color: #594bd5;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }

        .skills-list {
            display: flex;
            flex-wrap: wrap;
            gap: 17px;
        }

        .skill-card {
            background: white;
            border: 1px solid #e7e8ef;
            border-radius: 11px;
            padding: 20px;
            width: calc(33.333% - 12px);
            min-width: 230px;
            flex: 1 1 230px;
        }

        .skill-card-top {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 10px;
        }

        .skill-icon {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            background: #eeeaff;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6558e8;
            font-size: 19px;
        }

        .skill-card h3 {
            margin: 14px 0 10px;
            font-size: 17px;
            overflow-wrap: anywhere;
        }

        .proficiency {
            display: inline-block;
            padding: 6px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }

        .beginner {
            color: #92400e;
            background: #fef3c7;
        }

        .intermediate {
            color: #1d4ed8;
            background: #dbeafe;
        }

        .advanced {
            color: #166534;
            background: #dcfce7;
        }

        .skill-actions {
            display: flex;
            gap: 9px;
            margin-top: 18px;
        }

        .skill-actions form {
            margin: 0;
        }

        /* Edit dropdown */

        .edit-section {
            margin-top: 17px;
            border-top: 1px solid #ececf1;
            padding-top: 13px;
        }

        .edit-section summary {
            color: #594bd5;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
        }

        .edit-form {
            display: flex;
            flex-direction: column;
            gap: 13px;
            margin-top: 15px;
        }

        .edit-form .btn {
            width: 100%;
        }

        /* Empty state */

        .empty-state {
            width: 100%;
            background: white;
            border: 1px dashed #d1d5db;
            border-radius: 10px;
            text-align: center;
            padding: 40px 20px;
            color: #737b91;
        }

        .empty-icon {
            font-size: 35px;
            margin-bottom: 10px;
        }

        .empty-state h3 {
            color: #202938;
            margin: 0 0 8px;
        }

        .footer {
            text-align: center;
            color: #9ca3af;
            font-size: 13px;
            padding: 35px 10px 15px;
        }

        /* Responsive */

        @media (max-width: 900px) {

            .sidebar {
                width: 200px;
            }

            .main-content {
                margin-left: 200px;
                padding: 25px 18px;
            }
        }

        @media (max-width: 600px) {

            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }

            .sidebar nav {
                display: flex;
                flex-wrap: wrap;
            }

            .sidebar-bottom {
                margin-top: 10px;
            }

            .main-content {
                margin-left: 0;
                padding: 22px 14px;
            }

            .form-row {
                flex-direction: column;
                align-items: stretch;
            }

            .form-group {
                width: 100%;
            }

            .form-row .btn {
                width: 100%;
            }

            .skill-card {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<!-- Sidebar -->
<aside class="sidebar">
    <a class="brand"
       href="${pageContext.request.contextPath}/student-dashboard">
        <div class="brand-icon">🎓</div>

        <div>
            <h2>Campus Hub</h2>
            <p>Student Portal</p>
        </div>
    </a>

    <div class="nav-title">Workspace</div>

    <nav>

        <a href="${pageContext.request.contextPath}/student-dashboard"
           class="nav-link">
            <span class="nav-icon">▦</span>
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/projects"
           class="nav-link">
            <span class="nav-icon">▣</span>
            Projects
        </a>

        <a href="${pageContext.request.contextPath}/ideas"
           class="nav-link">
            <span class="nav-icon">✦</span>
            My Ideas
        </a>

        <a href="${pageContext.request.contextPath}/collaboration-requests"
           class="nav-link">
            <span class="nav-icon">⇄</span>
            Collaboration
        </a>

        <a href="${pageContext.request.contextPath}/skills"
           class="nav-link active">
            <span class="nav-icon">◇</span>
            My Skills
        </a>

        <a href="${pageContext.request.contextPath}/student-profile"
           class="nav-link">
            <span class="nav-icon">♙</span>
            My Profile
        </a>
    </nav>

    <hr class="nav-divider">
    <div class="nav-title">Account</div>
    <div class="sidebar-bottom">

        <a href="${pageContext.request.contextPath}/help"
           class="nav-link">
            <span class="nav-icon">?</span>
            Help &amp; Support
        </a>

        <a href="${pageContext.request.contextPath}/logout"
           class="nav-link logout-link">
            <span class="nav-icon">⇥</span>
            Logout
        </a>
    </div>
</aside>

<!-- Main content -->
<main class="main-content">
    <header class="page-header">
        <h1>My Skills</h1>

        <p>Showcase your abilities, manage your skills and
            highlight your strengths to other students.</p>
    </header>

    <!-- Result messages -->
    <c:if test="${param.result == 'added'}">
        <div class="notice">
            Skill added successfully.
        </div>
    </c:if>

    <c:if test="${param.result == 'updated'}">
        <div class="notice">
            Skill updated successfully.
        </div>
    </c:if>

    <c:if test="${param.result == 'deleted'}">
        <div class="notice">
            Skill deleted successfully.
        </div>
    </c:if>

    <c:if test="${param.result == 'duplicate'}">
        <div class="notice error">
            This skill already exists in your list.
        </div>
    </c:if>

    <c:if test="${param.result == 'invalid'}">
        <div class="notice error">
            Please enter a valid skill name and proficiency level.
        </div>
    </c:if>

    <c:if test="${param.result == 'notfound'}">
        <div class="notice error">
            Skill not found. Please refresh the page and try again.
        </div>
    </c:if>


    <!-- Add Skill -->

    <section class="panel">

        <h2>+ Add a New Skill</h2>

        <p class="panel-description">
            Add your technical or soft skills and select your proficiency level.
        </p>

        <form method="post"
              action="${pageContext.request.contextPath}/skills">

            <input type="hidden" name="action" value="add">

            <div class="form-row">

                <div class="form-group">

                    <label for="skillName">Skill Name</label>

                    <input type="text"
                           id="skillName"
                           name="skillName"
                           placeholder="Example: Java, Python, Communication"
                           maxlength="100"
                           required>

                </div>

                <div class="form-group">

                    <label for="proficiency">Proficiency Level</label>

                    <select id="proficiency"
                            name="proficiency"
                            required>

                        <option value="">Select proficiency</option>
                        <option value="BEGINNER">Beginner</option>
                        <option value="INTERMEDIATE">Intermediate</option>
                        <option value="ADVANCED">Advanced</option>
                    </select>

                </div>

                <button type="submit" class="btn btn-primary">
                    + Add Skill
                </button>
            </div>
        </form>
    </section>

    <!-- Display Skills -->

    <section>

        <div class="skills-heading">

            <h2>My Added Skills</h2>

            <span class="skill-count">
                ${skills.size()} Skills
            </span>

        </div>

        <div class="skills-list">

            <c:forEach var="skill" items="${skills}">

                <article class="skill-card">

                    <div class="skill-card-top">

                        <div class="skill-icon">◇</div>

                        <c:choose>

                            <c:when test="${skill.proficiency == 'BEGINNER'}">
                                <span class="proficiency beginner">
                                    Beginner
                                </span>
                            </c:when>

                            <c:when test="${skill.proficiency == 'INTERMEDIATE'}">
                                <span class="proficiency intermediate">
                                    Intermediate
                                </span>
                            </c:when>

                            <c:otherwise>
                                <span class="proficiency advanced">
                                    Advanced
                                </span>
                            </c:otherwise>

                        </c:choose>

                    </div>

                    <h3>
                        <c:out value="${skill.skillName}"/>
                    </h3>


                    <!-- Edit and Delete -->

                    <div class="skill-actions">

                        <details class="edit-section">

                            <summary>Edit Skill</summary>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/skills"
                                  class="edit-form">

                                <input type="hidden"
                                       name="action"
                                       value="update">

                                <input type="hidden"
                                       name="skillId"
                                       value="${skill.skillId}">

                                <div>

                                    <label>Skill Name</label>

                                    <input type="text"
                                           name="skillName"
                                           value="<c:out value='${skill.skillName}'/>"
                                           maxlength="100"
                                           required>

                                </div>

                                <div>

                                    <label>Proficiency</label>

                                    <select name="proficiency" required>

                                        <option value="BEGINNER"
                                            ${skill.proficiency == 'BEGINNER' ? 'selected' : ''}>
                                            Beginner
                                        </option>

                                        <option value="INTERMEDIATE"
                                            ${skill.proficiency == 'INTERMEDIATE' ? 'selected' : ''}>
                                            Intermediate
                                        </option>

                                        <option value="ADVANCED"
                                            ${skill.proficiency == 'ADVANCED' ? 'selected' : ''}>
                                            Advanced
                                        </option>

                                    </select>

                                </div>

                                <button type="submit"
                                        class="btn btn-primary">
                                    Save Changes
                                </button>

                            </form>

                        </details>

                        <form method="post"
                              action="${pageContext.request.contextPath}/skills"
                              onsubmit="return confirm('Are you sure you want to delete this skill?');">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="skillId"
                                   value="${skill.skillId}">

                            <button type="submit"
                                    class="btn btn-delete">
                                Delete
                            </button>
                        </form>
                    </div>
                </article>
            </c:forEach>

            <c:if test="${empty skills}">
                <div class="empty-state">
                    <div class="empty-icon">◇</div>
                    <h3>No Skills Added Yet</h3>
               <p>Start by adding your first skill using the form above.</p>
                </div>
            </c:if>
        </div>
    </section>

    <footer class="footer">Campus Innovation and Collaboration Hub
    </footer>
</main>
</body>
</html>