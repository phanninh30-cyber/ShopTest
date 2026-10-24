package com.ninh.asm.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ninh.asm.util.AuthValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("unit")
class AuthValidatorTest {

    // ---------- BR1: username - giá trị biên (BVA) ----------
    @ParameterizedTest(name = "TC_U01 username dài {0} ký tự -> {1}")
    @CsvSource({"3,false", "4,true", "5,true", "19,true", "20,true", "21,false"})
    @DisplayName("TC_U01 - BVA độ dài username")
    void usernameLengthBoundary(int length, boolean expected) {
        assertEquals(expected, AuthValidator.isValidUsername("a".repeat(length)));
    }

    // ---------- BR1: username - phân vùng tương đương (EP) ----------
    @ParameterizedTest(name = "TC_U02 username hợp lệ: {0}")
    @ValueSource(strings = {"ninh", "Ninh_01", "user_123", "ABCD"})
    void usernameValidClasses(String username) {
        assertTrue(AuthValidator.isValidUsername(username));
    }

    @ParameterizedTest(name = "TC_U03 username không hợp lệ: [{0}]")
    @ValueSource(strings = {"ni nh", "ninh@", "ninh-01", "người_dùng", "ab!cd"})
    void usernameInvalidClasses(String username) {
        assertFalse(AuthValidator.isValidUsername(username));
    }

    @ParameterizedTest(name = "TC_U04 username null hoặc rỗng")
    @NullAndEmptySource
    void usernameNullOrEmpty(String username) {
        assertFalse(AuthValidator.isValidUsername(username));
    }

    // ---------- BR2: password - giá trị biên (BVA) ----------
    @ParameterizedTest(name = "TC_U05 password dài {0} ký tự -> {1}")
    @CsvSource({"0,false", "5,false", "6,true", "7,true", "29,true", "30,true", "31,false"})
    void passwordLengthBoundary(int length, boolean expected) {
        assertEquals(expected, AuthValidator.isValidPassword("x".repeat(length)));
    }

    @Test
    @DisplayName("TC_U06 - password null không hợp lệ")
    void passwordNull() {
        assertFalse(AuthValidator.isValidPassword(null));
    }

    // ---------- BR3: email - phân vùng tương đương (EP) ----------
    @ParameterizedTest(name = "TC_U07 email hợp lệ: {0}")
    @ValueSource(strings = {"a@b.com", "ninh.nguyen@fpt.edu.vn", "user+tag@mail.org"})
    void emailValid(String email) {
        assertTrue(AuthValidator.isValidEmail(email));
    }

    @ParameterizedTest(name = "TC_U08 email không hợp lệ: [{0}]")
    @ValueSource(strings = {"abc", "abc@", "@mail.com", "a@b", "a b@mail.com", "a@b.c"})
    void emailInvalid(String email) {
        assertFalse(AuthValidator.isValidEmail(email));
    }

    // ---------- validateLogin - bảng quyết định ----------
    @Test
    @DisplayName("TC_U09 - Login: username rỗng")
    void loginMissingUsername() {
        assertEquals("Username is required", AuthValidator.validateLogin("", "123456"));
    }

    @Test
    @DisplayName("TC_U10 - Login: password rỗng")
    void loginMissingPassword() {
        assertEquals("Password is required", AuthValidator.validateLogin("ninh", ""));
    }

    @Test
    @DisplayName("TC_U11 - Login: dữ liệu đầy đủ")
    void loginOk() {
        assertNull(AuthValidator.validateLogin("ninh", "123456"));
    }

    // ---------- validateRegister - bảng quyết định ----------
    @Test
    @DisplayName("TC_U12 - Register: hợp lệ")
    void registerOk() {
        assertNull(AuthValidator.validateRegister("ninh_01", "ninh@mail.com", "123456", "123456"));
    }

    @Test
    @DisplayName("TC_U13 - Register: username sai")
    void registerBadUsername() {
        assertNotNull(AuthValidator.validateRegister("ab", "ninh@mail.com", "123456", "123456"));
    }

    @Test
    @DisplayName("TC_U14 - Register: email sai")
    void registerBadEmail() {
        assertEquals("Email is invalid",
                AuthValidator.validateRegister("ninh_01", "abc", "123456", "123456"));
    }

    @Test
    @DisplayName("TC_U15 - Register: password ngắn")
    void registerShortPassword() {
        assertEquals("Password must be 6-30 characters",
                AuthValidator.validateRegister("ninh_01", "ninh@mail.com", "123", "123"));
    }

    @Test
    @DisplayName("TC_U16 - Register: confirm không khớp")
    void registerConfirmMismatch() {
        assertEquals("Confirm password does not match",
                AuthValidator.validateRegister("ninh_01", "ninh@mail.com", "123456", "654321"));
    }
}