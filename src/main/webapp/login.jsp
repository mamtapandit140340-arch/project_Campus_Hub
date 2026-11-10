<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Hub - Login</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        .login-box {
            width: 300px;
            margin: 100px auto;
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-top: 12px;
        }

        input, select {
            width: 100%;
            padding: 8px;
            margin: 8px 0 15px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #2878d0;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #185da8;
        }

        p {
            text-align: center;
        }

        a {
            color: #2878d0;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            font-size: 13px;
        }

    </style>
</head>
<body>

<div class="login-box">
    <h2>Campus Hub Login</h2>
    <% if (request.getAttribute("errorMessage") != null) { %>

        <p class="error">
            <%= org.apache.commons.text.StringEscapeUtils.escapeHtml4(
                    String.valueOf(request.getAttribute("errorMessage"))
            ) %>
        </p>
    <% } %>

    <form action="${pageContext.request.contextPath}/login"
          method="post">

        <label>Email:</label>

        <input type="email"
               name="email"
               placeholder="Enter your email"
               required>

        <label>Password:</label>
        <input type="password"
               name="password"
               placeholder="Enter your password"
               required>
                <label>Login As:</label>
                <select name="role" required>
                    <option value="">Select Account Type</option>
                    <option value="STUDENT">Student</option>
                    <option value="MENTOR">Mentor</option>
                </select>
        <button type="submit">Login</button>
    </form>

    <p>
        Don't have an account?
        <a href="${pageContext.request.contextPath}/register.jsp">
            Register here
        </a>
    </p>
</div>
</body>
</html>