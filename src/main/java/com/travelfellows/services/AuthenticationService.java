package com.travelfellows.services;

import com.travelfellows.exceptions.InvalidEmailException;
import com.travelfellows.exceptions.InvalidUsernameException;
import com.travelfellows.exceptions.UserNotFoundException;
import com.travelfellows.exceptions.WrongPasswordException;
import com.travelfellows.models.User;
import com.travelfellows.util.PasswordUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

public class AuthenticationService {

    private final UserService userService;
    private final SessionService sessionService;

    public AuthenticationService(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public void registerUser(HttpServletResponse resp,
                             String email, String username,
                             String password, String passwordAgain) {

        User existingUser = userService.findByEmail(email);

        if (existingUser != null) {
            throw new InvalidEmailException("Пользователь с таким email существует.");
        }

        if (userService.findByUsername(username) != null) {
            throw new InvalidUsernameException("Пользователь с таким именем существует.");
        }

        if (!PasswordUtil.isValidPassword(password)) {
            throw new WrongPasswordException("Пароль небезопасен.");
        }

        if (!password.equals(passwordAgain)) {
            throw new WrongPasswordException("Пароли не совпадают!");
        }

        String salt = PasswordUtil.generateSalt();
        String passwordHash = PasswordUtil.hashPassword(password, salt);

        Long userId = userService.save(email, username, passwordHash, salt);

        LocalDateTime expireAt = LocalDateTime.now().plusHours(2);
        String sessionId = sessionService.save(userId, expireAt);
        CookieService.createCookie(sessionId, resp);
    }

    public void loginUser(HttpServletResponse resp,
                          String email, String password) {

        User existingUser = userService.findByEmail(email);

        if (existingUser == null) {
            throw new UserNotFoundException("Пользователь с таким email не существует.");
        }

        if (!PasswordUtil.isCorrectPassword(existingUser, password)) {
            throw new WrongPasswordException("Неверный пароль! Попробуйте ещё раз.");
        }

        LocalDateTime expireAt = LocalDateTime.now().plusHours(2);
        String sessionId = sessionService.save(existingUser.getId(), expireAt);
        CookieService.createCookie(sessionId, resp);
    }
}