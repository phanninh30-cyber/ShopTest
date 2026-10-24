<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Categories - ShopTest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0" id="pageTitle">Category Management</h1>
        <div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-sm btn-outline-primary">Home</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm btn-outline-secondary"
               id="logout">Logout</a>
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" id="msgError"><c:out value="${error}"/></div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/categories"
          id="categoryForm" class="row g-2 mb-4">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="<c:out value='${formId}'/>">
        <div class="col-auto">
            <input type="text" class="form-control" id="name" name="name" placeholder="Category name"
                   value="<c:out value='${formName}'/>">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary" id="btnSave">
                <c:out value="${empty formId ? 'Add' : 'Update'}"/>
            </button>
            <c:if test="${not empty formId}">
                <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-light">Cancel</a>
            </c:if>
        </div>
    </form>

    <table class="table table-bordered bg-white" id="categoryTable">
        <thead>
        <tr><th style="width:80px;">ID</th><th>Name</th><th style="width:200px;">Actions</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${categories}">
            <tr>
                <td><c:out value="${c.id}"/></td>
                <td class="cat-name"><c:out value="${c.name}"/></td>
                <td>
                    <a class="btn btn-sm btn-warning btn-edit"
                       href="${pageContext.request.contextPath}/admin/categories?action=edit&id=${c.id}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/categories"
                          class="d-inline">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${c.id}">
                        <button type="submit" class="btn btn-sm btn-danger btn-delete"
                                onclick="return confirm('Delete this category?');">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>