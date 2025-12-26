package com.travelfellows.services;

import com.travelfellows.dto.TripWithLikes;
import com.travelfellows.dto.TripWithOwner;
import com.travelfellows.models.Trip;
import com.travelfellows.models.User;
import com.travelfellows.models.UserInfo;
import com.travelfellows.repositories.impl.JdbcFavoritesRepository;
import com.travelfellows.repositories.impl.JdbcTripRepository;
import com.travelfellows.repositories.impl.JdbcUserInfoRepository;
import com.travelfellows.repositories.impl.JdbcUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TripService {

    private final JdbcTripRepository tripRepository;
    private final JdbcUserRepository userRepository;
    private final JdbcUserInfoRepository userInfoRepository;
    private final JdbcFavoritesRepository favoritesRepository;

    public TripService(JdbcTripRepository tripRepository, JdbcUserRepository userRepository,
                       JdbcUserInfoRepository userInfoRepository, JdbcFavoritesRepository favoritesRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public boolean update(Trip trip) {
        return tripRepository.update(trip);
    }

    public void delete(Long id) {
        tripRepository.delete(id);
    }

    public Trip findTripById(Long id) {
        return tripRepository.findById(id)
                .orElse(null);
    }

    public TripWithOwner findTripWithOwnerById(Long id) {
        Optional<Trip> tripOptional = tripRepository.findById(id);

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();

            User owner = userRepository.findById(trip.getUserId());
            String username = (owner != null) ? owner.getUsername() : "Неизвестный пользователь";
            UserInfo userInfo = userInfoRepository.findById(trip.getUserId()).orElse(null);

            return new TripWithOwner(trip, username, userInfo);
        }

        return null;
    }

    public List<TripWithOwner> findAllTripsWithOwner() {
        List<Trip> trips =  tripRepository.findAll();

        return trips.stream().map(trip -> {
            String username = userRepository.findById(trip.getUserId()).getUsername();
            return new TripWithOwner(trip, username);
        }).collect(Collectors.toList());
    }

    public List<TripWithOwner> findAllTripsWithFavorites(Long userId) {
        List<Trip> trips = tripRepository.findAll();
        List<Long> favoriteTripIds = favoritesRepository.findFavoriteTripIds(userId);

        return trips.stream().map(trip -> {
            String username = userRepository.findById(trip.getUserId()).getUsername();
            UserInfo userInfo = userInfoRepository.findById(trip.getUserId()).orElse(null);
            boolean isFavorite = favoriteTripIds.contains(trip.getId());
            return new TripWithOwner(trip, username, userInfo, isFavorite);
        }).collect(Collectors.toList());
    }

    public List<TripWithLikes> findTripsWithLikes(Long userId) {
        List<Trip> trips = tripRepository.findByUserId(userId);

        return trips.stream().map(trip -> {
            int likesCount = countLikes(trip.getId());
            return new TripWithLikes(trip, likesCount);
        }).collect(Collectors.toList());
    }

    public boolean addToFavorites(Long userId, Long tripId) {
        return favoritesRepository.save(userId, tripId);
    }

    public void removeFromFavorites(Long userId, Long tripId) {
        favoritesRepository.delete(userId, tripId);
    }

    public List<TripWithOwner> findFavoriteTrips(Long userId) {
        List<Long> favoriteTripIds = favoritesRepository.findFavoriteTripIds(userId);

        return favoriteTripIds.stream()
                .map(tripRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(trip -> {
                    String username = userRepository.findById(trip.getUserId()).getUsername();
                    return new TripWithOwner(trip, username, true);
                })
                .collect(Collectors.toList());
    }

    public int countLikes(Long tripId) {
        return favoritesRepository.countLikes(tripId);
    }
}