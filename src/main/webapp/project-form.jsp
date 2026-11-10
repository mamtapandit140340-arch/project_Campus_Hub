<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 01-11-2026
  Time: 00:43
  To change this template use File | Settings | File Templates.
--%><%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

    <c:set var="displayName" value="${sessionScope.userName}" />

    <c:if test="${empty displayName}">
        <c:set var="displayName" value="Student" />
    </c:if>

    <%
        if (session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String contextPath = request.getContextPath();
    %>

    <!DOCTYPE html>
    <html lang="en">

    <head>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Create Project | Campus Hub</title>

        <style>

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, Helvetica, sans-serif;
                background: #f7f8fc;
                color: #242236;
            }

            a {
                text-decoration: none;
                color: inherit;
            }

            /* SIDEBAR */

            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                width: 238px;
                height: 100vh;
                background: #191526;
                color: white;
                padding: 24px 15px;
                display: flex;
                flex-direction: column;
                z-index: 10;
                overflow-y: auto;
            }

            .brand {
                display: flex;
                align-items: center;
                gap: 12px;
                padding: 0 9px 28px;
            }

            .brand-icon {
                width: 42px;
                height: 42px;
                background: #6558e8;
                border-radius: 10px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 22px;
            }

            .brand h2 {
                font-size: 18px;
            }

            .brand p {
                font-size: 11px;
                color: #aaa5c2;
                margin-top: 5px;
            }

            .nav-title {
                color: #858097;
                font-size: 10px;
                font-weight: bold;
                letter-spacing: 1.2px;
                padding: 12px;
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
                transform: translateX(3px);
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

            /* MAIN */

            .main {
                margin-left: 238px;
                min-height: 100vh;
                padding: 25px 30px 35px;
            }

            .topbar {
                display: flex;
                align-items: center;
                justify-content: flex-end;
                margin-bottom: 32px;
            }

            .topbar-right {
                display: flex;
                align-items: center;
                gap: 17px;
            }

            .notification {
                width: 38px;
                height: 38px;
                display: flex;
                align-items: center;
                justify-content: center;
                border: 1px solid #ececf3;
                border-radius: 10px;
                background: white;
            }

            /* PROFILE DROPDOWN - SAME AS DASHBOARD */

            .profile-menu {
                position: relative;
            }

            .profile-menu summary {
                list-style: none;
                cursor: pointer;
            }

            .profile-menu summary::-webkit-details-marker {
                display: none;
            }

            .profile-trigger {
                display: flex;
                align-items: center;
                padding: 5px 8px;
                border-radius: 9px;
            }

            .profile-trigger:hover {
                background: #eeecff;
            }

            .profile {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .profile-info {
                text-align: right;
            }

            .profile-info strong {
                display: block;
                font-size: 12px;
            }

            .profile-info span {
                color: #85869a;
                font-size: 11px;
            }

            .avatar {
                width: 40px;
                height: 40px;
                background: #6558e8;
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: bold;
            }

            .profile-dropdown {
                position: absolute;
                right: 0;
                top: calc(100% + 10px);
                width: 215px;
                padding: 8px;
                background: white;
                border: 1px solid #ececf3;
                border-radius: 11px;
                box-shadow: 0 12px 30px rgba(30,25,55,0.13);
                z-index: 200;
            }

            .profile-dropdown-heading {
                padding: 10px;
                border-bottom: 1px solid #ececf3;
                margin-bottom: 6px;
            }

            .profile-dropdown-heading strong,
            .profile-dropdown-heading span {
                display: block;
            }

            .profile-dropdown-heading strong {
                font-size: 12px;
            }

            .profile-dropdown-heading span {
                font-size: 10px;
                color: #85869a;
                margin-top: 4px;
            }

            .profile-dropdown a {
                display: block;
                padding: 10px;
                font-size: 12px;
                border-radius: 7px;
            }

            .profile-dropdown a:hover {
                background: #f4f2ff;
                color: #6558e8;
            }

            .profile-dropdown .logout-menu {
                color: #c03939;
            }

            /* PAGE HEADING */

            .breadcrumb {
                font-size: 12px;
                color: #85869a;
                margin-bottom: 12px;
            }

            .breadcrumb a:hover {
                color: #6558e8;
            }

            .page-heading {
                margin-bottom: 23px;
            }

            .page-heading h1 {
                font-size: 26px;
                margin-bottom: 8px;
            }

            .page-heading p {
                font-size: 13px;
                color: #85869a;
                line-height: 1.6;
            }

            /* FORM */

            .form-container {
                max-width: 800px;
                background: white;
                padding: 25px;
                border: 1px solid #ececf3;
                border-radius: 12px;
            }

            .form-container h2 {
                font-size: 17px;
                margin-bottom: 8px;
            }

            .form-description {
                color: #85869a;
                font-size: 12px;
                margin-bottom: 23px;
            }

            .form-group {
                margin-bottom: 19px;
            }

            .form-group label {
                display: block;
                font-size: 12px;
                font-weight: bold;
                margin-bottom: 8px;
            }

            .required {
                color: red;
            }

            .form-control {
                width: 100%;
                padding: 11px;
                border: 1px solid #ececf3;
                border-radius: 7px;
                font-size: 12px;
                font-family: Arial, sans-serif;
                outline: none;
                background: white;
                color: #242236;
            }

            .form-control:focus {
                border-color: #6558e8;
            }

            textarea.form-control {
                height: 105px;
                resize: vertical;
            }

            .form-help {
                display: block;
                margin-top: 6px;
                font-size: 10px;
                color: #85869a;
            }

            /* MESSAGES */

            .error-message {
                background: #fff1f1;
                color: #b83232;
                border: 1px solid #f2caca;
                padding: 12px;
                margin-bottom: 18px;
                border-radius: 7px;
                font-size: 12px;
            }

            .warning-message {
                background: #fff8e8;
                color: #946200;
                border: 1px solid #f2dfb5;
                padding: 13px;
                margin-bottom: 18px;
                border-radius: 7px;
                font-size: 12px;
                line-height: 1.6;
            }

            /* BUTTONS */

            .form-buttons {
                display: flex;
                justify-content: flex-end;
                gap: 10px;
                margin-top: 25px;
                padding-top: 20px;
                border-top: 1px solid #ececf3;
            }

            .button {
                padding: 11px 18px;
                border-radius: 7px;
                font-size: 12px;
                cursor: pointer;
                border: none;
            }

            .cancel-button {
                background: #f0f0f5;
                color: #555;
            }

            .cancel-button:hover {
                background: #e7e7ed;
            }

            .submit-button {
                background: #6558e8;
                color: white;
            }

            .submit-button:hover {
                background: #5144d2;
            }

            .submit-button:disabled {
                background: #aaa;
                cursor: not-allowed;
            }

            .footer {
                text-align: center;
                color: #85869a;
                font-size: 11px;
                padding-top: 30px;
            }

            /* RESPONSIVE */

            @media (max-width: 700px) {

                .sidebar {
                    position: relative;
                    width: 100%;
                    height: auto;
                    padding: 15px;
                }

                .brand {
                    padding-bottom: 12px;
                }

                .nav-title,
                .nav-divider,
                .sidebar-bottom {
                    display: none;
                }

                .sidebar nav {
                    display: flex;
                    overflow-x: auto;
                    gap: 7px;
                }

                .nav-link {
                    flex-shrink: 0;
                    padding: 10px;
                }

                .nav-link span:last-child {
                    display: none;
                }

                .main {
                    margin-left: 0;
                    padding: 20px 14px;
                }

                .profile-info {
                    display: none;
                }

                .form-container {
                    padding: 17px;
                }

                .form-buttons {
                    flex-direction: column-reverse;
                }

                .button {
                    width: 100%;
                    text-align: center;
                }
            }

        </style>

    </head>

    <body>

    <!-- SIDEBAR -->

    <aside class="sidebar">

        <div class="brand">
            <div class="brand-icon">🎓</div>

            <div>
                <h2>Campus Hub</h2>
                <p>Student Portal</p>
            </div>
        </div>

        <div class="nav-title">Workspace</div>

        <nav>

            <a href="<%= contextPath %>/student-dashboard"
               class="nav-link">
                <span class="nav-icon">▦</span>
                <span>Dashboard</span>
            </a>

            <a href="<%= contextPath %>/projects"
               class="nav-link active">
                <span class="nav-icon">▣</span>
                <span>Projects</span>
            </a>

            <a href="<%= contextPath %>/ideas"
               class="nav-link">
                <span class="nav-icon">✦</span>
                <span>My Ideas</span>
            </a>

            <a href="<%= contextPath %>/collaboration-requests"
               class="nav-link">
                <span class="nav-icon">⇄</span>
                <span>Collaboration</span>
            </a>

            <a href="<%= contextPath %>/skills"
               class="nav-link">
                <span class="nav-icon">◇</span>
                <span>My Skills</span>
            </a>

            <a href="<%= contextPath %>/student-profile"
               class="nav-link">
                <span class="nav-icon">♙</span>
                <span>My Profile</span>
            </a>

        </nav>

        <hr class="nav-divider">

        <div class="nav-title">Account</div>

        <nav>

            <a href="<%= contextPath %>/student-profile"
               class="nav-link">
                <span class="nav-icon">⚙</span>
                <span>Profile &amp; Settings</span>
            </a>

            <a href="<%= contextPath %>/help"
               class="nav-link">
                <span class="nav-icon">?</span>
                <span>Help &amp; Support</span>
            </a>

        </nav>

        <div class="sidebar-bottom">

            <hr class="nav-divider">

            <a href="<%= contextPath %>/logout"
               class="nav-link logout-link">
                <span class="nav-icon">⇥</span>
                <span>Logout</span>
            </a>

        </div>

    </aside>


    <!-- MAIN CONTENT -->

    <main class="main">

        <!-- TOPBAR -->

        <header class="topbar">

            <div class="topbar-right">

                <a href="<%= contextPath %>/collaboration-requests"
                   class="notification">
                    ♧
                </a>

                <!-- SAME PROFILE DROPDOWN AS DASHBOARD -->

                <details class="profile-menu">

                    <summary class="profile-trigger">

                        <div class="profile">

                            <div class="profile-info">
                                <strong>
                                    <c:out value="${displayName}"/>
                                </strong>
                                <span>Student</span>
                            </div>

                            <div class="avatar">
                                <c:out value="${fn:substring(displayName, 0, 1)}"/>
                            </div>

                        </div>

                    </summary>

                    <div class="profile-dropdown">

                        <div class="profile-dropdown-heading">
                            <strong>
                                <c:out value="${displayName}"/>
                            </strong>
                            <span>Student Account</span>
                        </div>

                        <a href="<%= contextPath %>/student-profile">
                            👤 My Profile
                        </a>

                        <a href="<%= contextPath %>/student-profile?mode=edit">
                            ✎ Edit Profile
                        </a>

                        <a href="<%= contextPath %>/student-profile?mode=settings">
                            ⚙ Account Settings
                        </a>

                        <a href="<%= contextPath %>/logout"
                           class="logout-menu">
                            ⇥ Logout
                        </a>

                    </div>

                </details>

            </div>

        </header>


        <!-- PAGE HEADING -->

        <section class="page-heading">

            <div class="breadcrumb">

                <a href="<%= contextPath %>/projects">
                    Projects
                </a>

                / Create Project

            </div>

            <h1>Create New Project</h1>

            <p>
                Select one of your approved ideas and add the project details.
            </p>

        </section>


        <!-- PROJECT FORM -->

        <section class="form-container">

            <h2>Project Details</h2>

            <p class="form-description">
                Fill in the information below to create your project.
                Fields marked with * are compulsory.
            </p>


            <!-- ERROR MESSAGE -->

            <c:if test="${not empty errorMessage}">

                <div class="error-message">
                    <c:out value="${errorMessage}"/>
                </div>

            </c:if>


            <!-- NO APPROVED IDEAS -->

            <c:if test="${empty availableIdeas}">

                <div class="warning-message">
                    You do not have any approved ideas available for project creation.
                    Submit an idea and wait for mentor approval before creating a project.
                </div>

            </c:if>


            <!-- NO MENTORS -->

            <c:if test="${empty mentors}">

                <div class="warning-message">
                    No active mentors are available at the moment.
                    A mentor must be available to create a project.
                </div>

            </c:if>


            <form action="<%= contextPath %>/project-form"
                  method="post">


                <!-- SELECT IDEA -->

                <div class="form-group">

                    <label for="ideaId">
                        Select Approved Idea <span class="required">*</span>
                    </label>

                    <select name="ideaId"
                            id="ideaId"
                            class="form-control"
                            required>

                        <option value="">
                            Select your approved idea
                        </option>

                        <c:forEach items="${availableIdeas}" var="idea">

                            <option value="${idea.idea_id}"
                                <c:if test="${param.ideaId == idea.idea_id}">
                                    selected
                                </c:if>>

                                <c:out value="${idea.title}"/>

                            </option>

                        </c:forEach>

                    </select>

                    <small class="form-help">
                        Only your approved ideas that have not already been
                        converted into projects are displayed.
                    </small>

                </div>


                <!-- PROJECT NAME -->

                <div class="form-group">

                    <label for="projectName">
                        Project Name <span class="required">*</span>
                    </label>

                    <input type="text"
                           name="projectName"
                           id="projectName"
                           class="form-control"
                           placeholder="Enter your project name"
                           maxlength="200"
                           value="<c:out value='${param.projectName}'/>"
                           required>

                </div>


                <!-- MENTOR -->

                <div class="form-group">

                    <label for="mentorId">
                        Select Mentor <span class="required">*</span>
                    </label>

                    <select name="mentorId"
                            id="mentorId"
                            class="form-control"
                            required>

                        <option value="">
                            Select a mentor
                        </option>

                        <c:forEach items="${mentors}" var="mentor">

                            <option value="${mentor.user_id}"
                                <c:if test="${param.mentorId == mentor.user_id}">
                                    selected
                                </c:if>>

                                <c:out value="${mentor.name}"/>

                                <c:if test="${not empty mentor.specialization}">
                                    - <c:out value="${mentor.specialization}"/>
                                </c:if>

                            </option>

                        </c:forEach>

                    </select>

                </div>


                <!-- DESCRIPTION -->

                <div class="form-group">

                    <label for="description">
                        Project Description
                    </label>

                    <textarea name="description"
                              id="description"
                              class="form-control"
                              placeholder="Describe what your project will do..."><c:out value="${param.description}"/></textarea>
                     </div>

                <!-- START DATE -->
                <div class="form-group">
                    <label for="startDate">
                        Start Date
                    </label>
                    <input type="date"
                           name="startDate"
                           id="startDate"
                           class="form-control"
                           value="<c:out value='${param.startDate}'/>">
                </div>

                <!-- EXPECTED END DATE -->
                <div class="form-group">
                    <label for="expectedEndDate">
                        Expected End Date
                    </label>

                    <input type="date"
                           name="expectedEndDate"
                           id="expectedEndDate"
                           class="form-control"
                           value="<c:out value='${param.expectedEndDate}'/>">
                </div>

                <!-- BUTTONS -->
                <div class="form-buttons">
                    <a href="<%= contextPath %>/projects"
                       class="button cancel-button">
                        Cancel
                    </a>

                    <button type="submit"
                            class="button submit-button"
                            <c:if test="${empty availableIdeas or empty mentors}">
                                disabled
                            </c:if>>
                        Create Project
                    </button>
                </div>
            </form>
        </section>

        <footer class="footer">
            © 2026 Campus Innovation and Collaboration Hub
        </footer>
    </main>
    </body>
    </html>