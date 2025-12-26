package com.travelfellows.services;

import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.Session;
import com.travelfellows.repositories.impl.JdbcSessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionService {

    private final JdbcSessionRepository sessionRepository;

    public SessionService(JdbcSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String save(Long userId, LocalDateTime expireTime) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, userId, expireTime);

        sessionRepository.save(session);

        return sessionId;
    }

    public void delete(String sessionId) {
        sessionRepository.delete(sessionId);
    }

    public Session findById(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Long getUserIdFromSession(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    Session session = findById(sessionId);
                    if (session != null && session.getExpireAt().isAfter(LocalDateTime.now())) {
                        return session.getUserId();
                    }
                    if (session != null && session.getExpireAt().isBefore(LocalDateTime.now())) {
                        throw new SessionExpiredException("Время сессии истекло.");
                    }
                }
            }
        }
        return null;
    }

    public void removeSessionFromDatabase(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    delete(sessionId);
                    break;
                }
            }
        }
    }
}
