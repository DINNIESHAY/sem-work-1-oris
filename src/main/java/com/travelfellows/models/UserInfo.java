package com.travelfellows.models;

import java.time.LocalDate;

public class UserInfo {
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dateOfBirth;
    private final String country;
    private final String city;
    private final String avatarUrl;
    private final String bio;

    public UserInfo(Long userId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String country, String city, String avatarUrl, String bio) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.city = city;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }
}
