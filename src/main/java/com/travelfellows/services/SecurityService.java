package com.travelfellows.services;

import com.travelfellows.exceptions.AccessException;
import com.travelfellows.exceptions.SessionExpiredException;
import com.travelfellows.models.Session;
import com.travelfellows.models.User;

import java.time.LocalDateTime;

public class SecurityService {

    private final SessionService sessionService;
    private final UserService userService;

    public SecurityService(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public User getUserBySessionId(String sessionId) {
        try {
            Session session = sessionService.findById(sessionId);

            if (session.getExpireAt().isBefore(LocalDateTime.now())) {
                sessionService.delete(sessionId);
                throw new SessionExpiredException("Время сессии истекло.");
            }

            User user = userService.findById(session.getUserId());
            if (user == null) {
                throw new AccessException("Необходимо войти в учетную запись.");
            }

            return user;
        } catch (IllegalArgumentException e) {
            throw new AccessException("Войдите в учетную запись.");
        }
    }
}
