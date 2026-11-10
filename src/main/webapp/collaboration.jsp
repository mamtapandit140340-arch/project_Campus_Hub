<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Collaboration Requests | Campus Hub</title>

    <style>
        :root {
            --sidebar: #211b32;
            --primary: #635bdb;
            --page-bg: #f5f7fb;
            --text: #202938;
            --muted: #6b7280;
            --border: #e5e7eb;
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: var(--page-bg);
            color: var(--text);
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
            overflow-y: auto;
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
            color: white;
        }

        .brand-icon {
            width: 42px;
            height: 42px;
            flex-shrink: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background: var(--primary);
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
            transition: background 0.2s ease;
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
            flex-shrink: 0;
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
            margin: 0 0 10px;
            font-size: clamp(25px, 4vw, 34px);
        }

        .page-header p {
            color: var(--muted);
            line-height: 1.6;
            margin: 0;
        }

        .notice {
            margin-bottom: 20px;
            padding: 13px 16px;
            border-radius: 8px;
            background: #eef2ff;
            color: #3730a3;
        }

        .notice.error {
            background: #fef2f2;
            color: #991b1b;
        }

        .section {
            margin-top: 32px;
        }

        .section-heading {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 15px;
        }

        .section-heading h2 {
            margin: 0;
            font-size: 21px;
        }

        .count {
            font-size: 13px;
            background: #e5e7eb;
            border-radius: 20px;
            padding: 4px 9px;
        }

        .request-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 18px;
        }

        .request-card {
            background: white;
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 22px;
            box-shadow: 0 3px 12px rgba(15, 23, 42, 0.04);
            min-width: 0;
        }

        .card-top {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 10px;
        }

        .card-top h3 {
            margin: 0;
            font-size: 19px;
            overflow-wrap: anywhere;
        }

        .status {
            font-size: 11px;
            font-weight: 700;
            border-radius: 20px;
            padding: 6px 9px;
            white-space: nowrap;
        }

        .status.pending {
            color: #92400e;
            background: #fef3c7;
        }

        .status.accepted {
            color: #166534;
            background: #dcfce7;
        }

        .status.rejected {
            color: #991b1b;
            background: #fee2e2;
        }

        .requester {
            margin: 17px 0 8px;
            font-weight: 600;
        }

        .email,
        .request-date {
            font-size: 13px;
            color: var(--muted);
            overflow-wrap: anywhere;
        }

        .message {
            background: #f9fafb;
            padding: 13px;
            border-radius: 8px;
            margin: 16px 0;
            font-size: 14px;
            line-height: 1.6;
            overflow-wrap: anywhere;
            white-space: pre-wrap;
        }

        .message-label {
            font-size: 12px;
            font-weight: 700;
            color: var(--muted);
            margin-bottom: 5px;
        }

        /* Equal-sized Accept and Reject buttons */
        .actions {
            display: flex;
            gap: 12px;
            margin-top: 18px;
        }

        .actions form {
            flex: 1;
            min-width: 0;
            margin: 0;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 100%;
            min-height: 44px;
            padding: 12px 16px;
            border: none;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-align: center;
            transition: background-color 0.2s ease;
        }

        .btn-accept {
            background: #16a34a;
            color: white;
        }

        .btn-accept:hover {
            background: #15803d;
        }

        .btn-reject {
            background: #dc2626;
            color: white;
        }

        .btn-reject:hover {
            background: #b91c1c;
        }

        .empty-state {
            padding: 25px;
            background: white;
            border: 1px dashed #d1d5db;
            border-radius: 10px;
            text-align: center;
            color: var(--muted);
            grid-column: 1 / -1;
        }

        .empty-state h3 {
            margin-top: 0;
        }

        .footer {
            text-align: center;
            color: #9ca3af;
            font-size: 13px;
            padding: 30px 15px;
        }

        @media (max-width: 800px) {
            .sidebar {
                width: 200px;
            }

            .main-content {
                margin-left: 200px;
                padding: 25px 18px;
            }

            .request-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 600px) {
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
            .nav-divider {
                margin-top: 8px;
            }

            .sidebar nav {
                display: flex;
                flex-wrap: wrap;
                gap: 3px;
            }

            .nav-link {
                padding: 10px;
            }

            .sidebar-bottom {
                margin-top: 8px;
            }

            .main-content {
                margin-left: 0;
                padding: 22px 14px;
            }

            .request-card {
                padding: 17px;
            }
        }
    </style>
