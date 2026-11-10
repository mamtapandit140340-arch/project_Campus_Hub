<%--
  Created by IntelliJ IDEA.
  User: diksha
  Date: 31-10-2026
  Time: 02:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit an Idea | Campus Hub</title>

    <style>
        * { box-sizing: border-box; }

        :root {
            --primary: #6558e8;
            --primary-dark: #5043d0;
            --background: #f7f8fc;
            --text: #242236;
            --muted: #77798e;
            --border: #e5e6ef;
            --white: #fff;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: var(--background);
            color: var(--text);
        }

        a { text-decoration: none; color: inherit; }

        .topbar {
            height: 70px;
            padding: 0 6%;
            background: white;
            border-bottom: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 10px;
            font-weight: bold;
            font-size: 18px;
        }

        .brand-icon {
            width: 38px;
            height: 38px;
            display: grid;
            place-items: center;
            background: var(--primary);
            color: white;
            border-radius: 11px;
        }

        .back-link {
            color: var(--muted);
            font-size: 13px;
        }

        .back-link:hover { color: var(--primary); }

        .container {
            width: min(780px, 92%);
            margin: 38px auto;
        }

        .intro { margin-bottom: 24px; }

        .breadcrumb {
            color: var(--muted);
            font-size: 12px;
            margin-bottom: 12px;
        }

        h1 {
            font-size: clamp(24px, 4vw, 32px);
            margin: 0 0 10px;
        }

        .intro p {
            color: var(--muted);
            font-size: 14px;
            line-height: 1.7;
            margin: 0;
        }

        .form-card {
            background: var(--white);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: clamp(20px, 5vw, 36px);
            box-shadow: 0 8px 28px rgba(35, 28, 75, .04);
        }

        .step-indicator {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 8px;
            margin-bottom: 28px;
        }

        .step-marker {
            height: 4px;
            border-radius: 8px;
            background: #e9e9f2;
        }

        .step-marker.active { background: var(--primary); }

        .step-labels {
            display: flex;
            justify-content: space-between;
            gap: 8px;
            margin: -17px 0 30px;
            color: var(--muted);
            font-size: 11px;
        }

        .step-label.active {
            color: var(--primary);
            font-weight: bold;
        }

        .form-step { display: none; }
        .form-step.active { display: block; }

        .step-heading { margin-bottom: 24px; }

        .step-heading h2 {
            font-size: 20px;
            margin: 0 0 8px;
        }

        .step-heading p {
            color: var(--muted);
            font-size: 13px;
            line-height: 1.6;
            margin: 0;
        }

        .field { margin-bottom: 22px; }

        label {
            display: block;
            font-size: 13px;
            font-weight: bold;
            margin-bottom: 9px;
        }

        .required { color: #d64545; }

        input, select, textarea {
            width: 100%;
            padding: 13px 14px;
            border: 1px solid #dfe0eb;
            border-radius: 9px;
            font: inherit;
            font-size: 13px;
            color: var(--text);
            background: white;
        }

        textarea {
            min-height: 145px;
            resize: vertical;
            line-height: 1.6;
        }

        input:focus, select:focus, textarea:focus {
            outline: 2px solid #6558e833;
            border-color: var(--primary);
        }

        .hint {
            margin-top: 8px;
            font-size: 11px;
            color: var(--muted);
            line-height: 1.6;
        }

        .error-message {
            color: #b42318;
            background: #fff0ef;
            border: 1px solid #ffd5d1;
            border-radius: 8px;
            padding: 12px;
            font-size: 12px;
            margin-bottom: 20px;
        }

        .review-list {
            display: grid;
            gap: 16px;
        }

        .review-item {
            background: #f8f8fc;
            border: 1px solid var(--border);
            border-radius: 10px;
            padding: 15px;
        }

        .review-item strong {
            display: block;
            font-size: 12px;
            margin-bottom: 8px;
        }

        .review-item p {
            margin: 0;
            font-size: 13px;
            line-height: 1.7;
            white-space: pre-wrap;
            overflow-wrap: anywhere;
        }

        .form-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            border-top: 1px solid var(--border);
            padding-top: 22px;
            margin-top: 28px;
        }

        .button {
            border: 0;
            border-radius: 9px;
            padding: 12px 18px;
            font-size: 13px;
            font-weight: bold;
            cursor: pointer;
        }

        .button-secondary {
            background: #f0f0f7;
            color: #4c4d60;
        }

        .button-primary {
            background: var(--primary);
            color: white;
        }

        .button-primary:hover { background: var(--primary-dark); }

        .button:disabled {
            opacity: .45;
            cursor: not-allowed;
        }

        .privacy-note {
            color: var(--muted);
            font-size: 11px;
            line-height: 1.7;
            margin-top: 18px;
        }

        @media (max-width: 500px) {
            .topbar { padding: 0 4%; }
            .brand { font-size: 15px; }
            .back-link { font-size: 11px; }
            .container { margin: 25px auto; }
            .step-labels { font-size: 9px; }
            .button { padding: 12px; }
        }
    </style>
