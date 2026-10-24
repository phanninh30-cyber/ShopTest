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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/login.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        username = username == null ? null : username.trim();

        String error = AuthValidator.validateLogin(username, password);
        if (error == null) {
            User user = DataStore.getInstance().findUser(username);
            if (user == null || !user.getPassword().equals(password)) {
                error = "Invalid username or password";
            } else {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        }
        request.setAttribute("error", error);
        request.setAttribute("username", username);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}