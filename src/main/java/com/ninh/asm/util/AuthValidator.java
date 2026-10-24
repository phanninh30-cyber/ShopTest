package com.ninh.asm.util;

import java.util.regex.Pattern;

/**
 * Quy tắc nghiệp vụ:
 * BR1: username 4-20 ký tự, chỉ gồm chữ, số, dấu gạch dưới
 * BR2: password 6-30 ký tự
 * BR3: email đúng định dạng
 * BR4: confirm password phải trùng password
 */
public final class AuthValidator {

    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_]{4,20}$");
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private AuthValidator() {
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 30;
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    /** @return null nếu hợp lệ, ngược lại trả về thông báo lỗi */
    public static String validateLogin(String username, String password) {
        if (username == null || username.isBlank()) {
            return "Username is required";
        }
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        return null;
    }

    /** @return null nếu hợp lệ, ngược lại trả về thông báo lỗi */
    public static String validateRegister(String username, String email,
                                          String password, String confirm) {
        if (!isValidUsername(username)) {
            return "Username must be 4-20 characters (letters, digits, underscore)";
        }
        if (!isValidEmail(email)) {
            return "Email is invalid";
        }
        if (!isValidPassword(password)) {
            return "Password must be 6-30 characters";
        }
        if (!password.equals(confirm)) {
            return "Confirm password does not match";
        }
        return null;
    }
}