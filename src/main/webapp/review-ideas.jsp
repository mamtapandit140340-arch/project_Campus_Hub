
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Student Ideas | Campus Hub</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          rel="stylesheet">

    <style>
        :root {
            --sidebar: #201a32;
            --purple: #6255df;
            --blue: #2860e8;
            --text: #202744;
            --muted: #69748f;
            --page: #f3f5fc;
            --border: #e2e6f0;
        }

        * { box-sizing: border-box; }

        body {
            margin: 0;
            background: var(--page);
            color: var(--text);
            font-family: Arial, Helvetica, sans-serif;
        }

        .sidebar {
            position: fixed;
            inset: 0 auto 0 0;
            width: 232px;
            background: var(--sidebar);
            color: white;
            padding: 22px 12px;
            display: flex;
            flex-direction: column;
            z-index: 100;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 13px;
            padding: 0 10px 36px;
        }

        .brand-icon {
            width: 46px;
            height: 46px;
            border-radius: 13px;
            display: grid;
            place-items: center;
            background: linear-gradient(135deg, #8b72ff, #5944d5);
            font-size: 23px;
        }

        .brand h5 {
            margin: 0;
            font-size: 17px;
            font-weight: 700;
        }

        .brand small {
            display: block;
            margin-top: 4px;
            color: #b9b4d6;
            font-size: 12px;
        }

        .nav-heading {
            padding: 0 12px;
            margin-bottom: 12px;
            color: #a9a4c1;
            font-size: 11px;
            letter-spacing: 1px;
        }

        .side-link {
            display: flex;
            align-items: center;
            gap: 14px;
            padding: 13px 15px;
            margin-bottom: 5px;
            border-radius: 9px;
            color: #e4e1f0;
            text-decoration: none;
            font-size: 14px;
        }

        .side-link i { width: 17px; text-align: center; }

        .side-link:hover,
        .side-link.active {
            color: white;
            background: var(--purple);
        }

        .side-bottom {
            margin-top: auto;
            padding: 18px 12px 5px;
            border-top: 1px solid #403951;
        }

        .side-bottom a {
            color: #ff999f;
            text-decoration: none;
        }

        .main {
            margin-left: 232px;
            padding: 27px 32px 35px;
            min-height: 100vh;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 16px;
            margin-bottom: 20px;
        }

        .page-header h1 {
            font-size: clamp(25px, 2.4vw, 35px);
            font-weight: 750;
            margin: 0 0 7px;
        }

        .page-header p { color: var(--muted); margin: 0; }

        .mentor-label {
            white-space: nowrap;
            padding-top: 6px;
            font-weight: 600;
        }

        .summary-grid {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 18px;
            margin-bottom: 23px;
        }

        .summary-card {
            background: white;
            border: 1px solid var(--border);
            border-radius: 11px;
            padding: 17px 20px;
            display: flex;
            align-items: center;
            gap: 19px;
            box-shadow: 0 2px 5px #20274408;
        }

        .summary-icon {
            width: 76px;
            height: 76px;
            flex-shrink: 0;
            display: grid;
            place-items: center;
            border-radius: 14px;
            font-size: 33px;
        }

        .summary-icon.blue { background: #e1eaff; color: #2860e8; }
        .summary-icon.orange { background: #fff0df; color: #ec7d18; }
        .summary-icon.green { background: #def4e5; color: #22985a; }

        .summary-card span { color: var(--muted); font-size: 15px; }
        .summary-card h2 { margin: 6px 0 0; font-size: 34px; font-weight: 750; }

        .filters {
            display: grid;
            grid-template-columns: minmax(180px, 1fr) auto minmax(130px, 155px)
                               minmax(130px, 155px) auto;
            align-items: center;
            gap: 12px;
            margin-bottom: 20px;
        }

        .filters .form-control,
        .filters .form-select {
            min-height: 43px;
            border-color: #dce1ef;
            border-radius: 8px;
        }

        .search-wrap { position: relative; }
        .search-wrap i {
            position: absolute;
            top: 14px;
            left: 14px;
            color: #8b95b0;
        }
        .search-wrap input { padding-left: 40px; }

        .btn-refresh {
            min-height: 43px;
            background: var(--blue);
            color: white;
            border: 0;
            border-radius: 8px;
            padding: 0 18px;
        }

        .idea-list {
            display: flex;
            flex-direction: column;
            gap: 9px;
        }

        .idea-card {
            background: white;
            border: 1px solid var(--border);
            border-radius: 11px;
            padding: 17px 19px;
            display: grid;
            grid-template-columns: 86px minmax(0, 1fr) auto;
            gap: 17px;
            align-items: start;
            box-shadow: 0 2px 4px #20274408;
        }

        .idea-icon {
            height: 86px;
            width: 86px;
            border-radius: 14px;
            display: grid;
            place-items: center;
            font-size: 37px;
        }

        .idea-icon.blue { background: #e2eaff; color: #3b79e8; }
        .idea-icon.green { background: #e0f5e7; color: #209a60; }
        .idea-icon.purple { background: #eee9ff; color: #7761e7; }

        .idea-info h3 {
            font-size: 20px;
            font-weight: 750;
            margin: 0 0 8px;
        }

        .metadata {
            display: flex;
            flex-wrap: wrap;
            gap: 8px 12px;
            align-items: center;
            margin-bottom: 8px;
            color: #424d70;
            font-size: 13px;
        }

        .metadata i { margin-right: 5px; }
        .meta-divider { color: #c1c7d7; }

        .status {
            display: inline-block;
            border-radius: 20px;
            padding: 5px 11px;
            font-size: 11px;
            font-weight: 750;
            white-space: nowrap;
        }

        .status.pending { color: #946300; background: #fff0bd; }
        .status.under-review { color: #174db4; background: #dce8ff; }
        .status.approved { color: #087944; background: #d8f8e5; }
        .status.rejected { color: #a8202a; background: #ffe0e2; }
        .status.needs-improvement { color: #8b4d0b; background: #ffead1; }

        .idea-description {
            color: #4c5678;
            font-size: 13px;
            line-height: 1.55;
            overflow-wrap: anywhere;
        }

        .idea-description strong { color: #343d60; }

        .idea-actions {
            border-left: 1px solid var(--border);
            padding-left: 18px;
            min-height: 86px;
            display: flex;
            align-items: flex-start;
            justify-content: center;
            gap: 8px;
            flex-wrap: wrap;
            min-width: 300px;
        }

        .idea-actions .btn {
            font-size: 12px;
            font-weight: 650;
            padding: 9px 12px;
            white-space: nowrap;
        }

        .btn-view { background: var(--blue); color: white; }
        .btn-approve { background: #23995d; color: white; }
        .btn-reject { background: #e73d4b; color: white; }
        .btn-changes { border: 1px solid #8586a7; color: #343957; background: white; }

        .btn-view:hover, .btn-approve:hover, .btn-reject:hover {
            color: white;
            filter: brightness(.92);
        }

        .queue-card {
            margin-top: 21px;
            background: white;
            border: 1px solid var(--border);
            border-radius: 12px;
            overflow: hidden;
        }

        .queue-heading {
            padding: 16px 18px 0;
            display: flex;
            gap: 25px;
            border-bottom: 1px solid var(--border);
        }

        .queue-heading a {
            padding: 0 10px 13px;
            text-decoration: none;
            color: var(--muted);
            font-size: 14px;
            font-weight: 650;
        }

        .queue-heading a.active {
            color: #1645b8;
            border-bottom: 3px solid var(--blue);
        }

        .queue-table-wrap { overflow-x: auto; }

        .queue-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 12px;
        }

        .queue-table th {
            background: #f1f3fb;
            color: #596384;
            font-weight: 700;
        }

        .queue-table th, .queue-table td {
            padding: 12px 10px;
            border: 1px solid #e7eaf3;
            vertical-align: middle;
        }

        .queue-table td .btn {
            margin: 2px;
            font-size: 11px;
            white-space: nowrap;
        }

        .empty-state {
            padding: 32px 15px;
            text-align: center;
            color: var(--muted);
            border: 1px dashed #d6dceb;
            border-radius: 10px;
            background: white;
        }

        .footer {
            text-align: center;
            color: #929bb4;
            font-size: 12px;
            margin-top: 28px;
        }

        @media (max-width: 1200px) {
            .main { padding: 24px 20px; }
            .idea-card { grid-template-columns: 68px minmax(0, 1fr); }
            .idea-icon { width: 68px; height: 68px; font-size: 29px; }
            .idea-actions {
                grid-column: 2;
                border-left: 0;
                border-top: 1px solid var(--border);
                padding: 12px 0 0;
                min-width: 0;
                justify-content: flex-start;
            }
        }

        @media (max-width: 850px) {
            .sidebar { width: 72px; padding: 16px 7px; }
            .brand { justify-content: center; padding: 0 0 30px; }
            .brand > div:last-child, .nav-heading, .side-link span,
            .side-bottom span { display: none; }
            .side-link { justify-content: center; padding: 14px 5px; }
            .side-link i { width: auto; }
            .side-bottom { padding: 18px 5px; text-align: center; }
            .main { margin-left: 72px; }
            .summary-grid { gap: 10px; }
            .summary-card { padding: 13px; gap: 10px; }
            .summary-icon { width: 48px; height: 48px; font-size: 22px; }
            .summary-card h2 { font-size: 27px; }
            .filters {
                grid-template-columns: 1fr 1fr;
            }
            .filters .search-wrap { grid-column: 1 / -1; }
        }

        @media (max-width: 560px) {
            .main { padding: 20px 12px; }
            .page-header h1 { font-size: 25px; }
            .page-header p { font-size: 13px; }
            .mentor-label { font-size: 12px; }
            .summary-grid { grid-template-columns: 1fr; }
            .summary-card { padding: 12px 15px; }
            .summary-icon { width: 52px; height: 52px; }
            .filters { grid-template-columns: 1fr; }
            .filters .search-wrap { grid-column: auto; }
            .idea-card { padding: 13px; gap: 11px; }
            .idea-info h3 { font-size: 17px; }
            .idea-actions { grid-column: 1 / -1; }
            .metadata { font-size: 12px; }
            .queue-heading { gap: 5px; }
        }
    </style>
</head>

<body>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<aside class="sidebar">
    <div class="brand">
        <div class="brand-icon"><i class="fa-solid fa-graduation-cap"></i></div>
        <div>
            <h5>Campus Hub</h5>
            <small>Mentor Portal</small>
        </div>
    </div>

    <div class="nav-heading">MAIN MENU</div>

    <a class="side-link" href="${ctx}/mentor-dashboard">
        <i class="fa-solid fa-house"></i><span>Dashboard</span>
    </a>
    <a class="side-link active" href="${ctx}/review-ideas">
        <i class="fa-solid fa-star"></i><span>Review Ideas</span>
    </a>
    <a class="side-link" href="${ctx}/approve-projects">
        <i class="fa-solid fa-circle-check"></i><span>Approve Projects</span>
    </a>
    <a class="side-link" href="${ctx}/my-projects">
        <i class="fa-solid fa-folder"></i><span>My Projects</span>
    </a>
    <a class="side-link" href="${ctx}/mentor-profile">
        <i class="fa-solid fa-user"></i><span>Profile</span>
    </a>

    <div class="side-bottom">
        <a href="${ctx}/logout">
            <i class="fa-solid fa-right-from-bracket"></i>
            <span>Logout</span>
        </a>
    </div>
</aside>

<main class="main">

    <header class="page-header">
        <div>
            <h1>Review Student Ideas</h1>
            <p>Evaluate submitted ideas and move promising concepts toward project approval.</p>
        </div>
        <div class="mentor-label">
            <i class="fa-solid fa-user-tie me-2"></i>
            <c:out value="${empty mentorName ? sessionScope.userName : mentorName}"/>
            <span class="text-secondary ms-1">Mentor</span>
        </div>
    </header>

    <c:if test="${not empty param.result}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <c:choose>
                <c:when test="${param.result eq 'approved'}">Idea approved successfully.</c:when>
                <c:when test="${param.result eq 'rejected'}">Idea rejected.</c:when>
                <c:when test="${param.result eq 'changesRequested'}">Changes requested successfully.</c:when>
                <c:otherwise>Action result: <c:out value="${param.result}"/></c:otherwise>
            </c:choose>
            <button type="button" class="btn-close" data-bs-dismiss="alert"
                    aria-label="Close"></button>
        </div>
    </c:if>

    <div class="summary-grid">
        <div class="summary-card">
            <div class="summary-icon blue"><i class="fa-regular fa-lightbulb"></i></div>
            <div>
                <span>Pending Review</span>
                <h2><c:out value="${empty pendingCount ? 0 : pendingCount}"/></h2>
            </div>
        </div>

        <div class="summary-card">
            <div class="summary-icon orange"><i class="fa-regular fa-clock"></i></div>
            <div>
                <span>Under Review</span>
                <h2><c:out value="${empty underReviewCount ? 0 : underReviewCount}"/></h2>
            </div>
        </div>

        <div class="summary-card">
            <div class="summary-icon green"><i class="fa-solid fa-circle-check"></i></div>
            <div>
                <span>Approved</span>
                <h2><c:out value="${empty approvedCount ? 0 : approvedCount}"/></h2>
            </div>
        </div>
    </div>

    <form id="filterForm" class="filters" method="get" action="${ctx}/review-ideas">
        <div class="search-wrap">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input class="form-control" id="searchInput" name="search"
                   value="<c:out value='${param.search}'/>"
                   placeholder="Search ideas by title or student name...">
        </div>

        <label for="categoryFilter">Category</label>
        <select class="form-select" id="categoryFilter" name="category">
            <option value="">All Categories</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category.categoryId}"
                    <c:if test="${param.category eq category.categoryId.toString()}">selected</c:if>>
                    <c:out value="${category.categoryName}"/>
                </option>
            </c:forEach>
        </select>

        <select class="form-select" id="statusFilter" name="status"
                aria-label="Filter by status">
            <option value="">All Status</option>
            <option value="PENDING" <c:if test="${param.status eq 'PENDING'}">selected</c:if>>Pending</option>
            <option value="UNDER_REVIEW" <c:if test="${param.status eq 'UNDER_REVIEW'}">selected</c:if>>Under Review</option>
            <option value="APPROVED" <c:if test="${param.status eq 'APPROVED'}">selected</c:if>>Approved</option>
            <option value="REJECTED" <c:if test="${param.status eq 'REJECTED'}">selected</c:if>>Rejected</option>
            <option value="NEEDS_IMPROVEMENT" <c:if test="${param.status eq 'NEEDS_IMPROVEMENT'}">selected</c:if>>Needs Improvement</option>
        </select>

        <button class="btn-refresh" type="submit">
            <i class="fa-solid fa-rotate me-1"></i> Refresh
        </button>
    </form>

    <section class="idea-list">
        <c:choose>
            <c:when test="${empty ideas}">
                <div class="empty-state">
                    <i class="fa-regular fa-lightbulb fs-2 mb-3"></i>
                    <h5>No ideas found</h5>
                    <p class="mb-0">There are no ideas matching the selected filters.</p>
                </div>
            </c:when>

            <c:otherwise>
                <c:forEach var="idea" items="${ideas}" varStatus="loop">
                    <article class="idea-card">

                        <div class="idea-icon ${loop.index % 3 eq 0 ? 'blue' : (loop.index % 3 eq 1 ? 'green' : 'purple')}">
                            <c:choose>
                                <c:when test="${loop.index % 3 eq 0}">
                                    <i class="fa-solid fa-map-location-dot"></i>
                                </c:when>
                                <c:when test="${loop.index % 3 eq 1}">
                                    <i class="fa-solid fa-recycle"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-solid fa-users"></i>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="idea-info">
                            <h3><c:out value="${idea.title}"/></h3>

                            <div class="metadata">
                                <span><i class="fa-solid fa-user"></i>
                                    <c:out value="${idea.studentName}"/>
                                </span>
                                <span class="meta-divider">|</span>
                                <span><i class="fa-solid fa-table-cells"></i>
                                    <c:out value="${idea.categoryName}"/>
                                </span>
                                <span class="meta-divider">|</span>
                                <span><i class="fa-regular fa-calendar"></i>
                                    <c:out value="${idea.submittedAt}"/>
                                </span>
                                <span class="status ${fn:toLowerCase(fn:replace(idea.status, '_', '-'))}">
                                    <c:out value="${idea.status}"/>
                                </span>
                            </div>

                            <div class="idea-description">
                                <div><strong>Problem:</strong>
                                    <c:out value="${idea.problemStatement}"/>
                                </div>
                                <div><strong>Proposed Solution:</strong>
                                    <c:out value="${idea.proposedSolution}"/>
                                </div>
                                <div><strong>Expected Outcome:</strong>
                                    <c:out value="${idea.expectedOutcome}"/>
                                </div>
                            </div>
                        </div>

                        <div class="idea-actions">
                            <button type="button" class="btn btn-view"
                                    data-bs-toggle="modal"
                                    data-bs-target="#ideaModal${idea.ideaId}">
                                <i class="fa-regular fa-eye me-1"></i> View Details
                            </button>

                            <c:if test="${idea.status eq 'PENDING' or idea.status eq 'UNDER_REVIEW'}">
                                <form method="post" action="${ctx}/review-ideas"
                                      onsubmit="return confirm('Approve this idea?');">
                                    <input type="hidden" name="ideaId" value="${idea.ideaId}">
                                    <input type="hidden" name="action" value="approve">
                                    <button class="btn btn-approve" type="submit">
                                        <i class="fa-solid fa-check me-1"></i> Approve
                                    </button>
                                </form>

                                <form method="post" action="${ctx}/review-ideas"
                                      onsubmit="return confirm('Reject this idea?');">
                                    <input type="hidden" name="ideaId" value="${idea.ideaId}">
                                    <input type="hidden" name="action" value="reject">
                                    <button class="btn btn-reject" type="submit">
                                        <i class="fa-solid fa-xmark me-1"></i> Reject
                                    </button>
                                </form>

                                <button type="button" class="btn btn-changes"
                                        data-bs-toggle="modal"
                                        data-bs-target="#changesModal${idea.ideaId}">
                                    <i class="fa-solid fa-pen me-1"></i> Request Changes
                                </button>
                            </c:if>
                        </div>
                    </article>

                    <!-- Idea details modal -->
                    <div class="modal fade" id="ideaModal${idea.ideaId}" tabindex="-1"
                         aria-labelledby="ideaModalTitle${idea.ideaId}" aria-hidden="true">
                        <div class="modal-dialog modal-lg modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="ideaModalTitle${idea.ideaId}">
                                        <c:out value="${idea.title}"/>
                                    </h5>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p><strong>Student:</strong>
                                        <c:out value="${idea.studentName}"/></p>
                                    <p><strong>Category:</strong>
                                        <c:out value="${idea.categoryName}"/></p>
                                    <p><strong>Problem Statement</strong><br>
                                        <c:out value="${idea.problemStatement}"/></p>
                                    <p><strong>Proposed Solution</strong><br>
                                        <c:out value="${idea.proposedSolution}"/></p>
                                    <p><strong>Expected Outcome</strong><br>
                                        <c:out value="${idea.expectedOutcome}"/></p>
                                    <p><strong>Status:</strong>
                                        <c:out value="${idea.status}"/></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Request changes modal -->
                    <div class="modal fade" id="changesModal${idea.ideaId}" tabindex="-1"
                         aria-labelledby="changesTitle${idea.ideaId}" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <form method="post" action="${ctx}/review-ideas">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="changesTitle${idea.ideaId}">
                                            Request Changes
                                        </h5>
                                        <button type="button" class="btn-close"
                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Idea: <strong><c:out value="${idea.title}"/></strong></p>
                                        <input type="hidden" name="ideaId" value="${idea.ideaId}">
                                        <input type="hidden" name="action" value="requestChanges">
                                        <label class="form-label" for="feedback${idea.ideaId}">
                                            Feedback for the student
                                        </label>
                                        <textarea class="form-control"
                                                  id="feedback${idea.ideaId}"
                                                  name="feedback" rows="4"
                                                  maxlength="1000" required
                                                  placeholder="Explain what needs improvement..."></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-light"
                                                data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-primary">
                                            Send Feedback
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </section>

    <!-- Project approval queue -->
    <section class="queue-card">
        <div class="queue-heading">
            <a class="active" href="${ctx}/mentor-project-approvals">
                <i class="fa-solid fa-briefcase me-2"></i>Project Approval Queue
            </a>
            <a href="${ctx}/mentor-projects">
                <i class="fa-solid fa-list me-2"></i>My Projects
            </a>
        </div>

        <div class="queue-table-wrap">
            <table class="queue-table">
                <thead>
                <tr>
                    <th>Project Name</th>
                    <th>From Idea</th>
                    <th>Mentor Assigned</th>
                    <th>Status</th>
                    <th>Expected End Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty projectsAwaitingApproval}">
                        <tr>
                            <td colspan="6" class="text-center text-secondary py-4">
                                No projects awaiting approval.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="project" items="${projectsAwaitingApproval}">
                            <tr>
                                <td><strong><c:out value="${project.projectName}"/></strong></td>
                                <td>
                                    #<c:out value="${project.ideaId}"/> -
                                    <c:out value="${project.ideaTitle}"/>
                                </td>
                                <td><c:out value="${project.mentorName}"/></td>
                                <td>
                                    <span class="status under-review">
                                        <c:out value="${project.status}"/>
                                    </span>
                                </td>
                                <td><c:out value="${project.expectedEndDate}"/></td>
                                <td>
                                    <a class="btn btn-sm btn-view"
                                       href="${ctx}/mentor-project-approvals?projectId=${project.projectId}">
                                        <i class="fa-regular fa-eye"></i> View
                                    </a>

                                    <form class="d-inline" method="post"
                                          action="${ctx}/mentor-project-approvals"
                                          onsubmit="return confirm('Approve this project?');">
                                        <input type="hidden" name="projectId"
                                               value="${project.projectId}">
                                        <input type="hidden" name="action" value="approve">
                                        <button class="btn btn-sm btn-approve" type="submit">
                                            Approve
                                        </button>
                                    </form>

                                    <form class="d-inline" method="post"
                                          action="${ctx}/mentor-project-approvals"
                                          onsubmit="return confirm('Reject this project?');">
                                        <input type="hidden" name="projectId"
                                               value="${project.projectId}">
                                        <input type="hidden" name="action" value="reject">
                                        <button class="btn btn-sm btn-reject" type="submit">
                                            Reject
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="footer">Campus Innovation and Collaboration Hub</footer>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Client-side filtering of the ideas already loaded on this page.
    const searchInput = document.getElementById("searchInput");
    const categoryFilter = document.getElementById("categoryFilter");
    const statusFilter = document.getElementById("statusFilter");
    const filterForm = document.getElementById("filterForm");

    // Submit filters to the servlet so filtering can be done against the database.
    // Debouncing prevents a request on every keystroke.
    let searchTimer;
    searchInput.addEventListener("input", function () {
        clearTimeout(searchTimer);
        searchTimer = setTimeout(() => filterForm.requestSubmit(), 500);
    });

    categoryFilter.addEventListener("change", () => filterForm.requestSubmit());
    statusFilter.addEventListener("change", () => filterForm.requestSubmit());
</script>

</body>
</html>