</head>

<body>

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
            <span>Dashboard</span>
        </a>

        <a href="${pageContext.request.contextPath}/projects"
           class="nav-link">
            <span class="nav-icon">▣</span>
            <span>Projects</span>
        </a>

        <a href="${pageContext.request.contextPath}/ideas"
           class="nav-link">
            <span class="nav-icon">✦</span>
            <span>My Ideas</span>
        </a>

        <a href="${pageContext.request.contextPath}/collaboration-requests"
           class="nav-link active">
            <span class="nav-icon">⇄</span>
            <span>Collaboration</span>
        </a>

        <a href="${pageContext.request.contextPath}/skills"
           class="nav-link">
            <span class="nav-icon">◇</span>
            <span>My Skills</span>
        </a>

        <a href="${pageContext.request.contextPath}/student-profile.jsp"
           class="nav-link">
            <span class="nav-icon">♙</span>
            <span>My Profile</span>
        </a>
    </nav>

    <hr class="nav-divider">

    <div class="nav-title">Account</div>

    <div class="sidebar-bottom">
        <a href="${pageContext.request.contextPath}/help.jsp"
           class="nav-link">
            <span class="nav-icon">?</span>
            <span>Help &amp; Support</span>
        </a>

        <a href="${pageContext.request.contextPath}/logout"
           class="nav-link logout-link">
            <span class="nav-icon">⇥</span>
            <span>Logout</span>
        </a>
    </div>

</aside>

