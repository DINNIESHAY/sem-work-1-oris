package com.travelfellows.models;

public class TripApplication {
    private final Long id;
    private final Long userId;
    private final Long tripId;
    private final String message;
    private final String contacts;

    public TripApplication(Long userId, Long tripId, String message, String contacts) {
        this.id = null;
        this.userId = userId;
        this.tripId = tripId;
        this.message = message;
        this.contacts = contacts;
    }

    public TripApplication(Long id, Long userId, Long tripId, String message, String contacts) {
        this.id = id;
        this.userId = userId;
        this.tripId = tripId;
        this.message = message;
        this.contacts = contacts;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTripId() {
        return tripId;
    }

    public String getMessage() {
        return message;
    }

    public String getContacts() {
        return contacts;
    }
}