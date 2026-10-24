package com.ninh.asm.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ninh.asm.controller.LoginServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class LoginServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/ShopTest");
    }

    @Test
    @DisplayName("TC_U17 - doGet hiển thị trang login")
    void doGetForwardsToLoginView() throws Exception {
        new LoginServlet().doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/views/login.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_U18 - doPost đăng nhập đúng: lưu session và chuyển sang /home")
    void doPostValidCredentials() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("123456");

        new LoginServlet().doPost(request, response);

        verify(session).setAttribute(eq("user"), any());
        verify(response).sendRedirect("/ShopTest/home");
    }

    @Test
    @DisplayName("TC_U19 - doPost sai mật khẩu: báo lỗi, không chuyển trang")
    void doPostWrongPassword() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("sai_pass");

        new LoginServlet().doPost(request, response);

        verify(request).setAttribute("error", "Invalid username or password");
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("TC_U20 - doPost bỏ trống username: báo lỗi bắt buộc nhập")
    void doPostEmptyUsername() throws Exception {
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("123456");

        new LoginServlet().doPost(request, response);

        verify(request).setAttribute("error", "Username is required");
        verify(response, never()).sendRedirect(anyString());
    }
}