<main class="main-content">

    <header class="page-header">
        <h1>Collaboration Requests</h1>
        <p>
            Review requests from students who want to join your projects.
            Accept or reject pending requests below.
        </p>
    </header>

    <c:if test="${param.result == 'accepted'}">
        <div class="notice">
            Request accepted successfully. The requester has been added to the project.
        </div>
    </c:if>

    <c:if test="${param.result == 'rejected'}">
        <div class="notice">
            Collaboration request rejected.
        </div>
    </c:if>

    <c:if test="${param.result == 'alreadyProcessed'}">
        <div class="notice error">
            This request has already been processed. Refresh the page to see its current status.
        </div>
    </c:if>

    <c:set var="pendingCount" value="0"/>
    <c:set var="acceptedCount" value="0"/>
    <c:set var="rejectedCount" value="0"/>

    <c:forEach var="item" items="${requests}">
        <c:choose>
            <c:when test="${item.status == 'PENDING'}">
                <c:set var="pendingCount" value="${pendingCount + 1}"/>
            </c:when>
            <c:when test="${item.status == 'ACCEPTED'}">
                <c:set var="acceptedCount" value="${acceptedCount + 1}"/>
            </c:when>
            <c:when test="${item.status == 'REJECTED'}">
                <c:set var="rejectedCount" value="${rejectedCount + 1}"/>
            </c:when>
        </c:choose>
    </c:forEach>

    <!-- Pending requests -->
    <section class="section">
        <div class="section-heading">
            <h2>Pending Requests</h2>
            <span class="count">${pendingCount}</span>
        </div>

        <div class="request-grid">
            <c:forEach var="item" items="${requests}">
                <c:if test="${item.status == 'PENDING'}">
                    <article class="request-card">

                        <div class="card-top">
                            <h3><c:out value="${item.projectName}"/></h3>
                            <span class="status pending">PENDING</span>
                        </div>

                        <p class="requester">
                            From: <c:out value="${item.senderName}"/>
                        </p>

                        <p class="email">
                            <c:out value="${item.senderEmail}"/>
                        </p>

                        <div class="message">
                            <div class="message-label">Request message</div>
                            <c:choose>
                                <c:when test="${not empty item.message}">
                                    <c:out value="${item.message}"/>
                                </c:when>
                                <c:otherwise>No message provided.</c:otherwise>
                            </c:choose>
                        </div>

                        <p class="request-date">
                            Received: <c:out value="${item.createdAt}"/>
                        </p>

                        <div class="actions">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/collaboration-requests.jsp"
                                  onsubmit="return confirm('Accept this collaboration request?');">
                                <input type="hidden" name="requestId"
                                       value="${item.requestId}">
                                <input type="hidden" name="action" value="accept">
                                <button class="btn btn-accept" type="submit">
                                    Accept
                                </button>
                            </form>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/collaboration-requests.jsp"
                                  onsubmit="return confirm('Reject this collaboration request?');">
                                <input type="hidden" name="requestId"
                                       value="${item.requestId}">
                                <input type="hidden" name="action" value="reject">
                                <button class="btn btn-reject" type="submit">
                                    Reject
                                </button>
                            </form>
                        </div>

                    </article>
                </c:if>
            </c:forEach>

            <c:if test="${pendingCount == 0}">
                <div class="empty-state">
                    <h3>No pending requests</h3>
                    <p>New collaboration requests will appear here.</p>
                </div>
            </c:if>
        </div>
    </section>

    <!-- Accepted requests -->
    <section class="section">
        <div class="section-heading">
            <h2>Accepted Requests</h2>
            <span class="count">${acceptedCount}</span>
        </div>

        <div class="request-grid">
            <c:forEach var="item" items="${requests}">
                <c:if test="${item.status == 'ACCEPTED'}">
                    <article class="request-card">

                        <div class="card-top">
                            <h3><c:out value="${item.projectName}"/></h3>
                            <span class="status accepted">ACCEPTED</span>
                        </div>

                        <p class="requester">
                            From: <c:out value="${item.senderName}"/>
                        </p>

                        <p class="email">
                            <c:out value="${item.senderEmail}"/>
                        </p>

                        <div class="message">
                            <div class="message-label">Request message</div>
                            <c:choose>
                                <c:when test="${not empty item.message}">
                                    <c:out value="${item.message}"/>
                                </c:when>
                                <c:otherwise>No message provided.</c:otherwise>
                            </c:choose>
                        </div>

                        <p class="request-date">
                            Received: <c:out value="${item.createdAt}"/>
                        </p>

                    </article>
                </c:if>
            </c:forEach>

            <c:if test="${acceptedCount == 0}">
                <div class="empty-state">No accepted requests yet.</div>
            </c:if>
        </div>
    </section>

    <!-- Rejected requests -->
    <section class="section">
        <div class="section-heading">
            <h2>Rejected Requests</h2>
            <span class="count">${rejectedCount}</span>
        </div>

        <div class="request-grid">
            <c:forEach var="item" items="${requests}">
                <c:if test="${item.status == 'REJECTED'}">
                    <article class="request-card">

                        <div class="card-top">
                            <h3><c:out value="${item.projectName}"/></h3>
                            <span class="status rejected">REJECTED</span>
                        </div>

                        <p class="requester">
                            From: <c:out value="${item.senderName}"/>
                        </p>

                        <p class="email">
                            <c:out value="${item.senderEmail}"/>
                        </p>

                        <div class="message">
                            <div class="message-label">Request message</div>
                            <c:choose>
                                <c:when test="${not empty item.message}">
                                    <c:out value="${item.message}"/>
                                </c:when>
                                <c:otherwise>No message provided.</c:otherwise>
                            </c:choose>
                        </div>

                        <p class="request-date">
                            Received: <c:out value="${item.createdAt}"/>
                        </p>

                    </article>
                </c:if>
            </c:forEach>

            <c:if test="${rejectedCount == 0}">
                <div class="empty-state">No rejected requests.</div>
            </c:if>
        </div>
    </section>

    <footer class="footer">
        Campus Innovation and Collaboration Hub
    </footer>

</main>

</body>
</html>