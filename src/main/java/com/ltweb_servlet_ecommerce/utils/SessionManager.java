package com.ltweb_servlet_ecommerce.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<Long, HttpSession> userSessions = new ConcurrentHashMap<>();

    public static void addSession(Long userId, HttpSession session) {
        userSessions.put(userId, session);
    }

    public static HttpSession getSession(Long userId) {
        return userSessions.get(userId);
    }

    public static void removeSession(Long userId) {
        userSessions.remove(userId);
    }
}

