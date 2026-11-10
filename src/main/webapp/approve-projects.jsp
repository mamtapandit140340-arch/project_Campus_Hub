<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 01-11-2026
  Time: 01:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Approve Projects | Campus Hub</title>

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
        .tag {
            display: inline-block;
            border-radius: 20px;
            padding: 5px 10px;
            font-size: 12px;
            background: #fff0c2;
            color: #865b00;
            font-weight: bold;
        }
        @media (max-width: 760px) {
            .sidebar {
                position: static;
                width: 100%;
            }
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
    <a class="active"
       href="${pageContext.request.contextPath}/approve-projects">Approve Projects</a>
    <a href="${pageContext.request.contextPath}/my-projects">My Projects</a>
    <a href="${pageContext.request.contextPath}/profile.jsp">Profile</a>
     <div class="side-bottom">
           <a href="${pageContext.request.contextPath}/logout">
               <i class="fa-solid fa-right-from-bracket"></i>
               <span>⇥Logout</span>
           </a>
       </div>
</aside>

<main class="main">
    <h1 class="fw-bold">Approve Projects</h1>
    <p class="muted mb-4">
        Review projects assigned to you and approve or reject their proposals.
    </p>

    <c:if test="${param.result == 'approved'}">
        <div class="alert alert-success">Project approved successfully.</div>
    </c:if>
    <c:if test="${param.result == 'rejected'}">
        <div class="alert alert-warning">Project rejected.</div>
    </c:if>
    <c:if test="${param.result == 'notUpdated'}">
        <div class="alert alert-info">
            The project was already processed or is not assigned to your account.
        </div>
    </c:if>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>Pending Approval</h4>
        <span class="badge text-bg-primary">
            ${projects.size()} project(s)
        </span>
    </div>

    <c:choose>
        <c:when test="${empty projects}">
            <div class="project-card text-center py-5">
                <h5>No projects awaiting approval</h5>
                <p class="muted mb-0">
                    Projects assigned to you for approval will appear here.
                </p>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach var="p" items="${projects}">
                <section class="project-card">
                    <div class="d-flex justify-content-between flex-wrap gap-2">
                        <div>
                            <h4 class="fw-bold">${p.projectName}</h4>
                            <span class="tag">PENDING APPROVAL</span>
                        </div>
                        <div class="muted">
                            Project ID: ${p.projectId}
                        </div>
                    </div>

                    <hr>

                    <p>
                        <strong>Based on idea:</strong> ${p.ideaTitle}
                        (Idea #${p.ideaId})
                    </p>
                    <p>
                        <strong>Submitted by:</strong> ${p.studentName}
                    </p>
                    <p>
                        <strong>Category:</strong>
                        <c:out value="${p.categoryName != null ? p.categoryName : 'Uncategorized'}"/>
                    </p>
                    <p>
                        <strong>Description:</strong>
                        <c:out value="${p.description != null ? p.description : 'No description provided.'}"/>
                    </p>
                    <p>
                        <strong>Expected completion:</strong>
                        <c:out value="${p.expectedEndDate != null ? p.expectedEndDate : 'Not specified'}"/>
                    </p>
                    <p>
                        <strong>Project lifecycle:</strong> ${p.projectStatus}
                    </p>

                    <div class="d-flex gap-2 flex-wrap mt-4">
                        <form method="post"
                              action="${pageContext.request.contextPath}/approve-projects"
                              onsubmit="return confirm('Approve this project?');">
                            <input type="hidden" name="projectId"
                                   value="${p.projectId}">
                            <input type="hidden" name="action" value="approve">
                            <button class="btn btn-success" type="submit">
                                ✓ Approve Project
                            </button>
                        </form>

                        <form method="post"
                              action="${pageContext.request.contextPath}/approve-projects"
                              onsubmit="return confirm('Reject this project?');">
                            <input type="hidden" name="projectId"
                                   value="${p.projectId}">
                            <input type="hidden" name="action" value="reject">
                            <button class="btn btn-danger" type="submit">
                                ✕ Reject Project
                            </button>
                        </form>
                    </div>
                </section>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>