<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 30-10-2026
  Time: 08:55
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String studentName = (String) session.getAttribute("userName");
    if (studentName == null || studentName.trim().isEmpty()) {
        studentName = "Student";
    }

    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Campus Hub | Projects</title>

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

        /* SIDEBAR */

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
            transition: background 0.2s ease,
                        color 0.2s ease,
                        transform 0.2s ease;
        }

        .nav-link:hover {
            background: #302a45;
            color: white;
            transform: translateX(3px);
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

        /* MAIN CONTENT */

        .main {
            margin-left: 238px;
            padding: 25px 30px 35px;
            min-height: 100vh;
        }

        .topbar {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-bottom: 35px;
        }

        .profile {
            position: relative;
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
            width: 38px;
            height: 38px;
            border-radius: 50%;
            background: var(--primary);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }

        /* ACCOUNT PROFILE DROPDOWN */

        .profile-menu-button {
            width: 40px;
            height: 40px;
            border: none;
            border-radius: 50%;
            background: var(--primary);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        .profile-dropdown {
            position: absolute;
            top: calc(100% + 15px);
            right: 0;
            width: 235px;
            padding: 10px;
            background: white;
            border: 1px solid var(--border);
            border-radius: 13px;
            box-shadow: 0 12px 30px rgba(35, 28, 75, 0.13);
            z-index: 30;
            display: none;
        }

        .profile-dropdown.show {
            display: block;
            animation: fadeIn 0.18s ease both;
        }

        .dropdown-user {
            padding: 7px 9px 12px;
            border-bottom: 1px solid var(--border);
            margin-bottom: 7px;
        }

        .dropdown-user strong {
            display: block;
            font-size: 13px;
            margin-bottom: 4px;
        }

        .dropdown-user span {
            color: var(--muted);
            font-size: 11px;
        }

        .dropdown-link {
            display: flex;
            align-items: center;
            gap: 9px;
            padding: 10px 9px;
            border-radius: 7px;
            color: #39384a;
            font-size: 12px;
        }

        .dropdown-link:hover {
            background: #f4f2ff;
            color: var(--primary);
        }

        .dropdown-link .menu-icon {
            width: 15px;
            text-align: center;
        }

        .dropdown-link.logout {
            color: #b94b55;
        }

        .dropdown-link.logout:hover {
            background: #fff1f1;
            color: #a8323e;
        }

        /* PAGE INTRODUCTION */

        .page-heading {
            animation: fadeIn 0.5s ease both;
        }

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

        /* FEATURE BANNER */

        .banner {
            margin: 25px 0;
            padding: 23px;
            border: 1px solid #e9e5ff;
            border-radius: 14px;
            background: linear-gradient(110deg, #ffffff, #f0eeff);
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 18px;
            animation: fadeIn 0.6s ease both;
        }

        .banner h2 {
            font-size: 17px;
            margin-bottom: 8px;
        }

        .banner p {
            color: #74758a;
            font-size: 12px;
            line-height: 1.7;
            max-width: 520px;
        }

        .primary-button {
            display: inline-block;
            background: var(--primary);
            color: white;
            padding: 11px 16px;
            border: none;
            border-radius: 8px;
            font-size: 12px;
            white-space: nowrap;
            transition: background 0.2s ease,
                        transform 0.2s ease,
                        box-shadow 0.2s ease;
        }

        .primary-button:hover {
            background: #5144d2;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px #6558e833;
        }

        /* FILTERS */

        .section-heading {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 12px;
            margin: 28px 0 17px;
        }

        .section-heading h2 {
            font-size: 17px;
        }

        .project-count {
            font-size: 11px;
            color: var(--muted);
        }

        .filter-bar {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 20px;
        }

        .filter-button {
            padding: 9px 14px;
            border: 1px solid var(--border);
            background: white;
            color: #66677b;
            border-radius: 8px;
            font-size: 12px;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .filter-button:hover,
        .filter-button.active {
            background: var(--primary);
            border-color: var(--primary);
            color: white;
        }

        /* PROJECT CARDS */

        .project-grid {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 18px;
        }

        .project-card {
            background: white;
            border: 1px solid var(--border);
            border-radius: 13px;
            padding: 20px;
            display: flex;
            flex-direction: column;
            min-width: 0;
            animation: fadeIn 0.5s ease both;
            transition: transform 0.25s ease,
                        box-shadow 0.25s ease,
                        border-color 0.25s ease;
        }

        .project-card:nth-child(2) {
            animation-delay: 0.08s;
        }

        .project-card:nth-child(3) {
            animation-delay: 0.16s;
        }

        .project-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(35, 28, 75, 0.08);
            border-color: #d9d3ff;
        }

        .project-card-top {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            margin-bottom: 17px;
        }

        .project-icon {
            width: 45px;
            height: 45px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 12px;
            background: #eeecff;
            font-size: 22px;
            transition: transform 0.2s ease;
        }

        .project-card:hover .project-icon {
            transform: scale(1.08);
        }

        .category {
            font-size: 10px;
            color: #6558e8;
            background: #eeecff;
            padding: 6px 8px;
            border-radius: 6px;
        }

        .project-card h3 {
            font-size: 15px;
            margin-bottom: 10px;
            line-height: 1.5;
        }

        .description {
            color: var(--muted);
            font-size: 12px;
            line-height: 1.7;
            margin-bottom: 17px;
            flex: 1;
        }

        .skill-list {
            display: flex;
            flex-wrap: wrap;
            gap: 7px;
            margin-bottom: 18px;
        }

        .skill {
            font-size: 10px;
            padding: 6px 8px;
            background: #f4f4f8;
            border-radius: 5px;
            color: #66677b;
        }

        .project-footer {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            border-top: 1px solid #f0f0f5;
            padding-top: 15px;
        }

        .team-info {
            color: var(--muted);
            font-size: 11px;
        }

        .details-link {
            color: var(--primary);
            font-size: 11px;
            font-weight: bold;
        }

        .details-link:hover {
            text-decoration: underline;
        }

        .empty-message {
            display: none;
            grid-column: 1 / -1;
            text-align: center;
            padding: 35px 15px;
            color: var(--muted);
            background: white;
            border: 1px solid var(--border);
            border-radius: 12px;
            font-size: 13px;
        }

        .footer {
            text-align: center;
            color: var(--muted);
            font-size: 11px;
            padding-top: 35px;
        }

        /* SIMPLE ENTRANCE ANIMATION */

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* RESPONSIVE LAYOUT */

        @media (max-width: 1100px) {
            .project-grid {
                grid-template-columns: repeat(2, minmax(0, 1fr));
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

            .topbar {
                margin-bottom: 25px;
            }

            .profile-info {
                display: none;
            }

            .page-heading h1 {
                font-size: 24px;
            }

            .banner {
                align-items: flex-start;
                flex-direction: column;
                padding: 18px;
            }

            .project-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (prefers-reduced-motion: reduce) {
            *,
            *::before,
            *::after {
                animation: none !important;
                transition: none !important;
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
        <a href="<%= contextPath %>/student-dashboard.jsp" class="nav-link">
            <span class="nav-icon">▦</span>
            <span>Dashboard</span>
        </a>

        <a href="<%= contextPath %>/projects"
           class="nav-link active">
            <span class="nav-icon">▣</span>
            <span>Projects</span>
        </a>

        <a href="<%= contextPath %>/ideas.jsp" class="nav-link">
            <span class="nav-icon">✦</span>
            <span>My Ideas</span>
        </a>

        <a href="<%= contextPath %>/collaboration-requests"
           class="nav-link">
            <span class="nav-icon">⇄</span>
            <span>Collaboration</span>
        </a>

        <a href="<%= contextPath %>/skills.jsp" class="nav-link">
            <span class="nav-icon">◇</span>
            <span>My Skills</span>
        </a>

        <a href="<%= contextPath %>/student-profile.jsp" class="nav-link">
            <span class="nav-icon">♙</span>
            <span>My Profile</span>
        </a>
    </nav>

    <hr class="nav-divider">

    <div class="nav-title">Account</div>

    <nav>
        <a href="<%= contextPath %>/help.jsp" class="nav-link">
            <span class="nav-icon">?</span>
            <span>Help & Support</span>
        </a>
    </nav>

    <div class="sidebar-bottom">
        <hr class="nav-divider">
        <a href="<%= contextPath %>/logout" class="nav-link logout-link">
            <span class="nav-icon">⇥</span>
            <span>Logout</span>
        </a>
    </div>

</aside>

<!-- MAIN CONTENT -->
<main class="main">

    <header class="topbar">
        <div class="profile">

            <button type="button"
                    class="profile-menu-button"
                    id="profileMenuButton"
                    aria-label="Open account menu"
                    aria-haspopup="true"
                    aria-expanded="false"
                    aria-controls="profileDropdown">

                <%= studentName.substring(0, 1).toLowerCase() %>

            </button>

            <div class="profile-dropdown"
                 id="profileDropdown"
                 role="menu"
                 aria-labelledby="profileMenuButton">

                <div class="dropdown-user">
                    <strong><%= studentName %></strong>
                    <span>Student Account</span>
                </div>

                <a href="<%= contextPath %>/student-profile.jsp"
                   class="dropdown-link"
                   role="menuitem">

                    <span class="menu-icon">♟</span>
                    <span>My Profile</span>
                </a>

                <a href="<%= contextPath %>/edit-profile.jsp"
                   class="dropdown-link"
                   role="menuitem">

                    <span class="menu-icon">✎</span>
                    <span>Edit Profile</span>
                </a>

                <a href="<%= contextPath %>/account-settings.jsp"
                   class="dropdown-link"
                   role="menuitem">

                    <span class="menu-icon">⚙</span>
                    <span>Account Settings</span>
                </a>

                <a href="<%= contextPath %>/logout"
                   class="dropdown-link logout"
                   role="menuitem">

                    <span class="menu-icon">⇥</span>
                    <span>Logout</span>
                </a>

            </div>
        </div>
    </header>

    <!-- PAGE HEADING -->
    <section class="page-heading">
        <div class="breadcrumb">
            <a href="<%= contextPath %>/student-dashboard">Dashboard</a>
            / Projects
        </div>

        <h1>Explore Projects</h1>

        <p>
            Discover innovative projects, share your skills, and find
            teammates to turn ideas into reality.
        </p>
    </section>

    <!-- BANNER -->
    <section class="banner">
        <div>
            <h2>Have an idea worth building?</h2>
            <p>
                Start a project, bring together like-minded students,
                and build something meaningful with your campus community.
            </p>
        </div>

        <a href="<%= contextPath %>/project-form.jsp"
           class="primary-button">
            + Create Project
        </a>
    </section>

<!-- PROJECT FILTERS -->

<div class="section-heading">

    <h2>Discover Projects</h2>

    <span class="project-count">
        <c:out value="${projectCount}" default="0"/>
        projects
    </span>

</div>


<div class="filter-bar">

    <button type="button"
            class="filter-button active"
            data-category="all">
        All Projects
    </button>

    <button type="button"
            class="filter-button"
            data-category="Web Development">
        Web Development
    </button>

    <button type="button"
            class="filter-button"
            data-category="Artificial Intelligence">
        AI &amp; ML
    </button>

    <button type="button"
            class="filter-button"
            data-category="Environment">
        Environment
    </button>

</div>


<!-- PROJECT CARDS -->

<section class="project-grid">

    <c:choose>

        <c:when test="${empty projects}">

            <div class="empty-message"
                 style="display:block;">

                No projects are available yet.

            </div>

        </c:when>


        <c:otherwise>

            <c:forEach var="project"
                       items="${projects}">

                <article class="project-card"
                         data-category="<c:out value='${project.category_name}'/>">

                    <div class="project-card-top">

                        <div class="project-icon">
                            ▣
                        </div>

                        <span class="category">

                            <c:out
                                    value="${project.category_name}"
                                    default="Uncategorized"/>
                        </span>
                    </div>

                    <h3><c:out value="${project.project_name}"/></h3>

                    <p class="description">
                        <c:out value="${project.description}"
                                default="No description available."/></p>


                    <div class="skill-list">
                        <span class="skill">
                            Status:
                            <c:out value="${project.status}" default="Unknown"/>
                        </span>

                        <span class="skill">
                            Progress:
                            <c:out
                                    value="${project.progress_percentage}"
                                    default="0"/>%
                        </span>
                    </div>

                    <div class="project-footer">
                        <span class="team-info">
                            Project #<c:out value="${project.project_id}"/></span>

                        <a href="<%= contextPath %>/collaboration-requests"
                           class="details-link">
                            Collaborate →
                        </a>
                    </div>
                </article>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <div class="empty-message"
         id="emptyMessage">No projects found in this category.</div>
</section>
                    <footer class="footer">
                        © 2026 Campus Innovation and Collaboration Hub
                    </footer>

                </main>

                <!-- ACCOUNT PROFILE DROPDOWN -->
                <script>

                    const profileMenuButton =
                        document.getElementById("profileMenuButton");

                    const profileDropdown =
                        document.getElementById("profileDropdown");

                    // Open and close dropdown
                    profileMenuButton.addEventListener("click", function(event) {

                        event.stopPropagation();

                        const isOpen =
                            profileDropdown.classList.toggle("show");

                        profileMenuButton.setAttribute(
                            "aria-expanded",
                            String(isOpen)
                        );
                    });

                    // Prevent closing when clicking inside dropdown
                    profileDropdown.addEventListener("click", function(event) {
                        event.stopPropagation();
                    });

                    // Close when clicking outside
                    document.addEventListener("click", function() {

                        profileDropdown.classList.remove("show");

                        profileMenuButton.setAttribute(
                            "aria-expanded",
                            "false"
                        );
                    });

                    // Close using Escape key
                    document.addEventListener("keydown", function(event) {

                        if (event.key === "Escape") {

                            profileDropdown.classList.remove("show");

                            profileMenuButton.setAttribute(
                                "aria-expanded",
                                "false"
                            );
                        }
                    });

                </script>

                <!-- SIMPLE CATEGORY FILTER -->
                <script>

                    const filterButtons =
                        document.querySelectorAll(".filter-button");

                    const projectCards =
                        document.querySelectorAll(".project-card");

                    const emptyMessage =
                        document.getElementById("emptyMessage");


                    function filterProjects(selectedCategory) {

                        let visibleProjects = 0;


                        projectCards.forEach(function(card) {

                            const cardCategory =
                                card.dataset.category
                                    .trim()
                                    .toLowerCase();

                            const selected =
                                selectedCategory
                                    .trim()
                                    .toLowerCase();


                            const matches =
                                selected === "all" ||
                                cardCategory === selected;


                            if (matches) {

                                card.style.display = "flex";

                                visibleProjects++;

                            } else {

                                card.style.display = "none";

                            }

                        });


                        if (emptyMessage) {

                            emptyMessage.style.display =
                                visibleProjects === 0
                                    ? "block"
                                    : "none";

                        }


                        const projectCount =
                            document.querySelector(".project-count");


                        if (projectCount) {

                            projectCount.textContent =
                                visibleProjects +
                                (
                                    visibleProjects === 1
                                        ? " project"
                                        : " projects"
                                );

                        }

                    }


                    filterButtons.forEach(function(button) {

                        button.addEventListener(
                            "click",
                            function() {

                                filterButtons.forEach(function(item) {

                                    item.classList.remove("active");

                                });


                                button.classList.add("active");


                                filterProjects(
                                    button.dataset.category
                                );

                            }
                        );

                    });


                    /*
                     * Reset when the page is restored from
                     * browser back/forward cache.
                     */

                    window.addEventListener("pageshow", function() {
                            filterButtons.forEach(function(button) {button.classList.remove("active");
                            });

                            const allButton =
                                document.querySelector('.filter-button[data-category="all"]');

                            if (allButton) {
                                allButton.classList.add("active");
                            }
                            filterProjects("all");
                        }
                    );

                </script>
                </body>
                </html>