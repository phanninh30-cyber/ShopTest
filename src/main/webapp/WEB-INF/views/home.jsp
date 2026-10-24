<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home - ShopTest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <h1 class="h3" id="pageTitle">Home</h1>
    <p id="welcome">Hello, <c:out value="${sessionScope.user.username}"/> (<c:out value="${sessionScope.user.role}"/>)</p>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <a class="btn btn-outline-primary" id="linkCategories"
           href="${pageContext.request.contextPath}/admin/categories">Manage categories</a>
    </c:if>
    <a class="btn btn-outline-secondary" id="logout"
       href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</body>
</html>