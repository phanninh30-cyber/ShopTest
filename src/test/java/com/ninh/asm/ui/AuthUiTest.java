package com.ninh.asm.ui;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class AuthUiTest extends BaseTest {

    private String uniqueUser() {
        return "user" + System.currentTimeMillis() % 100000000L;
    }

    @Test(groups = {"smoke", "login"}, description = "TC_UI01 Đăng nhập đúng")
    public void loginSuccess() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.get("admin.user"), Config.get("admin.pass"));
        assertTrue(page.isLoggedIn(), "Phải thấy nút Logout sau khi đăng nhập");
        assertTrue(page.currentUrl().contains("/home"), "Phải chuyển sang trang Home");
    }

    @Test(groups = {"regression", "login"}, description = "TC_UI02 Sai mật khẩu")
    public void loginWrongPassword() {
        LoginPage page = new LoginPage(driver).open();
        page.login(Config.get("admin.user"), "sai_mat_khau_999");
        assertFalse(page.isLoggedIn());
        assertTrue(page.getErrorMessage().contains("Invalid username or password"));
    }

    @Test(groups = {"regression", "login"}, description = "TC_UI03 Bỏ trống thông tin")
    public void loginEmpty() {
        LoginPage page = new LoginPage(driver).open();
        page.login("", "");
        assertFalse(page.isLoggedIn());
        assertTrue(page.getErrorMessage().contains("Username is required"));
    }

    @Test(groups = {"smoke", "register"}, description = "TC_UI04 Đăng ký tài khoản mới rồi đăng nhập")
    public void registerSuccess() {
        String user = uniqueUser();
        new RegisterPage(driver).open().register(user, user + "@mail.com", "123456", "123456");

        LoginPage login = new LoginPage(driver);
        assertTrue(login.currentUrl().contains("/login"), "Phải chuyển về trang login");
        assertTrue(login.getSuccessMessage().contains("Registration successful"));

        login.login(user, "123456");
        assertTrue(login.isLoggedIn(), "Tài khoản vừa đăng ký phải đăng nhập được");
    }

    @Test(groups = {"regression", "register"}, description = "TC_UI05 Confirm password không khớp")
    public void registerConfirmMismatch() {
        RegisterPage page = new RegisterPage(driver).open();
        page.register("auto_user1", "auto1@mail.com", "123456", "654321");
        assertTrue(page.getErrorMessage().contains("Confirm password does not match"));
    }

    @Test(groups = {"regression", "register"}, description = "TC_UI06 Trùng username đã tồn tại")
    public void registerDuplicateUser() {
        RegisterPage page = new RegisterPage(driver).open();
        page.register(Config.get("admin.user"), "dup@mail.com", "123456", "123456");
        assertTrue(page.getErrorMessage().contains("Username already exists"));
    }

    @Test(groups = {"regression", "register"}, description = "TC_UI07 Username quá ngắn")
    public void registerShortUsername() {
        RegisterPage page = new RegisterPage(driver).open();
        page.register("abc", "abc@mail.com", "123456", "123456");
        assertTrue(page.getErrorMessage().contains("Username must be 4-20 characters"));
    }

    @Test(groups = {"regression", "security"}, description = "TC_UI08 User thường bị chặn vào trang admin")
    public void normalUserCannotAccessAdmin() {
        String user = uniqueUser();
        new RegisterPage(driver).open().register(user, user + "@mail.com", "123456", "123456");

        LoginPage login = new LoginPage(driver);
        login.login(user, "123456");
        assertTrue(login.isLoggedIn());

        new CategoryPage(driver).open();
        assertTrue(driver.getPageSource().contains("403"), "Phải bị chặn với lỗi 403");
    }

    @Test(groups = {"regression", "security"}, description = "TC_UI09 Chưa đăng nhập bị đưa về trang login")
    public void guestRedirectedToLogin() {
        new CategoryPage(driver).open();
        assertTrue(driver.getCurrentUrl().contains("/login"), "Phải bị chuyển về /login");
    }
}