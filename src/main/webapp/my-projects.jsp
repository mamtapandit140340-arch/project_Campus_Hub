<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 01-11-2026
  Time: 01:41
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Projects | Campus Hub</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            margin: 0;
            background: #f3f5fb;
            color: #252746;
            font-family: Arial, sans-serif;
        }
        .sidebar {
            position: fixed;
            inset: 0 auto 0 0;
            width: 230px;
            padding: 28px 16px;
            background: #211b35;
            color: white;
        }
        .brand {
            font-size: 22px;
            font-weight: bold;
            margin: 0 10px 8px;
        }
        .sidebar a {
            display: block;
            padding: 13px 14px;
            margin: 8px 0;
            border-radius: 8px;
            color: #e7e4f6;
            text-decoration: none;
        }
        .sidebar a.active, .sidebar a:hover {
            background: #6255df;
            color: white;
        }
 .side-bottom {
         position: absolute;
         bottom: 0;
         left: 0;
         width: 100%;
     }

     .side-bottom a {
         display: flex;
         align-items: center;
         gap: 12px;
         padding: 12px 16px;
         color: #ff999f;
         text-decoration: none;
     }
        .main {
            margin-left: 230px;
            padding: 34px;
        }
        .project-card {
            background: white;
            border: 1px solid #e3e7f0;
            border-radius: 12px;
            padding: 22px;
            margin-bottom: 18px;
            box-shadow: 0 3px 12px #1b24500a;
        }
        .muted { color: #69728b; }
        .status {
            display: inline-block;
            border-radius: 20px;
            padding: 5px 10px;
            font-size: 12px;
            font-weight: bold;
        }
        .PENDING { background: #fff0c2; color: #865b00; }
        .APPROVED { background: #d8f7e4; color: #137547; }
        .REJECTED { background: #ffe0e0; color: #a32222; }
        .progress { height: 9px; }
        @media (max-width: 760px) {
            .main {
                margin-left: 0;
                padding: 18px;
            }
        }
    </style>
</head>

<body>
<aside class="sidebar">
    <div class="brand">🎓 Campus Hub</div>
    <div class="small text-white-50 mb-4 ms-2">Mentor Portal</div>

    <a href="${pageContext.request.contextPath}/mentor-dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/review-ideas">Review Ideas</a>
    <a href="${pageContext.request.contextPath}/approve-projects">Approve Projects</a>
    <a class="active"
       href="${pageContext.request.contextPath}/my-projects">My Projects</a>
    <a href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
      <div class="side-bottom">
            <a href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>
                <span>⇥Logout</span>
            </a>
        </div>
</aside>

<main class="main">
    <h1 class="fw-bold">My Projects</h1>
    <p class="muted mb-4">
        Track the approval status, schedule, and progress of your assigned projects.
    </p>

    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="project-card">
                <div class="muted">Total Assigned</div>
                <h2>${projects.size()}</h2>
            </div>
        </div>
        <div class="col-md-4">
            <div class="project-card">
                <div class="muted">Approved</div>
                <h2>
                    <c:set var="approvedCount" value="0"/>
                    <c:forEach var="p" items="${projects}">
                        <c:if test="${p.approvalStatus == 'APPROVED'}">
                            <c:set var="approvedCount"
                                   value="${approvedCount + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${approvedCount}
                </h2>
            </div>
        </div>
        <div class="col-md-4">
            <div class="project-card">
                <div class="muted">Awaiting Approval</div>
                <h2>
                    <c:set var="pendingCount" value="0"/>
                    <c:forEach var="p" items="${projects}">
                        <c:if test="${p.approvalStatus == 'PENDING'}">
                            <c:set var="pendingCount"
                                   value="${pendingCount + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${pendingCount}
                </h2>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty projects}">
            <div class="project-card text-center py-5">
                <h5>No projects assigned yet</h5>
                <p class="muted mb-0">
                    Projects assigned to your mentor account will appear here.
                </p>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach var="p" items="${projects}">
                <section class="project-card">
                    <div class="d-flex justify-content-between flex-wrap gap-2">
                        <div>
                            <h4 class="fw-bold">${p.projectName}</h4>
                            <p class="muted mb-2">
                                Based on idea: ${p.ideaTitle}
                            </p>
                        </div>

                        <span class="status ${p.approvalStatus}">
                            ${p.approvalStatus}
                        </span>
                    </div>

                    <p>
                        <strong>Category:</strong>
                        <c:out value="${p.categoryName != null ? p.categoryName : 'Uncategorized'}"/>
                    </p>

                    <p>
                        <c:out value="${p.description != null ? p.description : 'No description provided.'}"/>
                    </p>

                    <div class="row g-3 mb-3">
                        <div class="col-md-4">
                            <div class="muted small">Project ID</div>
                            <strong>${p.projectId}</strong>
                        </div>
                        <div class="col-md-4">
                            <div class="muted small">Start Date</div>
                            <strong>
                                <c:out value="${p.startDate != null ? p.startDate : 'Not set'}"/>
                            </strong>
                        </div>
                        <div class="col-md-4">
                            <div class="muted small">Expected End Date</div>
                            <strong>
                                <c:out value="${p.expectedEndDate != null ? p.expectedEndDate : 'Not set'}"/>
                            </strong>
                        </div>
                    </div>

                    <div class="d-flex justify-content-between mb-2">
                        <strong>Project Lifecycle: ${p.projectStatus}</strong>
                        <span>${p.progress}%</span>
                    </div>

                    <div class="progress mb-3">
                        <div class="progress-bar"
                             role="progressbar"
                             style="width: ${p.progress}%"
                             aria-valuenow="${p.progress}"
                             aria-valuemin="0"
                             aria-valuemax="100"></div>
                    </div>

                    <c:if test="${p.approvalStatus == 'REJECTED'}">
                        <div class="alert alert-danger mb-0">
                            This project was rejected. Review its proposal and
                            follow your project's resubmission process.
                        </div>
                    </c:if>
                </section>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>