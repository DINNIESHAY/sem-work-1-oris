package com.travelfellows.services;

import com.travelfellows.models.User;
import com.travelfellows.repositories.impl.JdbcUserRepository;

public class UserService {

    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(String email, String username, String passwordHash, String salt) {
        return userRepository.save(new User(email, username, passwordHash, salt));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}