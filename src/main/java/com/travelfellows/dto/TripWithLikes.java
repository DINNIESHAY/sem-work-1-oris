package com.travelfellows.dto;

import com.travelfellows.models.Trip;

public class TripWithLikes {

    private Trip trip;
    private int likesCount;

    public TripWithLikes(Trip trip, int likesCount) {
        this.trip = trip;
        this.likesCount = likesCount;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
