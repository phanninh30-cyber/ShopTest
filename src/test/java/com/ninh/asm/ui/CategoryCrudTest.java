package com.ninh.asm.ui;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class CategoryCrudTest extends BaseTest {

    private CategoryPage loginAsAdminAndOpenCategories() {
        new LoginPage(driver).open().login(Config.get("admin.user"), Config.get("admin.pass"));
        return new CategoryPage(driver).open();
    }

    @Test(groups = {"smoke", "crud"}, description = "TC_UI10 CRUD danh mục (Create-Read-Update-Delete)")
    public void fullCrudFlow() {
        CategoryPage page = loginAsAdminAndOpenCategories();
        String name = "AutoCat" + System.currentTimeMillis() % 100000;
        String newName = name + "_edit";

        page.create(name);
        page.open();
        assertTrue(page.exists(name), "CREATE: phải thấy danh mục vừa tạo");

        page.edit(name, newName);
        page.open();
        assertTrue(page.exists(newName), "UPDATE: phải thấy tên mới");
        assertFalse(page.exists(name), "UPDATE: tên cũ phải biến mất");

        page.delete(newName);
        page.open();
        assertFalse(page.exists(newName), "DELETE: danh mục phải bị xóa");
    }

    @Test(groups = {"regression", "crud"}, description = "TC_UI11 Tạo danh mục với tên rỗng")
    public void createEmptyName() {
        CategoryPage page = loginAsAdminAndOpenCategories();
        page.create("");
        assertTrue(page.getErrorMessage().contains("Category name is required"));
    }

    @Test(groups = {"regression", "crud"}, description = "TC_UI12 Tạo danh mục trùng tên")
    public void createDuplicateName() {
        CategoryPage page = loginAsAdminAndOpenCategories();
        page.create("Laptop"); // danh mục có sẵn
        assertTrue(page.getErrorMessage().contains("Category name already exists"));
    }

    @Test(groups = {"regression", "crud"}, description = "TC_UI13 Tên danh mục dài hơn 50 ký tự")
    public void createTooLongName() {
        CategoryPage page = loginAsAdminAndOpenCategories();
        page.create("A".repeat(51));
        assertTrue(page.getErrorMessage().contains("at most 50 characters"));
    }
}