</head>

<body>

<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/student-dashboard">
        <span class="brand-icon">🎓</span>
        <span>Campus Hub</span>
    </a>

    <a class="back-link"
       href="${pageContext.request.contextPath}/ideas">
        ← Back to My Ideas
    </a>
</header>

<main class="container">

    <section class="intro">
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/student-dashboard">Dashboard</a>
            / <a href="${pageContext.request.contextPath}/ideas">My Ideas</a>
            / Submit an Idea
        </div>

        <h1>Share your idea 💡</h1>
        <p>
            Explain the problem, describe your solution, and show the impact
            you want to create. Complete each step before submitting.
        </p>
    </section>

    <section class="form-card">

        <div class="step-indicator" aria-hidden="true">
            <div class="step-marker active"></div>
            <div class="step-marker"></div>
            <div class="step-marker"></div>
            <div class="step-marker"></div>
        </div>

        <div class="step-labels">
            <span class="step-label active">Basic info</span>
            <span class="step-label">Problem</span>
            <span class="step-label">Solution</span>
            <span class="step-label">Review</span>
        </div>

        <c:if test="${param.error == 'required'}">
            <div class="error-message">
                All fields are required. Please complete the form again.
            </div>
        </c:if>

        <c:if test="${param.error == 'category'}">
            <div class="error-message">
                Please select a valid category.
            </div>
        </c:if>

        <form id="ideaForm"
              action="${pageContext.request.contextPath}/idea-form"
              method="post">

            <!-- STEP 1 -->
            <section class="form-step active" data-step="0">

                <div class="step-heading">
                    <h2>Basic information</h2>
                    <p>Start with a clear title and choose the category that best fits your idea.</p>
                </div>

                <div class="field">
                    <label for="title">
                        Idea title <span class="required">*</span>
                    </label>

                    <input id="title"
                           name="title"
                           type="text"
                           maxlength="200"
                           minlength="5"
                           placeholder="e.g. Smart Campus Waste Management"
                           required>

                    <div class="hint">
                        Use a short, descriptive title that explains the main idea.
                    </div>
                </div>

                <div class="field">
                    <label for="categoryId">
                        Category <span class="required">*</span>
                    </label>

                    <select id="categoryId" name="categoryId" required>
                        <option value="">Choose a category</option>

                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}">
                                <c:out value="${category.categoryName}"/>
                            </option>
                        </c:forEach>
                    </select>

                    <div class="hint">
                        Categories are loaded from your MySQL categories table.
                    </div>
                </div>

            </section>

            <!-- STEP 2 -->
            <section class="form-step" data-step="1">

                <div class="step-heading">
                    <h2>Define the problem</h2>
                    <p>Describe the problem your idea addresses and why it matters.</p>
                </div>

                <div class="field">
                    <label for="problemStatement">
                        Problem statement <span class="required">*</span>
                    </label>

                    <textarea id="problemStatement"
                              name="problemStatement"
                              minlength="20"
                              maxlength="5000"
                              placeholder="What problem exists? Who experiences it? Why is it important to solve?"
                              required></textarea>

                    <div class="hint">
                        Be specific. Explain the current difficulty instead of only naming a broad topic.
                    </div>
                </div>

            </section>

            <!-- STEP 3 -->
            <section class="form-step" data-step="2">

                <div class="step-heading">
                    <h2>Describe your solution</h2>
                    <p>Explain how your idea could solve the problem you described.</p>
                </div>

                <div class="field">
                    <label for="proposedSolution">
                        Proposed solution <span class="required">*</span>
                    </label>

                    <textarea id="proposedSolution"
                              name="proposedSolution"
                              minlength="20"
                              maxlength="5000"
                              placeholder="How would your idea work? What would users do, and how would the solution help?"
                              required></textarea>

                    <div class="hint">
                        Focus on the core approach. You don't need to know every technical detail yet.
                    </div>
                </div>

            </section>

            <!-- STEP 4 -->
            <section class="form-step" data-step="3">

                <div class="step-heading">
                    <h2>Expected outcome and review</h2>
                    <p>Describe the benefits and check your information before submission.</p>
                </div>

                <div class="field">
                    <label for="expectedOutcome">
                        Expected outcome <span class="required">*</span>
                    </label>

                    <textarea id="expectedOutcome"
                              name="expectedOutcome"
                              minlength="10"
                              maxlength="3000"
                              placeholder="What improvement or measurable benefit do you expect?"
                              required></textarea>
                </div>

                <div class="review-list">
                    <div class="review-item">
                        <strong>Idea title</strong>
                        <p id="reviewTitle">Not entered</p>
                    </div>

                    <div class="review-item">
                        <strong>Category</strong>
                        <p id="reviewCategory">Not selected</p>
                    </div>

                    <div class="review-item">
                        <strong>Problem statement</strong>
                        <p id="reviewProblem">Not entered</p>
                    </div>

                    <div class="review-item">
                        <strong>Proposed solution</strong>
                        <p id="reviewSolution">Not entered</p>
                    </div>

                    <div class="review-item">
                        <strong>Expected outcome</strong>
                        <p id="reviewOutcome">Not entered</p>
                    </div>
                </div>

                <p class="privacy-note">
                    By submitting, you confirm that the information is accurate.
                    Your idea will be saved with a PENDING status for review.
                </p>

            </section>

            <div class="form-actions">
                <button type="button"
                        id="previousButton"
                        class="button button-secondary"
                        disabled>
                    ← Previous
                </button>

                <button type="button"
                        id="nextButton"
                        class="button button-primary">
                    Next step →
                </button>

                <button type="submit"
                        id="submitButton"
                        class="button button-primary"
                        hidden>
                    Submit Idea
                </button>
            </div>

        </form>
    </section>
