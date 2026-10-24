package com.ninh.asm.ui;

import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            PROPS.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Không đọc được config.properties", e);
        }
    }

    private Config() {
    }

    /** Ưu tiên giá trị truyền bằng -D, nếu không có thì lấy trong file config.properties. */
    public static String get(String key) {
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank() && !fromSystem.startsWith("${")) {
            return fromSystem;
        }
        return PROPS.getProperty(key);
    }

    public static int timeout() {
        return Integer.parseInt(get("timeout.seconds"));
    }
}