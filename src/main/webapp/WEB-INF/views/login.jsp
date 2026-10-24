<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - ShopTest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5" style="max-width: 420px;">
    <h1 class="h3 mb-4" id="pageTitle">Login</h1>

    <c:if test="${param.registered == '1'}">
        <div class="alert alert-success" id="msgSuccess">Registration successful. Please log in.</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger" id="msgError"><c:out value="${error}"/></div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
        <div class="mb-3">
            <label class="form-label" for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username"
                   value="<c:out value='${username}'/>">
        </div>
        <div class="mb-3">
            <label class="form-label" for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <button type="submit" class="btn btn-primary w-100" id="btnLogin">Login</button>
    </form>

    <p class="mt-3 text-center">
        No account? <a href="${pageContext.request.contextPath}/register" id="linkRegister">Register</a>
    </p>
</div>
</body>
</html>