</main>

<script>
    const form = document.getElementById("ideaForm");
    const steps = Array.from(document.querySelectorAll(".form-step"));
    const markers = Array.from(document.querySelectorAll(".step-marker"));
    const labels = Array.from(document.querySelectorAll(".step-label"));

    const previousButton = document.getElementById("previousButton");
    const nextButton = document.getElementById("nextButton");
    const submitButton = document.getElementById("submitButton");

    let currentStep = 0;

    function showStep(index) {
        currentStep = index;

        steps.forEach((step, i) => {
            step.classList.toggle("active", i === index);
        });

        markers.forEach((marker, i) => {
            marker.classList.toggle("active", i <= index);
        });

        labels.forEach((label, i) => {
            label.classList.toggle("active", i === index);
        });

        previousButton.disabled = index === 0;
        nextButton.hidden = index === steps.length - 1;
        submitButton.hidden = index !== steps.length - 1;

        if (index === steps.length - 1) {
            updateReview();
        }
    }

    function validateCurrentStep() {
        const fields = Array.from(
            steps[currentStep].querySelectorAll("input, select, textarea")
        );

        for (const field of fields) {
            if (!field.checkValidity()) {
                field.reportValidity();
                field.focus();
                return false;
            }

            if (field.value.trim() === "") {
                field.setCustomValidity("Please complete this field.");
                field.reportValidity();
                field.focus();
                field.setCustomValidity("");
                return false;
            }
        }

        return true;
    }

    function updateReview() {
        document.getElementById("reviewTitle").textContent =
            document.getElementById("title").value.trim() || "Not entered";

        const category = document.getElementById("categoryId");
        document.getElementById("reviewCategory").textContent =
            category.options[category.selectedIndex]?.text || "Not selected";

        document.getElementById("reviewProblem").textContent =
            document.getElementById("problemStatement").value.trim() || "Not entered";

        document.getElementById("reviewSolution").textContent =
            document.getElementById("proposedSolution").value.trim() || "Not entered";

        document.getElementById("reviewOutcome").textContent =
            document.getElementById("expectedOutcome").value.trim() || "Not entered";
    }

    nextButton.addEventListener("click", () => {
        if (!validateCurrentStep()) return;
        showStep(Math.min(currentStep + 1, steps.length - 1));
    });

    previousButton.addEventListener("click", () => {
        showStep(Math.max(currentStep - 1, 0));
    });

    form.addEventListener("submit", event => {
        if (!validateCurrentStep()) {
            event.preventDefault();
        } else {
            submitButton.disabled = true;
            submitButton.textContent = "Submitting...";
        }
    });
    showStep(0);
</script>
</body>
</html>
