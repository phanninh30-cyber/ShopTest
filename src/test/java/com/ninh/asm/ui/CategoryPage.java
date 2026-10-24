package com.ninh.asm.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CategoryPage extends BasePage {

    private final By nameInput = By.id("name");
    private final By saveButton = By.id("btnSave");
    private final By error = By.id("msgError");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public CategoryPage open() {
        open(Config.get("path.categories"));
        return this;
    }

    private By row(String name) {
        return By.xpath("//table[@id='categoryTable']//tr[td[@class='cat-name']"
                + "[normalize-space()='" + name + "']]");
    }

    public void create(String name) {
        type(nameInput, name);
        click(saveButton);
    }

    public boolean exists(String name) {
        return !driver.findElements(row(name)).isEmpty();
    }

    public void edit(String oldName, String newName) {
        click(By.xpath("//tr[td[@class='cat-name'][normalize-space()='" + oldName
                + "']]//a[contains(@class,'btn-edit')]"));
        type(nameInput, newName);
        click(saveButton);
    }

    public void delete(String name) {
        click(By.xpath("//tr[td[@class='cat-name'][normalize-space()='" + name
                + "']]//button[contains(@class,'btn-delete')]"));
        wait.until(ExpectedConditions.alertIsPresent()).accept(); // bấm OK ở hộp thoại xác nhận
    }

    public String getErrorMessage() {
        return textOf(error);
    }
}