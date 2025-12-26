package com.travelfellows.models;

public class User {

    private final Long id;
    private final String email;
    private final String username;
    private final String passwordHash;
    private final String salt;

    public User(Long id, String email, String username, String passwordHash, String salt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public User(String email, String username, String passwordHash, String salt) {
        this.id = null;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getUsername() {
        return username;
    }
}
