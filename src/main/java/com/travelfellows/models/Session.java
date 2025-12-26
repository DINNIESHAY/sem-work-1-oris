package com.travelfellows.models;

import java.time.LocalDateTime;

public class Session {
    private final String id;
    private final Long userId;
    private final LocalDateTime expireAt;

    public Session(String sessionId, Long userId, LocalDateTime expireAt) {
        this.id = sessionId;
        this.userId = userId;
        this.expireAt = expireAt;
    }

    public String getSessionId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }
}
