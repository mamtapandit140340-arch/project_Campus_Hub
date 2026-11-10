<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 31-10-2026
  Time: 00:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }

    String studentName =
            (String) session.getAttribute("userName");

    if (studentName == null || studentName.trim().isEmpty()) {
        studentName = "Student";
    }

    String contextPath = request.getContextPath();

    String avatarLetter =
            studentName.substring(0, 1).toUpperCase();
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Campus Hub | My Ideas</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --sidebar: #191526;
            --primary: #6558e8;
            --background: #f7f8fc;
            --text: #242236;
            --muted: #85869a;
            --border: #ececf3;
            --white: #ffffff;
            --green: #16805d;
            --yellow: #b77900;
            --red: #d64545;
            --blue: #3478c9;
        }

        body {
            font-family: Arial, Helvetica, sans-serif;
            background: var(--background);
            color: var(--text);
        }

        a {
            color: inherit;
            text-decoration: none;
        }

        /* =========================
           SIDEBAR
           ========================= */

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 238px;
            height: 100vh;

            background: var(--sidebar);
            color: white;

            padding: 24px 15px;

            display: flex;
            flex-direction: column;

            z-index: 10;
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

            display: flex;
            align-items: center;
            justify-content: center;

            background: var(--primary);
            border-radius: 12px;

            font-size: 22px;
        }

        .brand h2 {
            font-size: 18px;
        }

        .brand p {
            color: #aaa5c2;
            font-size: 11px;
            margin-top: 5px;
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

            transition: 0.2s ease;
        }

        .nav-link:hover {
            background: #302a45;
            color: white;
        }

        .nav-link.active {
            background: var(--primary);
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

        /* =========================
           MAIN
           ========================= */

        .main {
            margin-left: 238px;

            padding: 25px 30px 35px;

            min-height: 100vh;
        }

        /* =========================
           TOPBAR
           ========================= */

        .topbar {
            display: flex;
            justify-content: flex-end;
            align-items: center;

            margin-bottom: 35px;

            position: relative;
            z-index: 20;
        }

        .topbar-right {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .notification {
            width: 40px;
            height: 40px;

            border: 1px solid var(--border);
            border-radius: 11px;

            background: white;

            display: flex;
            align-items: center;
            justify-content: center;

            color: var(--primary);

            font-size: 19px;
        }

        .profile-menu {
            position: relative;
        }

        .profile-trigger {
            list-style: none;
            cursor: pointer;
        }

        .profile-trigger::-webkit-details-marker {
            display: none;
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
            color: var(--muted);
            font-size: 11px;
        }

        .avatar {
            width: 40px;
            height: 40px;

            border-radius: 50%;

            background: var(--primary);
            color: white;

            display: flex;
            align-items: center;
            justify-content: center;

            font-weight: bold;
        }

        /* PROFILE DROPDOWN */

        .profile-dropdown {
            position: absolute;

            top: calc(100% + 10px);
            right: 0;

            width: 250px;

            padding: 12px;

            background: white;

            border: 1px solid var(--border);
            border-radius: 14px;

            box-shadow:
                    0 12px 35px rgba(30, 25, 65, 0.13);

            visibility: hidden;
            opacity: 0;

            transform: translateY(-6px);

            pointer-events: none;

            transition: 0.18s ease;
        }

        .profile-menu[open] .profile-dropdown {
            visibility: visible;
            opacity: 1;

            transform: translateY(0);

            pointer-events: auto;
        }

        .profile-dropdown-heading {
            padding: 8px 6px 12px;

            margin-bottom: 7px;

            border-bottom: 1px solid var(--border);
        }

        .profile-dropdown-heading strong {
            display: block;

            font-size: 13px;

            margin-bottom: 5px;
        }

        .profile-dropdown-heading span {
            font-size: 11px;
            color: var(--muted);
        }

        .profile-dropdown a {
            display: flex;
            align-items: center;

            gap: 9px;

            padding: 11px 7px;

            border-radius: 7px;

            font-size: 12px;
        }

        .profile-dropdown a:hover {
            background: #f5f4ff;
        }

        .profile-dropdown .logout-menu {
            color: #e54848;
        }

        /* =========================
           PAGE HEADER
           ========================= */

        .breadcrumb {
            color: var(--muted);

            font-size: 12px;

            margin-bottom: 13px;
        }

        .breadcrumb a:hover {
            color: var(--primary);
        }

        .page-heading h1 {
            font-size: 28px;

            letter-spacing: -0.7px;

            margin-bottom: 10px;
        }

        .page-heading p {
            color: var(--muted);

            font-size: 13px;

            line-height: 1.7;
        }

        /* =========================
           HEADER ACTIONS
           ========================= */

        .page-actions {
            display: flex;

            justify-content: space-between;
            align-items: center;

            gap: 15px;

            margin: 28px 0 20px;
        }

        .page-actions h2 {
            font-size: 17px;
        }

        .idea-count {
            color: var(--muted);

            font-size: 11px;

            margin-left: 8px;
        }

        .primary-button {
            display: inline-block;

            background: var(--primary);

            color: white;

            padding: 11px 16px;

            border-radius: 8px;

            font-size: 12px;

            white-space: nowrap;

            transition: 0.2s ease;
        }

        .primary-button:hover {
            background: #5144d2;

            transform: translateY(-2px);
        }

        /* =========================
           IDEA GRID
           ========================= */

        .idea-grid {
            display: grid;

            grid-template-columns:
                repeat(3, minmax(0, 1fr));

            gap: 18px;
        }

        .idea-card {
            background: white;

            border: 1px solid var(--border);

            border-radius: 13px;

            padding: 20px;

            display: flex;
            flex-direction: column;

            min-width: 0;

            transition:
                transform 0.25s ease,
                box-shadow 0.25s ease;
        }

        .idea-card:hover {
            transform: translateY(-5px);

            box-shadow:
                0 10px 25px rgba(35, 28, 75, 0.08);
        }

        .idea-top {
            display: flex;

            justify-content: space-between;
            align-items: center;

            gap: 10px;

            margin-bottom: 17px;
        }

        .idea-icon {
            width: 45px;
            height: 45px;

            border-radius: 12px;

            background: #eeecff;

            display: flex;
            align-items: center;
            justify-content: center;

            font-size: 22px;
        }

        /* =========================
           STATUS
           ========================= */

        .status {
            padding: 6px 9px;

            border-radius: 6px;

            font-size: 10px;

            font-weight: bold;
        }

        .status-pending {
            background: #fff5d9;
            color: var(--yellow);
        }

        .status-under-review {
            background: #eaf3ff;
            color: var(--blue);
        }

        .status-approved {
            background: #e1f7ed;
            color: var(--green);
        }

        .status-rejected {
            background: #ffe7e7;
            color: var(--red);
        }

        .status-needs-improvement {
            background: #fff0df;
            color: #bd6814;
        }

        .status-default {
            background: #f0f0f5;
            color: #66677b;
        }

        /* =========================
           IDEA CONTENT
           ========================= */

        .idea-card h3 {
            font-size: 15px;

            line-height: 1.5;

            margin-bottom: 10px;
        }

        .idea-description {
            color: var(--muted);

            font-size: 12px;

            line-height: 1.7;

            margin-bottom: 16px;

            display: -webkit-box;

            -webkit-line-clamp: 3;

            -webkit-box-orient: vertical;

            overflow: hidden;

            flex: 1;
        }

        .idea-meta {
            display: flex;

            flex-wrap: wrap;

            gap: 7px;

            margin-bottom: 17px;
        }

        .meta-tag {
            background: #f4f4f8;

            color: #66677b;

            padding: 6px 8px;

            border-radius: 5px;

            font-size: 10px;
        }

        .idea-footer {
            border-top: 1px solid #f0f0f5;

            padding-top: 15px;

            display: flex;

            justify-content: space-between;

            align-items: center;

            gap: 10px;
        }

        .submitted-date {
            color: var(--muted);

            font-size: 10px;
        }

        .view-link {
            color: var(--primary);

            font-size: 11px;

            font-weight: bold;
        }

        .view-link:hover {
            text-decoration: underline;
        }

        /* =========================
           EMPTY STATE
           ========================= */

        .empty-state {
            grid-column: 1 / -1;

            background: white;

            border: 1px solid var(--border);

            border-radius: 14px;

            padding: 55px 20px;

            text-align: center;
        }

        .empty-icon {
            font-size: 42px;

            margin-bottom: 15px;
        }

        .empty-state h3 {
            font-size: 17px;

            margin-bottom: 8px;
        }

        .empty-state p {
            color: var(--muted);

            font-size: 12px;

            margin-bottom: 20px;
        }

        /* =========================
           FOOTER
           ========================= */

        .footer {
            text-align: center;

            color: var(--muted);

            font-size: 11px;

            padding-top: 35px;
        }

        /* =========================
           RESPONSIVE
           ========================= */

        @media (max-width: 1100px) {

            .idea-grid {
                grid-template-columns:
                    repeat(2, minmax(0, 1fr));
            }

            .main {
                padding: 22px 20px;
            }
        }

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

                margin: 0;

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

            .profile-dropdown {
                width:
                    min(250px, calc(100vw - 28px));
            }

            .page-heading h1 {
                font-size: 24px;
            }

            .page-actions {
                align-items: flex-start;

                flex-direction: column;
            }

            .idea-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<!-- =========================
     SIDEBAR
     ========================= -->

<aside class="sidebar">

    <div class="brand">

        <div class="brand-icon">
            🎓
        </div>

        <div>
            <h2>Campus Hub</h2>
            <p>Student Portal</p>
        </div>

    </div>

    <div class="nav-title">
        Workspace
    </div>

    <nav>

        <a href="<%= contextPath %>/student-dashboard"
           class="nav-link">

            <span class="nav-icon">▦</span>

            <span>Dashboard</span>

        </a>

        <a href="<%= contextPath %>/projects"
           class="nav-link">

            <span class="nav-icon">▣</span>

            <span>Projects</span>

        </a>

        <a href="<%= contextPath %>/ideas"
           class="nav-link active">

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

        <a href="<%= contextPath %>/student-profile.jsp"
           class="nav-link">

            <span class="nav-icon">♙</span>

            <span>My Profile</span>

        </a>

    </nav>

    <hr class="nav-divider">

    <div class="nav-title">
        Account
    </div>

    <nav>

        <a href="<%= contextPath %>/help.jsp"
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


<!-- =========================
     MAIN
     ========================= -->

<main class="main">

    <!-- TOPBAR -->

    <header class="topbar">

        <div class="topbar-right">

            <a href="<%= contextPath %>/collaboration-requests"
               class="notification"
               title="Collaboration requests">
                ♧</a>


            <details class="profile-menu">

                <summary class="profile-trigger">

                    <div class="profile">

                        <div class="profile-info">
                            <strong><%= studentName %></strong>
                            <span>Student</span>
                        </div>

                        <div class="avatar">
                            <%= avatarLetter %>
                        </div>
                    </div>
                </summary>

                <div class="profile-dropdown">
                    <div class="profile-dropdown-heading">
                        <strong><%= studentName %></strong>
                        <span>Student Account</span>
                    </div>

                    <a href="<%= contextPath %>/student-profile.jsp">
                        <span>♟</span>
                        <span>My Profile</span>
                    </a>

                    <a href="<%= contextPath %>/student-profile.jsp?mode=edit">
                        <span>✎</span>
                        <span>Edit Profile</span>
                    </a>

                    <a href="<%= contextPath %>/student-profile.jsp?mode=settings">
                        <span>⚙</span>
                        <span>Account Settings</span>
                    </a>

                    <a href="<%= contextPath %>/logout"
                       class="logout-menu">
                        <span>⇥</span>
                        <span>Logout</span>
                    </a>
                </div>
            </details>
        </div>
    </header>

    <!-- PAGE HEADER -->
    <section class="page-heading">
        <div class="breadcrumb">
            <a href="<%= contextPath %>/student-dashboard">
                Dashboard</a>
            / My Ideas
        </div>

        <h1>My Ideas</h1>

        <p>View and track the ideas you have submitted
            to the Campus Innovation and Collaboration Hub.</p>
    </section>

    <!-- ACTION HEADER -->

    <section class="page-actions">
        <div>
            <h2>Your Submitted Ideas
                <span class="idea-count">
                    <c:out value="${ideaCount}" default="0"/>
                    ideas
                </span>
            </h2>
        </div>

        <a href="<%= contextPath %>/idea-form"
           class="primary-button">
            + Submit New Idea
        </a>
    </section>


    <!-- IDEA GRID -->
    <section class="idea-grid">

        <!-- NO IDEAS -->
        <c:if test="${empty ideas}">
            <div class="empty-state">
                <div class="empty-icon">
                    💡
                </div>

                <h3>You haven't submitted any ideas yet </h3>

                <p>
                    Have an innovative solution for your campus?
                    Submit your first idea and share it with the community.
                </p>

                <a href="<%= contextPath %>/idea-form"
                   class="primary-button">
                    Submit Your First Idea
                </a>
            </div>
        </c:if>


        <!-- DYNAMIC IDEAS -->
        <c:forEach var="idea"
                   items="${ideas}">

            <article class="idea-card">

                <div class="idea-top">

                    <div class="idea-icon">
                        💡
                    </div>


                    <!-- STATUS -->

                    <c:choose>
                        <c:when test="${idea.status == 'PENDING'}">
                            <span class="status status-pending">
                                Pending
                            </span>
                        </c:when>

                        <c:when test="${idea.status == 'UNDER_REVIEW'}">
                            <span class="status status-under-review">
                                Under Review
                            </span>
                        </c:when>

                        <c:when test="${idea.status == 'APPROVED'}">
                            <span class="status status-approved">
                                Approved
                            </span>
                        </c:when>

                        <c:when test="${idea.status == 'REJECTED'}">
                           <span class="status status-rejected">
                                Rejected
                            </span>
                        </c:when>

                        <c:when test="${idea.status == 'NEEDS_IMPROVEMENT'}">
                            <span class="status status-needs-improvement">
                                Needs Improvement
                            </span>
                        </c:when>


                        <c:otherwise>
                            <span class="status status-default">
                                <c:out value="${idea.status}"/>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>


                <!-- TITLE -->
                <h3><c:out value="${idea.title}"/> </h3>

                <!-- DESCRIPTION -->
                <p class="idea-description">
                    <c:out value="${idea.problemStatement}"/>
                </p>

                <!-- META -->
                <div class="idea-meta">
                    <span class="meta-tag">
                        Category ID:
                        <c:out value="${idea.categoryId}"/>
                    </span>

                    <span class="meta-tag">
                        Idea #<c:out value="${idea.ideaId}"/>
                    </span>
                </div>

                <!-- FOOTER -->
                <div class="idea-footer">
                   <span class="submitted-date">
                        Submitted:
                       <c:out value="${idea.submittedAt}"/>
                    </span>

                    <a href="<%= contextPath %>/idea-details?id=${idea.ideaId}"
                       class="view-link">
                        View Details →
                    </a>
               </div>
            </article>
        </c:forEach>
    </section>

    <!-- FOOTER -->
    <footer class="footer">
        © 2026 Campus Innovation and Collaboration Hub
    </footer>
</main>

<!-- PROFILE DROPDOWN -->

<script>
    const profileMenu =
        document.querySelector(".profile-menu");
    document.addEventListener("click", function(event) {

        if (
            profileMenu.open &&
            !profileMenu.contains(event.target)
        ) {
            profileMenu.open = false;
        }
    });

    document.addEventListener("keydown", function(event) {

        if (
            event.key === "Escape" &&
            profileMenu.open
        ) {

            profileMenu.open = false;
            profileMenu
                .querySelector("summary")
                .focus();
        }
    });

</script>
</body>
</html>