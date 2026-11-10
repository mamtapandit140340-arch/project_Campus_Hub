<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gray-100 min-h-screen flex items-center justify-center">

<div class="bg-white w-full max-w-md p-7 rounded-lg shadow-md">

    <h2 class="text-2xl font-semibold text-gray-800 text-center">
        Campus Hub Registration
    </h2>

    <p class="text-gray-500 text-sm text-center mt-2 mb-6">
        Create your Campus Hub account
    </p>

<% if (request.getAttribute("errorMessage") != null) { %>
    <p class="text-red-600 text-sm mb-3">
        <%= org.apache.commons.text.StringEscapeUtils.escapeHtml4(
            String.valueOf(request.getAttribute("errorMessage"))) %>
    </p>
<% } %>

    <form action="register" method="post">

        <label class="block text-gray-700 mb-1">Register As</label>

        <select name="role" id="role" required
                class="w-full border border-gray-300 rounded p-2 mb-4">

            <option value="STUDENT">Student</option>
            <option value="MENTOR">Mentor</option>

        </select>

        <label class="block text-gray-700 mb-1">Full Name</label>
        <input type="text" name="name"
               placeholder="Enter your full name" required
               class="w-full border border-gray-300 rounded p-2 mb-4
                      focus:outline-none focus:border-blue-500">

        <label class="block text-gray-700 mb-1">Email Address</label>
        <input type="email" name="email"
               placeholder="Enter your email" required
               class="w-full border border-gray-300 rounded p-2 mb-4
                      focus:outline-none focus:border-blue-500">

        <label class="block text-gray-700 mb-1">Password</label>
        <input type="password" name="password"
               placeholder="Enter your password"
               minlength="8" required
               class="w-full border border-gray-300 rounded p-2 mb-5
                      focus:outline-none focus:border-blue-500">

        <button type="submit"
                class="w-full bg-blue-600 text-white py-2 rounded
                       hover:bg-blue-700">
            Register
        </button>

    </form>

    <p class="text-center text-sm text-gray-600 mt-5">
        Already have an account?
        <a href="login.jsp" class="text-blue-600 hover:underline">
            Login
        </a>
    </p>
</div>
</body>
</html>