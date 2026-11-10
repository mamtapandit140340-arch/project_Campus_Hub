
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    jakarta.servlet.http.HttpSession mentorSession =
            request.getSession(false);

    if (mentorSession == null
            || !"MENTOR".equalsIgnoreCase(
                String.valueOf(mentorSession.getAttribute("role")))) {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Mentor Dashboard | Campus Hub</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f7f8fc;
            color: #29253b;
        }

        /* SIDEBAR */

        .sidebar {
            width: 235px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            background: #191526;
            color: white;
            padding: 25px 15px;
            display: flex;
            flex-direction: column;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 11px;
            padding: 5px 10px 35px;
        }

        .brand-icon {
            width: 42px;
            height: 42px;
            background: #6558e8;
            border-radius: 9px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 22px;
            transition: 0.3s;
        }

        .brand:hover .brand-icon {
            transform: rotate(-6deg);
            background: #796def;
        }

        .brand h2 {
            font-size: 17px;
            margin-bottom: 4px;
        }

        .brand p {
            font-size: 12px;
            color: #aaa5bb;
        }

        .menu-heading {
            color: #9993aa;
            font-size: 11px;
            letter-spacing: 1px;
            padding: 0 13px;
            margin-bottom: 13px;
        }

        .sidebar-nav {
            display: flex;
            flex-direction: column;
            gap: 7px;
        }

        .sidebar-nav a {
            display: flex;
            align-items: center;
            gap: 13px;
            padding: 12px 13px;
            border-radius: 7px;
            color: #cfcbda;
            text-decoration: none;
            font-size: 14px;
            transition: 0.25s;
        }

        .nav-icon {
            width: 20px;
            text-align: center;
            font-size: 17px;
        }

        .sidebar-nav a:hover {
            color: white;
            background: #302a43;
            transform: translateX(4px);
        }

        .sidebar-nav a.active {
            background: #6558e8;
            color: white;
        }

        .sidebar-bottom {
            margin-top: auto;
        }

        .sidebar-divider {
            border: none;
            border-top: 1px solid #383247;
            margin: 18px 5px;
        }

        .logout-link {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 13px;
            border-radius: 7px;
            color: #e9a2a2;
            font-size: 14px;
            text-decoration: none;
            transition: 0.25s;
        }

        .logout-link:hover {
            background: #392b3a;
            color: #ffc0c0;
        }

        /* MAIN CONTENT */

        .main {
            margin-left: 235px;
            min-height: 100vh;
            padding: 30px 35px;
        }

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 15px;
            margin-bottom: 28px;
        }

        .page-title {
            font-size: 25px;
            color: #29253b;
            margin-bottom: 7px;
        }

        .welcome-text {
            color: #898496;
            font-size: 13px;
        }

        /* PROFILE DROPDOWN */

        .profile-menu {
            position: relative;
        }

        .profile-trigger {
            list-style: none;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 9px;
            background: white;
            border: 1px solid #e9e6f0;
            padding: 10px 14px;
            border-radius: 8px;
            font-size: 13px;
            color: #494458;
            transition: 0.25s;
        }

        .profile-trigger::-webkit-details-marker {
            display: none;
        }

        .profile-trigger:hover {
            border-color: #6558e8;
            color: #6558e8;
        }

        .profile-dropdown {
            position: absolute;
            right: 0;
            top: 45px;
            width: 170px;
            background: white;
            border: 1px solid #e9e6f0;
            border-radius: 8px;
            box-shadow: 0 6px 18px rgba(30, 20, 50, 0.08);
            padding: 7px;
            z-index: 5;
        }

        .profile-dropdown a {
            display: block;
            text-decoration: none;
            color: #494458;
            padding: 10px;
            font-size: 13px;
            border-radius: 5px;
            transition: 0.2s;
        }

        .profile-dropdown a:hover {
            background: #f1effd;
            color: #6558e8;
        }

        /* WELCOME BANNER */

        .welcome-banner {
            background: #eeecfb;
            border: 1px solid #e3def8;
            border-radius: 9px;
            padding: 22px 24px;
            margin-bottom: 24px;
        }

        .welcome-banner h2 {
            color: #39334c;
            font-size: 18px;
            margin-bottom: 8px;
        }

        .welcome-banner p {
            color: #777286;
            font-size: 13px;
            line-height: 1.7;
        }

        /* STATISTICS */

        .stats {
            display: flex;
            flex-wrap: wrap;
            gap: 17px;
            margin-bottom: 27px;
        }

        .stat-card {
            flex: 1 1 170px;
            background: white;
            border: 1px solid #eae7f1;
            border-radius: 9px;
            padding: 21px;
            transition: 0.25s;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            border-color: #d7d0f8;
            box-shadow: 0 7px 18px rgba(40, 30, 65, 0.06);
        }

        .stat-card p {
            color: #898496;
            font-size: 13px;
            margin-bottom: 13px;
        }

        .stat-card h2 {
            color: #6558e8;
            font-size: 28px;
        }

        /* OVERVIEW PANELS */

        .overview {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .panel {
            flex: 1 1 390px;
            min-width: 0;
            background: white;
            border: 1px solid #eae7f1;
            border-radius: 9px;
            padding: 22px;
        }

        .panel-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            margin-bottom: 7px;
        }

        .panel-header h3 {
            font-size: 16px;
            color: #39334c;
        }

        .view-all {
            color: #6558e8;
            font-size: 12px;
            text-decoration: none;
            white-space: nowrap;
        }

        .view-all:hover {
            color: #4033b9;
            text-decoration: underline;
        }

        .panel-description {
            color: #9691a2;
            font-size: 12px;
            margin-bottom: 17px;
            line-height: 1.6;
        }

        /* RECENT ITEMS */

        .recent-item {
            padding: 14px 0;
            border-bottom: 1px solid #efedf4;
            transition: padding-left 0.2s;
        }

        .recent-item:last-child {
            border-bottom: none;
        }

        .recent-item:hover {
            padding-left: 5px;
        }

        .recent-item h4 {
            font-size: 13px;
            color: #39334c;
            margin-bottom: 7px;
        }

        .recent-item p {
            font-size: 11px;
            color: #898496;
            line-height: 1.7;
        }

        .status {
            display: inline-block;
            margin-top: 7px;
            padding: 5px 9px;
            border-radius: 5px;
            background: #f1effc;
            color: #6558e8;
            font-size: 10px;
        }

        .status-approved {
            background: #eaf7ef;
            color: #25814b;
        }

        .status-rejected {
            background: #fff0f0;
            color: #bd4545;
        }

        .status-needs-improvement {
            background: #fff5e3;
            color: #9b701f;
        }

        /* PROJECT PROGRESS */

        .project-progress {
            margin-top: 10px;
        }

        .progress-heading {
            display: flex;
            justify-content: space-between;
            font-size: 11px;
            color: #898496;
            margin-bottom: 7px;
        }

        .progress {
            width: 100%;
            height: 6px;
            border-radius: 10px;
            background: #eeebf4;
            overflow: hidden;
        }

        .progress-bar {
            height: 100%;
            background: #8174e9;
            border-radius: 10px;
        }

        .empty-message {
            color: #9691a2;
            font-size: 13px;
            line-height: 1.7;
            padding: 18px 0;
        }

        /* RESULT MESSAGES */

        .alert {
            padding: 12px 15px;
            border-radius: 7px;
            margin-bottom: 18px;
            font-size: 13px;
        }

        .alert-success {
            background: #eaf7ef;
            color: #25814b;
        }

        .alert-error {
            background: #fff0f0;
            color: #bd4545;
        }

        /* FOOTER */

        .footer {
            text-align: center;
            padding: 25px 5px 5px;
            color: #a09bac;
            font-size: 11px;
        }

        /* RESPONSIVE */

        @media (max-width: 950px) {

            .sidebar {
                width: 210px;
            }

            .main {
                margin-left: 210px;
                padding: 24px;
            }
        }

        @media (max-width: 650px) {

            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
            }

            .brand {
                padding-bottom: 22px;
            }

            .sidebar-nav {
                flex-direction: row;
                flex-wrap: wrap;
            }

            .sidebar-nav a {
                flex: 1 1 130px;
            }

            .sidebar-bottom {
                margin-top: 12px;
            }

            .main {
                margin-left: 0;
                padding: 18px;
            }

            .topbar {
                align-items: flex-start;
                flex-direction: column;
            }

            .profile-menu {
                align-self: flex-end;
            }

            .panel {
                flex-basis: 100%;
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
            <p>Mentor Portal</p>
        </div>

    </div>


    <div class="menu-heading">MAIN MENU</div>


    <nav class="sidebar-nav">

        <a href="${pageContext.request.contextPath}/mentor-dashboard"
           class="active">
            <span class="nav-icon">⌂</span>
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/review-ideas">
            <span class="nav-icon">✦</span>
            Review Ideas
        </a>

        <a href="${pageContext.request.contextPath}/approve-projects">
            <span class="nav-icon">▰</span>
            Approve Projects
        </a>

        <a href="${pageContext.request.contextPath}/my-projects">
            <span class="nav-icon">✓</span>
           My Projects
        </a>

        <a href="${pageContext.request.contextPath}/mentor-profile">
            <span class="nav-icon">♙</span>
            My Profile
        </a>

    </nav>


    <div class="sidebar-bottom">

        <hr class="sidebar-divider">

        <a href="${pageContext.request.contextPath}/logout"
           class="logout-link">

            <span class="nav-icon">⇥</span>
            Logout
        </a>
    </div>
</aside>

<!-- MAIN CONTENT -->

<main class="main">


    <!-- TOP BAR -->

    <header class="topbar">

        <div>

            <h1 class="page-title">Mentor Dashboard</h1>

            <p class="welcome-text">
                Welcome back,
                <strong>
                    <c:out value="${mentorName}" default="Mentor"/>
                </strong>!
                Here's your overview for today.
            </p>

        </div>


        <details class="profile-menu">

            <summary class="profile-trigger">
                ♙ Mentor Account ▼
            </summary>

            <div class="profile-dropdown">

                <a href="${pageContext.request.contextPath}/mentor-profile">
                    My Profile
                </a>

                <a href="${pageContext.request.contextPath}/logout">
                  ⇥ Logout
                </a>
            </div>
        </details>
    </header>

    <!-- WELCOME MESSAGE -->
    <section class="welcome-banner">
        <h2>Welcome to your Mentor Workspace!</h2>
        <p>]Guide student ideas, share your knowledge and monitor
            the progress of projects assigned to you.</p>
    </section>

    <!-- RESULT MESSAGES -->

    <c:if test="${param.result == 'idea-reviewed'}">
        <div class="alert alert-success">
            Idea review submitted successfully.
        </div>
    </c:if>

    <c:if test="${param.result == 'project-reviewed'}">
        <div class="alert alert-success">
            Project review submitted successfully.
        </div>
    </c:if>

    <c:if test="${param.result == 'invalid-review'}">
        <div class="alert alert-error">
            Unable to process the submitted review.
        </div>
    </c:if>



    <!-- STATISTICS -->

    <section class="stats">

        <div class="stat-card">

            <p>Ideas to Review</p>

            <h2>
                <c:out value="${ideasToReview}" default="0"/>
            </h2>

        </div>


        <div class="stat-card">

            <p>Ideas Reviewed</p>

            <h2>
                <c:out value="${ideasReviewed}" default="0"/>
            </h2>

        </div>


        <div class="stat-card">

            <p>My Assigned Projects</p>

            <h2>
                <c:out value="${assignedProjects}" default="0"/>
            </h2>

        </div>


        <div class="stat-card">

            <p>Project Reviews Due</p>

            <h2>
                <c:out value="${projectReviewsDue}" default="0"/>
            </h2>

        </div>

    </section>



    <!-- RECENT IDEAS AND PROJECTS -->

    <section class="overview">


        <!-- RECENT IDEAS -->

        <div class="panel">

            <div class="panel-header">

                <h3>Recent Ideas</h3>

                <a class="view-all"
                   href="${pageContext.request.contextPath}/mentor-ideas">
                    View All →
                </a>

            </div>

            <p class="panel-description">
                Recently submitted ideas waiting for your attention.
            </p>


            <c:choose>

                <c:when test="${empty recentIdeas}">

                    <p class="empty-message">
                        No new ideas are waiting for review.
                    </p>

                </c:when>


                <c:otherwise>

                    <c:forEach var="idea" items="${recentIdeas}">

                        <div class="recent-item">

                            <h4>
                                <c:out value="${idea.title}"/>
                            </h4>

                            <p>
                                By:
                                <c:out value="${idea.studentName}"/>
                            </p>

                            <p>
                                Category:
                                <c:out value="${idea.category}"
                                       default="Not available"/>
                            </p>

                            <span class="status">
                                <c:out value="${idea.status}"/>
                            </span>

                        </div>

                    </c:forEach>

                </c:otherwise>

            </c:choose>

        </div>



        <!-- MY PROJECTS -->

        <div class="panel">

            <div class="panel-header">

                <h3>My Assigned Projects</h3>

                <a class="view-all"
                   href="${pageContext.request.contextPath}/mentor-projects">
                    View All →
                </a>

            </div>

            <p class="panel-description">
                A quick look at the projects currently assigned to you.
            </p>


            <c:choose>

                <c:when test="${empty recentProjects}">

                    <p class="empty-message">
                        No projects have been assigned to you yet.
                    </p>

                </c:when>


                <c:otherwise>

                    <c:forEach var="project" items="${recentProjects}">

                        <div class="recent-item">

                            <h4>
                                <c:out value="${project.projectName}"/>
                            </h4>

                            <p>
                                Project Status:
                                <c:out value="${project.status}"/>
                            </p>

                            <div class="project-progress">

                                <div class="progress-heading">

                                    <span>Progress</span>

                                    <strong>
                                        <c:out value="${project.progress}"/>%
                                    </strong>

                                </div>

                                <div class="progress">

                                    <div class="progress-bar"
                                         style="width: ${project.progress}%;"></div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <!-- FOOTER -->
    <footer class="footer">
        Campus Innovation & Collaboration Hub
        <br>
        Innovate • Collaborate • Grow
    </footer>

</main>

</body>
</html>
