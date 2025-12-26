package com.travelfellows.dto;

import com.travelfellows.models.Trip;
import com.travelfellows.models.UserInfo;

public class TripWithOwner {

    private Trip trip;
    private String ownerUsername;
    private UserInfo userInfo;
    private boolean favorite;

    public TripWithOwner(Trip trip, String ownerUsername) {
        this.trip = trip;
        this.ownerUsername = ownerUsername;
    }

    public TripWithOwner(Trip trip, String ownerUsername, UserInfo userInfo) {
        this.trip = trip;
        this.ownerUsername = ownerUsername;
        this.userInfo = userInfo;
    }

    public TripWithOwner(Trip trip, String ownerUsername, boolean favourite) {
        this.trip = trip;
        this.ownerUsername = ownerUsername;
        this.favorite = favourite;
    }

    public TripWithOwner(Trip trip, String ownerUsername, UserInfo userInfo, boolean favorite) {
        this.trip = trip;
        this.ownerUsername = ownerUsername;
        this.userInfo = userInfo;
        this.favorite = favorite;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favourite) {
        this.favorite = favourite;
    }
}
