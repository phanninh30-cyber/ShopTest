package com.ninh.asm.controller;

import com.ninh.asm.model.Category;
import com.ninh.asm.store.DataStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/categories")
public class CategoryServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/categories.jsp";
    private final DataStore store = DataStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("edit".equals(request.getParameter("action"))) {
            Integer id = parseId(request.getParameter("id"));
            Category category = id == null ? null : store.findCategory(id);
            if (category != null) {
                request.setAttribute("formId", category.getId());
                request.setAttribute("formName", category.getName());
            }
        }
        showList(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Integer id = parseId(request.getParameter("id"));

        if ("delete".equals(action)) {
            if (id != null) {
                store.deleteCategory(id);
            }
            response.sendRedirect(request.getContextPath() + "/admin/categories");
            return;
        }

        String name = request.getParameter("name");
        name = name == null ? "" : name.trim();

        String error = validateName(name, id);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("formId", id);
            request.setAttribute("formName", name);
            showList(request, response);
            return;
        }

        if (id == null) {
            store.addCategory(name);
        } else {
            store.updateCategory(id, name);
        }
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    private String validateName(String name, Integer id) {
        if (name.isEmpty()) {
            return "Category name is required";
        }
        if (name.length() > 50) {
            return "Category name must be at most 50 characters";
        }
        if (store.categoryNameExists(name, id)) {
            return "Category name already exists";
        }
        return null;
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categories", store.findAllCategories());
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    private static Integer parseId(String value) {
        try {
            return value == null || value.isBlank() ? null : Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}