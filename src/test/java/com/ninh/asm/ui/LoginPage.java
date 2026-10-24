package com.ninh.asm.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.id("btnLogin");
    private final By error = By.id("msgError");
    private final By success = By.id("msgSuccess");
    private final By logout = By.id("logout");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        open(Config.get("path.login"));
        return this;
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    public String getErrorMessage() {
        return textOf(error);
    }

    public String getSuccessMessage() {
        return textOf(success);
    }

    public boolean isLoggedIn() {
        return isDisplayed(logout);
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}