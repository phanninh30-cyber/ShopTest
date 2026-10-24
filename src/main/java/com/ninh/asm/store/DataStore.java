package com.ninh.asm.store;

import com.ninh.asm.model.Category;
import com.ninh.asm.model.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Kho dữ liệu lưu trong bộ nhớ (thay cho database để đơn giản). */
public final class DataStore {

    private static final DataStore INSTANCE = new DataStore();

    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, Category> categories = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    private DataStore() {
        // Dữ liệu mẫu có sẵn
        users.put("admin", new User("admin", "admin@shop.com", "123456", "ADMIN"));
        addCategory("Laptop");
        addCategory("Phone");
        addCategory("Accessories");
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    // ---------- User ----------
    public User findUser(String username) {
        return username == null ? null : users.get(username);
    }

    public boolean usernameExists(String username) {
        return findUser(username) != null;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // ---------- Category ----------
    public List<Category> findAllCategories() {
        List<Category> list = new ArrayList<>(categories.values());
        list.sort(Comparator.comparingInt(Category::getId));
        return list;
    }

    public Category findCategory(int id) {
        return categories.get(id);
    }

    public Category addCategory(String name) {
        Category category = new Category(sequence.incrementAndGet(), name);
        categories.put(category.getId(), category);
        return category;
    }

    public boolean updateCategory(int id, String name) {
        Category category = categories.get(id);
        if (category == null) {
            return false;
        }
        category.setName(name);
        return true;
    }

    public boolean deleteCategory(int id) {
        return categories.remove(id) != null;
    }

    /** Kiểm tra trùng tên (không phân biệt hoa thường), bỏ qua chính category đang sửa. */
    public boolean categoryNameExists(String name, Integer excludeId) {
        return categories.values().stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name)
                        && (excludeId == null || !excludeId.equals(c.getId())));
    }
}