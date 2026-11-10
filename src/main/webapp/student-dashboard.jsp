<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="displayName" value="${sessionScope.userName}" />

<c:if test="${empty displayName}">
    <c:set var="displayName" value="Student" />
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Campus Hub | Student Dashboard</title>

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
            text-decoration: none;
            color: inherit;
        }

        /* SIDEBAR */

        .sidebar {
            position: fixed;
            width: 238px;
            height: 100vh;
            background: var(--sidebar);
            color: white;
            padding: 24px 15px;
            display: flex;
            flex-direction: column;
            top: 0;
            left: 0;
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
            display: flex;
            align-items: center;
            justify-content: center;
            background: var(--primary);
            border-radius: 12px;
            font-size: 23px;
        }

        .brand h2 { font-size: 18px; }

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

        .sidebar-bottom { margin-top: auto; }
        .logout-link { color: #ffb4b4; }

        /* MAIN */

        .main {
            margin-left: 238px;
            min-height: 100vh;
            padding: 25px 30px 35px;
        }

        .topbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 32px;
        }

        .topbar-right {
            display: flex;
            align-items: center;
            gap: 17px;
            margin-left: auto;
        }

        .notification {
            width: 38px;
            height: 38px;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 1px solid var(--border);
            border-radius: 10px;
            background: white;
        }

        /* PROFILE DROPDOWN */

        .profile-menu { position: relative; }

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
            gap: 10px;
            padding: 5px 8px;
            border-radius: 9px;
        }

        .profile-trigger:hover { background: #eeecff; }

        .profile-info { text-align: right; }

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

        .profile-dropdown {
            position: absolute;
            right: 0;
            top: calc(100% + 10px);
            width: 215px;
            padding: 8px;
            background: white;
            border: 1px solid var(--border);
            border-radius: 11px;
            box-shadow: 0 12px 30px rgba(30,25,55,0.13);
            z-index: 200;
        }

        .profile-dropdown-heading {
            padding: 10px;
            border-bottom: 1px solid var(--border);
            margin-bottom: 6px;
        }

        .profile-dropdown-heading strong,
        .profile-dropdown-heading span {
            display: block;
        }

        .profile-dropdown-heading strong { font-size: 12px; }

        .profile-dropdown-heading span {
            font-size: 10px;
            color: var(--muted);
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
            color: var(--primary);
        }

        .profile-dropdown .logout-menu { color: #c03939; }

        /* WELCOME */

        .welcome h1 {
            font-size: 27px;
            margin-bottom: 8px;
        }

        .welcome p {
            font-size: 13px;
            color: var(--muted);
            line-height: 1.6;
        }

        /* HIGHLIGHT */

        .highlight {
            display: flex;
            align-items: flex-start;
            gap: 17px;
            padding: 23px;
            margin: 25px 0;
            background: linear-gradient(110deg, white, #f1efff);
            border: 1px solid #eeecff;
            border-radius: 14px;
        }

        .highlight-icon {
            min-width: 45px;
            height: 45px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: var(--primary);
            color: white;
            border-radius: 12px;
            font-size: 21px;
        }

        .highlight-content { flex: 1; }

        .highlight h3 {
            font-size: 15px;
            margin-bottom: 9px;
        }

        .highlight p {
            font-size: 12px;
            line-height: 1.7;
            color: #74758a;
        }

        .highlight-tags {
            display: flex;
            flex-wrap: wrap;
            gap: 9px;
            margin-top: 14px;
        }

        .tag {
            display: inline-block;
            padding: 8px 11px;
            background: #e9edff;
            color: #4267ad;
            border-radius: 6px;
            font-size: 11px;
            transition: 0.2s;
        }

        .tag.green {
            background: #e5f7ee;
            color: #16805d;
        }

        .tag:hover { transform: translateY(-2px); }

        /* STATISTICS */

        .stats-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 17px;
            margin-bottom: 27px;
        }

        .stat-card {
            flex: 1 1 180px;
            background: white;
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 19px;
            min-width: 0;
            transition: 0.2s;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 22px rgba(35,28,75,0.08);
        }

        .stat-top {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 8px;
            margin-bottom: 20px;
        }

        .stat-label {
            font-size: 12px;
            color: #77788d;
        }

        .stat-icon {
            width: 39px;
            height: 39px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 10px;
            font-size: 18px;
        }

        .purple {
            background: #ece9ff;
            color: #6558e8;
        }

        .yellow {
            background: #fff1d6;
            color: #d38a08;
        }

        .green-bg {
            background: #e1f7ec;
            color: #159b75;
        }

        .blue-bg {
            background: #e3efff;
            color: #3478d4;
        }

        .stat-number {
            font-size: 27px;
            font-weight: bold;
            margin-bottom: 7px;
        }

        .stat-note {
            font-size: 11px;
            color: var(--muted);
        }

        /* PANELS */

        .content-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 22px;
        }

        .panel {
            flex: 1 1 330px;
            min-width: 0;
            background: white;
            border: 1px solid var(--border);
            border-radius: 13px;
            padding: 21px;
        }

        .panel-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            margin-bottom: 20px;
        }

        .panel-header h3 { font-size: 15px; }

        .view-all {
            color: var(--primary);
            font-size: 11px;
            font-weight: bold;
            white-space: nowrap;
        }

        .view-all:hover { text-decoration: underline; }

        /* PROJECT / IDEA ROWS */

        .project-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 13px 0;
            border-bottom: 1px solid #f0f0f5;
            transition: 0.2s;
        }

        .project-item:last-child { border-bottom: none; }

        .project-item:hover {
            background: #faf9ff;
            padding-left: 8px;
            padding-right: 8px;
        }

        .project-icon {
            width: 42px;
            height: 42px;
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #eeecff;
            color: var(--primary);
            border-radius: 10px;
            font-size: 19px;
        }

        .project-info {
            flex: 1;
            min-width: 0;
        }

        .project-info h4 {
            font-size: 12px;
            margin-bottom: 6px;
        }

        .project-info p {
            font-size: 11px;
            color: var(--muted);
        }

        .project-title-link:hover {
            color: var(--primary);
            text-decoration: underline;
        }

        .status {
            display: inline-block;
            font-size: 10px;
            padding: 6px 8px;
            background: #e5f7ee;
            color: #16805d;
            border-radius: 6px;
            white-space: nowrap;
        }

        .status.pending {
            background: #fff2d9;
            color: #a66a00;
        }

        .empty-state {
            text-align: center;
            padding: 25px 10px;
            color: var(--muted);
        }

        .empty-icon {
            font-size: 30px;
            margin-bottom: 12px;
        }

        .empty-state p {
            font-size: 12px;
            line-height: 1.6;
        }

        .empty-action {
            display: inline-block;
            margin-top: 12px;
            color: var(--primary);
            font-size: 12px;
            font-weight: bold;
        }

        /* LOWER PANELS */

        .lower-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .quick-links {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
        }

        .quick-link {
            flex: 1 1 140px;
            padding: 15px;
            border: 1px solid var(--border);
            border-radius: 10px;
            transition: 0.2s;
        }

        .quick-link:hover {
            transform: translateY(-3px);
            border-color: #c9c2ff;
            background: #fcfbff;
        }

        .quick-link-icon {
            font-size: 21px;
            margin-bottom: 11px;
        }

        .quick-link h4 {
            font-size: 12px;
            margin-bottom: 6px;
        }

        .quick-link p {
            font-size: 11px;
            color: var(--muted);
            line-height: 1.5;
        }

        /* COLLABORATION */

        .mentor-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 0;
            border-bottom: 1px solid #f0f0f5;
        }

        .mentor-item:last-child { border-bottom: none; }

        .mentor-avatar {
            width: 39px;
            height: 39px;
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #dff5eb;
            color: #16805d;
            border-radius: 50%;
            font-weight: bold;
        }

        .mentor-info {
            flex: 1;
            min-width: 0;
        }

        .mentor-info h4 {
            font-size: 12px;
            margin-bottom: 5px;
        }

        .mentor-info p {
            font-size: 11px;
            color: var(--muted);
            margin-top: 3px;
        }

        .mentor-action {
            color: var(--primary);
            font-size: 11px;
        }

        .footer {
            padding: 25px 0 0;
            text-align: center;
            color: var(--muted);
            font-size: 11px;
        }

        /* RESPONSIVE */

        @media (max-width: 700px) {
            .sidebar {
                width: 100%;
                height: auto;
                position: relative;
                padding: 15px;
            }

            .brand { padding-bottom: 12px; }

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

            .nav-link span:last-child { display: none; }

            .main {
                margin-left: 0;
                padding: 20px 14px;
            }

            .welcome h1 { font-size: 23px; }

            .profile-info { display: none; }

            .highlight { padding: 16px; }

            .panel { padding: 16px; }
        }
    </style>
