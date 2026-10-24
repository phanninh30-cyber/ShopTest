package com.ninh.asm.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By username = By.id("username");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By confirm = By.id("confirmPassword");
    private final By registerButton = By.id("btnRegister");
    private final By error = By.id("msgError");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        open(Config.get("path.register"));
        return this;
    }

    public void register(String user, String mail, String pass, String confirmPass) {
        type(username, user);
        type(email, mail);
        type(password, pass);
        type(confirm, confirmPass);
        click(registerButton);
    }

    public String getErrorMessage() {
        return textOf(error);
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}