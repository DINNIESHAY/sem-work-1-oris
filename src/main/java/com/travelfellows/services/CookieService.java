package com.travelfellows.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieService {

    private static SessionService sessionService;

    public static void createCookie(String sessionId, HttpServletResponse resp) {
        Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        resp.addCookie(sessionCookie);
    }

    public static void deleteCookie(HttpServletResponse resp) {
        Cookie sessionCookie = new Cookie("SESSION_ID", "");
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);
        resp.addCookie(sessionCookie);
    }

    public static String extractSessionId(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SESSION_ID")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