</head>

<body>

<div class="layout">

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
            <a href="${pageContext.request.contextPath}/student-dashboard"
               class="nav-link active">
                <span class="nav-icon">▦</span>
                <span>Dashboard</span>
            </a>

            <a href="${pageContext.request.contextPath}/projects" class="nav-link">
                <span class="nav-icon">▣</span>
                <span>Projects</span>
            </a>

            <a href="${pageContext.request.contextPath}/ideas" class="nav-link">
                <span class="nav-icon">✦</span>
                <span>My Ideas</span>
            </a>

            <a href="${pageContext.request.contextPath}/collaboration-requests"
               class="nav-link">
                <span class="nav-icon">⇄</span>
                <span>Collaboration</span>
            </a>

            <a href="${pageContext.request.contextPath}/skills" class="nav-link">
                <span class="nav-icon">◇</span>
                <span>My Skills</span>
            </a>

            <a href="${pageContext.request.contextPath}/student-profile"
               class="nav-link">
                <span class="nav-icon">♙</span>
                <span>My Profile</span>
            </a>
        </nav>

        <hr class="nav-divider">

        <div class="nav-title">Account</div>

        <nav>
            <a href="${pageContext.request.contextPath}/student-profile"
               class="nav-link">
                <span class="nav-icon">⚙</span>
                <span>Profile & Settings</span>
            </a>

            <a href="${pageContext.request.contextPath}/help" class="nav-link">
                <span class="nav-icon">?</span>
                <span>Help & Support</span>
            </a>
        </nav>

        <div class="sidebar-bottom">
            <hr class="nav-divider">

            <a href="${pageContext.request.contextPath}/logout"
               class="nav-link logout-link">
                <span class="nav-icon">⇥</span>
                <span>Logout</span>
            </a>
        </div>

    </aside>

    <!-- MAIN DASHBOARD -->

    <main class="main">

        <header class="topbar">
            <div class="topbar-right">

                <a href="${pageContext.request.contextPath}/collaboration-requests"
                   class="notification"
                   title="Collaboration requests">
                    ♧
                </a>

                <details class="profile-menu">
                    <summary class="profile-trigger">

                        <div class="profile">
                            <div class="profile-info">
                                <strong><c:out value="${displayName}"/></strong>
                                <span>Student</span>
                            </div>

                            <div class="avatar">
                                <c:out value="${fn:substring(displayName, 0, 1)}"/>
                            </div>
                        </div>

                    </summary>

                    <div class="profile-dropdown">

                        <div class="profile-dropdown-heading">
                            <strong><c:out value="${displayName}"/></strong>
                            <span>Student Account</span>
                        </div>

                        <a href="${pageContext.request.contextPath}/student-profile">
                            👤 My Profile
                        </a>

                        <a href="${pageContext.request.contextPath}/student-profile?mode=edit">
                            ✎ Edit Profile
                        </a>

                        <a href="${pageContext.request.contextPath}/student-profile?mode=settings">
                            ⚙ Account Settings
                        </a>

                        <a href="${pageContext.request.contextPath}/logout"
                           class="logout-menu">
                            ⇥ Logout
                        </a>
                    </div>
                </details>
            </div>
        </header>

        <!-- WELCOME -->

        <section class="welcome">
            <h1>
                Welcome back, <c:out value="${displayName}"/>!
            </h1>

            <p>
                Here's an overview of your campus projects and
                collaboration activities.
            </p>
        </section>

        <!-- INNOVATION SPACE -->

        <section class="highlight">

            <div class="highlight-icon">✦</div>

            <div class="highlight-content">
                <h3>Your Innovation Space</h3>

                <p>
                    Have an idea that could make a difference?
                    Share your ideas, discover projects, and connect
                    with students who can help turn your ideas into reality.
                </p>

                <div class="highlight-tags">
                    <a class="tag"
                       href="${pageContext.request.contextPath}/idea-form">
                        💡 Share an Idea
                    </a>

                    <a class="tag green"
                       href="${pageContext.request.contextPath}/collaboration-requests">
                        🤝 Collaborate with Students
                    </a>
                </div>
            </div>
        </section>

        <!-- DYNAMIC STATISTICS -->

        <section class="stats-grid">

            <div class="stat-card">
                <div class="stat-top">
                    <span class="stat-label">My Projects</span>
                    <div class="stat-icon purple">▣</div>
                </div>

                <div class="stat-number">
                    <c:out value="${myProjectsCount}" default="0"/>
                </div>

                <div class="stat-note">
                    Projects created or joined by you
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span class="stat-label">Ideas Submitted</span>
                    <div class="stat-icon yellow">✦</div>
                </div>

                <div class="stat-number">
                    <c:out value="${myIdeasCount}" default="0"/>
                </div>

                <div class="stat-note">
                    Ideas submitted by you
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span class="stat-label">Collaborations</span>
                    <div class="stat-icon green-bg">⇄</div>
                </div>

                <div class="stat-number">
                    <c:out value="${acceptedCollaborationsCount}" default="0"/>
                </div>

                <div class="stat-note">
                    Accepted collaboration requests
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span class="stat-label">Pending Requests</span>
                    <div class="stat-icon blue-bg">✉</div>
                </div>

                <div class="stat-number">
                    <c:out value="${pendingRequestsCount}" default="0"/>
                </div>

                <div class="stat-note">
                    Requests awaiting your response
                </div>
            </div>

        </section>

        <!-- RECENT PROJECTS AND IDEAS -->

        <section class="content-grid">

            <!-- MY RECENT PROJECTS -->

            <div class="panel">

                <div class="panel-header">
                    <h3>My Recent Projects</h3>

                    <a href="${pageContext.request.contextPath}/projects"
                       class="view-all">
                        View All →
                    </a>
                </div>

                <c:choose>

                    <c:when test="${empty recentProjects}">
                        <div class="empty-state">
                            <div class="empty-icon">▣</div>

                            <p>
                                You have not created or joined any projects yet.
                            </p>

                            <a href="${pageContext.request.contextPath}/projects"
                               class="empty-action">
                                Explore Projects →
                            </a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${recentProjects}" var="project">

                            <div class="project-item">

                                <div class="project-icon">▣</div>

                                <div class="project-info">
                                    <h4>
                                        <a class="project-title-link"
                                           href="${pageContext.request.contextPath}/projects?projectId=${project.project_id}">
                                            <c:out value="${project.project_name}"/>
                                        </a>
                                    </h4>

                                    <p>
                                        <c:out value="${project.category_name}"
                                               default="Uncategorized"/>
                                    </p>
                                </div>

                                <span class="status">
                                    <c:out value="${project.status}"
                                           default="UNKNOWN"/>
                                </span>

                            </div>

                        </c:forEach>
                    </c:otherwise>

                </c:choose>

            </div>

            <!-- MY RECENT IDEAS -->

            <div class="panel">

                <div class="panel-header">
                    <h3>My Recent Ideas</h3>

                    <a href="${pageContext.request.contextPath}/ideas"
                       class="view-all">
                        View All →
                    </a>
                </div>

                <c:choose>

                    <c:when test="${empty recentIdeas}">
                        <div class="empty-state">
                            <div class="empty-icon">✦</div>

                            <p>
                                You have not submitted any ideas yet.
                            </p>

                            <a href="${pageContext.request.contextPath}/idea-form"
                               class="empty-action">
                                Share Your First Idea →
                            </a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${recentIdeas}" var="idea">

                            <div class="project-item">

                                <div class="project-icon">✦</div>

                                <div class="project-info">
                                    <h4>
                                        <c:out value="${idea.title}"/>
                                    </h4>

                                    <p>
                                        <c:out value="${idea.category_name}"
                                               default="Uncategorized"/>
                                    </p>
                                </div>

                                <span class="status">
                                    <c:out value="${idea.status}"
                                           default="PENDING"/>
                                </span>

                            </div>

                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <!-- QUICK ACTIONS AND COLLABORATION -->
        <section class="lower-grid">
            <!-- QUICK ACTIONS -->
            <div class="panel">
                <div class="panel-header">
                    <h3>Quick Actions</h3>
                </div>

                <div class="quick-links">

                    <a href="${pageContext.request.contextPath}/idea-form"
                       class="quick-link">
                        <div class="quick-link-icon">💡</div>
                        <h4>Share an Idea</h4>
                        <p>Submit your innovative idea.</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/ideas"
                       class="quick-link">
                        <div class="quick-link-icon">✦</div>
                        <h4>My Ideas</h4>
                        <p>View and track your submitted ideas.</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/projects"
                       class="quick-link">
                        <div class="quick-link-icon">🔍</div>
                        <h4>Explore Projects</h4>
                        <p>Discover projects created by students.</p>
                    </a>

                    <a href="${pageContext.request.contextPath}/collaboration-requests"
                       class="quick-link">
                        <div class="quick-link-icon">🤝</div>
                        <h4>Collaborate with Students</h4>
                        <p>Explore projects and find teammates.</p>
                    </a>

                </div>
            </div>

            <!-- COLLABORATION CORNER -->

            <div class="panel">

                <div class="panel-header">
                    <h3>Collaboration Corner</h3>

                    <a href="${pageContext.request.contextPath}/collaboration-requests"
                       class="view-all">
                        View All →
                    </a>
                </div>

                <c:choose>

                    <c:when test="${empty recentCollaborationRequests}">
                        <div class="empty-state">
                            <div class="empty-icon">⇄</div>

                            <p>
                                No collaboration requests yet.
                            </p>

                            <a href="${pageContext.request.contextPath}/projects"
                               class="empty-action">
                                Explore Projects →
                            </a>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${recentCollaborationRequests}"
                                   var="collaboration">

                            <div class="mentor-item">

                                <div class="mentor-avatar">
                                    <c:out value="${fn:substring(collaboration.other_student_name, 0, 1)}"/>
                                </div>

                                <div class="mentor-info">
                                    <h4>
                                        <c:out value="${collaboration.other_student_name}"/>
                                    </h4>

                                    <p>
                                        <c:out value="${collaboration.direction}"/>
                                        ·
                                        <c:out value="${collaboration.project_name}"/>
                                    </p>

                                    <p>
                                        Status:
                                        <c:out value="${collaboration.status}"/>
                                    </p>
                                </div>

                                <a href="${pageContext.request.contextPath}/collaboration-requests"
                                   class="mentor-action">
                                    View
                                </a>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

        </section>

        <footer class="footer">© 2026 Campus Innovation and Collaboration Hub</footer>
    </main>
</div>
</body>
</html>