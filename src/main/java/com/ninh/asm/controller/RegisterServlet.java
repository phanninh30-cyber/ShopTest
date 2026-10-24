package com.ninh.asm.controller;

import com.ninh.asm.model.User;
import com.ninh.asm.store.DataStore;
import com.ninh.asm.util.AuthValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/register.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = trim(request.getParameter("username"));
        String email = trim(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        DataStore store = DataStore.getInstance();
        String error = AuthValidator.validateRegister(username, email, password, confirm);
        if (error == null && store.usernameExists(username)) {
            error = "Username already exists";
        }

        if (error == null) {
            store.addUser(new User(username, email, password, "USER"));
            response.sendRedirect(request.getContextPath() + "/login?registered=1");
            return;
        }
        request.setAttribute("error", error);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}