package com.ninh.asm.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver create() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,768", "--remote-allow-origins=*");
        if ("true".equalsIgnoreCase(Config.get("headless"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        // Selenium 4.6+ tự tải chromedriver, không cần cài thêm
        return new ChromeDriver(options);
    }
}